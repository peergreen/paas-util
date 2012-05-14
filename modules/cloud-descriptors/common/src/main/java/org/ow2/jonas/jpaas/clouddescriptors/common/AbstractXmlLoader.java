/**
 * JPaaS Util
 * Copyright (C) 2012 Bull S.A.S.
 * Contact: jasmine@ow2.org
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
 * USA
 *
 * --------------------------------------------------------------------------
 * $Id$
 * --------------------------------------------------------------------------
 */

package org.ow2.jonas.jpaas.clouddescriptors.common;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
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
     * Verifies an XML file against an XSD and instantiates it using a given
     * class.
     *
     * @param xsdPaths XSDs files path.
     * @param xmlns XML namespace, will be added if non found in the XML
     * @param schemaLocation XML schema location, will be added if non found in
     *        the XML
     * @param xmlRoot Root element of the XML (for completing the XML if XSD
     *        xmlns is missing)
     * @param rootClass Root class used for instantiating JAXB.
     * @param urlXML XML to load.
     * @return XML loaded using JAXB and the rootClass.
     * @throws Exception
     */
    public <T> T loadSchemaAndFile(final List<URL> xsdPaths, final String xmlns, final String schemaLocation,
                                   final String xmlRoot, final Class<T> rootClass, final URL urlXML)
            throws Exception {
        String xml = readURL(urlXML);
        return loadSchemaAndFile(xsdPaths, xmlns, schemaLocation, xmlRoot, rootClass, xml);
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
     * @param rootClass Root class used for instantiating JAXB.
     * @param xml XML to load.
     * @return XML loaded using JAXB and the rootClass.
     * @throws Exception
     */
    public <T> T loadSchemaAndFile(final List<URL> xsdPaths, final String xmlns, final String schemaLocation,
                                   final String xmlRoot, final Class<T> rootClass, String xml)
            throws Exception {

        JAXBContext jc = JAXBContext.newInstance(rootClass.getPackage().getName(), AbstractXmlLoader.class.getClassLoader());
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
        JAXBElement<T> root = unMarshaller.unmarshal(new StreamSource(xmlInputStream), rootClass);
        value = root.getValue();

        return value;
    }

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
     *
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
