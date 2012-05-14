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

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
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
    protected Properties loadProperties(String location) throws Exception {
        Properties properties = new Properties();
        URL filename = getClass().getClassLoader().getResource(location);
        FileInputStream input = null;
        try {
            input = new FileInputStream(filename.getFile());
        } catch (FileNotFoundException e) {
            throw new Exception("Cannot read cloud-application properties file", e);
        }
        try {
            properties.load(input);
        } catch (Exception e) {
            throw new Exception("Cannot load cloud-application properties file", e);
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
        List<URL> urls = new ArrayList<URL>();
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
}