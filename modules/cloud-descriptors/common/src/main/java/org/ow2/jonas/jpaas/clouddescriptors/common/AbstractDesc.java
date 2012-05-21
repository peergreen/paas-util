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

import org.ow2.util.log.Log;
import org.ow2.util.log.LogFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * Abstract descriptor
 * @author Mohammed Boukada
 */
public class AbstractDesc {

    /**
     * The logger
     */
    protected static Log logger = LogFactory.getLog(AbstractDesc.class);

    /**
     * XSD URL
     */
    protected List<URL> xsdUrls;

    /**
     * Namespaces of the XML document
     */
    protected Map<String, Node> namespaces;

    /**
     * XMLNS pattern
     */
    public static final Pattern XMLNS_PATTERN = Pattern.compile("xmlns.*");

    /**
     * Load properties file
     * @return properties
     * @throws Exception
     */
    protected Properties loadProperties(String location, ClassLoader classLoader) throws Exception {
        Properties properties = new Properties();
        InputStream input = classLoader.getResourceAsStream(location);
        try {
            properties.load(input);
        } catch (Exception e) {
            throw new Exception("Cannot load " + location + " properties file", e);
        } finally {
            input.close();
        }
        return properties;
    }

    /**
     * Get all namespaces of the document
     * @param node The node to check
     */
    protected void getNamespace(final Node node) {
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node child = nodeList.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) child;
                NamedNodeMap namedNodeMap = element.getAttributes();
                for (int y = 0; y < namedNodeMap.getLength(); y++) {
                    Attr attribute = Attr.class.cast(namedNodeMap.item(y));
                    if (XMLNS_PATTERN.matcher(attribute.getName()).matches()) {
                        String namespace = attribute.getValue();
                        if (namespace != null && !namespace.isEmpty()) {
                            this.namespaces.put(namespace, child);
                        }
                        getNamespace(child);
                    }
                }
            }
        }
    }

    /**
     * @param resources List of resources
     * @return the list of Source associate to the given resources list
     */
    protected List<URL> getXsdURL(final Map<String, Class<?>> resources) throws Exception {
        List<URL> urls = new LinkedList<URL>();
        for (Map.Entry<String, Class<?>> resource : resources.entrySet()) {
            URL url = getXsdURL(resource);
            if (url != null) {
                urls.add(url);
            }
        }
        return urls;
    }

    /**
     * @param resource Resource
     * @return the URL of the given resource
     * @throws Exception
     */
    protected URL getXsdURL(Map.Entry<String, Class<?>> resource) throws Exception {
        String resourceName = resource.getKey();
        Class<?> clazz = resource.getValue();
        return getXsdURL(resourceName, clazz);
    }

    /**
     * @param resource The resource to retrieve
     * @param clazz A {@link Class} associated to the given resource (they should share the same classloader)
     * @return the URL of the given resource
     * @throws Exception
     */
    protected URL getXsdURL(final String resource, final Class clazz) throws Exception {
        URL url = clazz.getClassLoader().getResource(resource);
        if (url == null) {
            url = clazz.getResource(resource);
            if (url == null) {
                throw new Exception("Cannot get the URL of the resource " + resource + "\n");
            }
        }
        return url;
    }

    /**
     * @param url The xml URL
     * @return the associated file
     */
    protected File getFile(final URL url) {
        try {
            return new File(url.toURI());
        } catch (URISyntaxException e) {
            logger.error("Cannot get the URI of the URL " + url.getFile(), e);
        }
        return null;
    }

    /**
     * @param file The  file
     * @return the associated document
     */
    protected Document getDocument(final File file) {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        Document document = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            logger.error("Cannot get the instance of the DocumentBuilder", e);
        }
        if (documentBuilder != null) {
            try {
                document = documentBuilder.parse(file);
            } catch (SAXException e) {
                logger.error("Cannot parse XML file " + file.getAbsolutePath(), e);
            } catch (IOException e) {
                logger.error("Cannot parse XML file " + file.getAbsolutePath(), e);
            }
        }
        return document;
    }

    /**
     * @param content
     * @return the associated document
     */
    protected Document getDocument(final String content) {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        Document document = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            logger.error("Cannot get the instance of the DocumentBuilder", e);
        }
        if (documentBuilder != null) {
            try {
                document = documentBuilder.parse(new InputSource(new StringReader(content.trim())));
            } catch (SAXException e) {
                logger.error("Cannot parse XML content of " + content, e);
            } catch (IOException e) {
                logger.error("Cannot parse XML content of " + content, e);
            }
        }
        return document;
    }
}