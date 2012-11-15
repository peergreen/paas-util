/**
 * JPaaS
 * Copyright 2012 Bull S.A.S.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * $Id:$
 */

package org.ow2.jonas.jpaas.clouddescriptors.common;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract Xml Loader
 * @author Mohammed Boukada
 */
public class AbstractXmlLoader {

    /**
     * Namespace prefix mapper property name
     */
    private final String NAMESPACE_PREFIX_MAPPER_PROPERTY_NAME = "com.sun.xml.bind.namespacePrefixMapper";

    /**
     * Verifies an XML file against an XSD and instantiates it using a given
     * class.
     *
     * @param xsdPaths XSDs files path.
     * @param xmlns XML namespace, will be added if non found in the XML
     * @param schemaLocation XML schema location, will be added if non found in
     *        the XML
     * @param xmlRoot Root element of the XML (for completing the XML if XSD
     *        xmlns is missing)
     * @param rootClasses Root class used for instantiating JAXB.
     * @param urlXML XML to load.
     * @return XML loaded using JAXB and the rootClass.
     * @throws Exception
     */
    public <T> T loadSchemaAndFile(final List<URL> xsdPaths, final String xmlns, final String schemaLocation,
                                   final String xmlRoot, final URL urlXML,  final Class<T> ... rootClasses)
            throws Exception {
        String xml = readURL(urlXML);
        return loadSchemaAndFile(xsdPaths, xmlns, schemaLocation, xmlRoot, xml, rootClasses);
    }

    /**
     * Verifies an XML file against an XSD and instantiates it using a given
     * class.
     *
     * @param xsdPaths XSDs files path.
     * @param xmlns XML namespace, will be added if non found in the XML
     * @param schemaLocation XML schema location, will be added if non found in
     *        the XML
     * @param xmlRoot Root element of the XML (for completing the XML if XSD
     *        xmlns is missing)
     * @param rootClasses Root class used for instantiating JAXB.
     * @param xml XML to load.
     * @return XML loaded using JAXB and the rootClass.
     * @throws Exception
     */
    public <T> T loadSchemaAndFile(final List<URL> xsdPaths, final String xmlns, final String schemaLocation,
                                   final String xmlRoot, String xml, final Class<T> ... rootClasses)
            throws Exception {

        JAXBContext jc = JAXBContext.newInstance(rootClasses);
        Unmarshaller unMarshaller = jc.createUnmarshaller();
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        List<Source> xsdSources = getSources(xsdPaths);
        Schema schema = schemaFactory.newSchema(xsdSources.toArray(new Source[xsdSources.size()]));
        unMarshaller.setSchema(schema);

        final int xmlRootStart = xml.indexOf("<" + xmlRoot);
        if (xmlRootStart != -1) {
            final int xmlRootEnd = xml.indexOf(">", xmlRootStart);

            if (xmlRootEnd != -1) {
                final int xmlnsIndex = xml.indexOf("xmlns", xmlRootStart);

                if (xmlnsIndex == -1) {
                    xml = xml.substring(0, xmlRootStart) + "<" + xmlRoot + " xmlns=\"" + xmlns
                            + "\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"" + xmlns + " "
                            + schemaLocation + "\"" + xml.substring(xmlRootStart + xmlRoot.length() + 1);
                }
            }
        }
        InputStream xmlInputStream = new ByteArrayInputStream(xml.getBytes());

        T value;
        JAXBElement<T> root = unMarshaller.unmarshal(new StreamSource(xmlInputStream), rootClasses[0]);
        value = root.getValue();

        return value;
    }

    /**
     * Generate xml content
     * @param jaxbElement root element
     * @param rootClasses Root class used for instantiating JAXB.
     * @return xml content
     * @throws javax.xml.bind.JAXBException
     */
    public String toXml(JAXBElement<?> jaxbElement, final List<URL> xsdURLs, NamespacePrefixMapper mapper,
                        final Class<?> ... rootClasses) throws Exception {
        JAXBContext jc = JAXBContext.newInstance(rootClasses);
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        // Load schemas
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        List<Source> xsdSources = getSources(xsdURLs);
        Schema schema = schemaFactory.newSchema(xsdSources.toArray(new Source[xsdSources.size()]));
        marshaller.setSchema(schema);
        if (mapper != null) {
            marshaller.setProperty(NAMESPACE_PREFIX_MAPPER_PROPERTY_NAME, mapper);
        }
        StringWriter sw = new StringWriter();
        marshaller.marshal(jaxbElement, sw);
        return sw.toString();
    }

    /**
     * Read url content
     * @param url
     * @return content
     * @throws IOException
     */
    private static String readURL(final URL url) throws IOException {
        URLConnection connection = url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        try {
            StringBuilder sb = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (sb.length() > 0) {
                    sb.append('\n');
                }
                sb.append(inputLine);
            }

            return sb.toString();
        } finally {
            in.close();
        }
    }

    /**
     * Get sources list corresponding to URLs
     * @param urls List of URL
     * @return the list of {@link javax.xml.transform.Source} associated to the list of url
     */
    protected List<Source> getSources(final List<URL> urls) throws Exception {
        List<Source> sources = new ArrayList<Source>();
        for (URL url: urls) {
            Source source = getSource(url);
            if (source != null) {
                sources.add(source);
            }
        }
        return sources;
    }

    /**
     * Get the source corresponding to URL
     * @param url The {@link java.net.URL}
     * @return the {@link javax.xml.transform.Source} associated to the given URL
     * @throws Exception
     */
    protected Source getSource(final URL url) throws Exception {
        if (url != null) {
            InputStream inputStream = null;
            try {
                inputStream = url.openStream();
            } catch (IOException e) {
                throw new Exception("Cannot get the inpustream of the URL " + url.getPath() + "\n");
            }

            try {
                return new StreamSource(inputStream);
            } catch (Exception e) {
                throw new Exception("Cannot create a new StreamSource for the URL : " + url.getPath()
                        + "\n", e);
            }
        } else {
            return null;
        }
    }
}
