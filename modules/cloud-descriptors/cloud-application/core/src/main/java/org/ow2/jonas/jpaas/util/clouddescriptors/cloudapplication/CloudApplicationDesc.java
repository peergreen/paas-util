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

package org.ow2.jonas.jpaas.util.clouddescriptors.cloudapplication;

import org.ow2.jonas.jpaas.clouddescriptors.common.AbstractDesc;
import org.ow2.jonas.jpaas.util.clouddescriptors.cloudapplication.deployable.artefact.ArtefactVersion;
import org.ow2.jonas.jpaas.util.clouddescriptors.cloudapplication.deployable.xml.XmlVersion;
import org.ow2.util.log.Log;
import org.ow2.util.log.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Describe cloud application
 * @author Mohammed Boukada
 */
public class CloudApplicationDesc extends AbstractDesc {

    /**
     * The logger
     */
    protected static Log logger = LogFactory.getLog(CloudApplicationDesc.class);

    /**
     * Cloud-application
     */
    protected Object cloudApplication;

    /**
     * Version of cloud-application
     */
    protected CloudApplicationVersion cloudApplicationVersion;

    /**
     * Version of Artefact
     */
    protected ArtefactVersion artefactVersion;

    /**
     * Version of Xml
     */
    protected XmlVersion xmlVersion;

    /**
     * Properties file path (in properties path format)
     */
    private static final String CLOUD_APPLICATION_PROPERTIES_NAME = "properties/cloud-application.properties";

    /**
     * Default constructor
     */
    public CloudApplicationDesc() throws Exception {
        init();
    }

    /**
     * Constructor with xml url
     * @param urlCloudApplication
     * @throws Exception
     */
    public CloudApplicationDesc(URL urlCloudApplication) throws Exception {
        init();
        loadCloudApplication(urlCloudApplication);
    }

    /**
     * Constructor with xml content
     * @param cloudApplication
     * @throws Exception
     */
    public CloudApplicationDesc(String cloudApplication) throws Exception {
        init();
        loadCloudApplication(cloudApplication);
    }

    /**
     * Initialisation
     * @throws Exception
     */
    private void init() throws Exception {
        this.xsdUrls = new ArrayList<URL>();
        this.namespaces = new LinkedHashMap<String, Node> ();
        CloudApplicationPropertiesManager.setProperties(loadProperties(CLOUD_APPLICATION_PROPERTIES_NAME));
    }

    /**
     * Set XSD URLs
     * @throws Exception
     */
    private void setXsdUrls() throws Exception {
        initArtefactAndXmlVersions();
        String xsdCloudApplication = CloudApplicationPropertiesManager.getXsdCloudApplicationPath(cloudApplicationVersion);
        String xsdArtefact = CloudApplicationPropertiesManager.getXsdArtefactPath(artefactVersion);
        String xsdXml = CloudApplicationPropertiesManager.getXsdXmlPath(xmlVersion);
        Map<String, Class<?>> resources = new HashMap<String, Class<?>>();
        resources.put(xsdCloudApplication, CloudApplicationVersion.class);
        resources.put(xsdArtefact, ArtefactVersion.class);
        resources.put(xsdXml, XmlVersion.class);
        xsdUrls = getXsdURL(resources);
    }

    /**
     * Initialize artefact and xml version
     */
    private void initArtefactAndXmlVersions() {
        List<String> namespaces = new ArrayList<String>();
        for (Map.Entry<String, Node> entry : this.namespaces.entrySet()) {
            namespaces.add(entry.getKey());
        }
        artefactVersion = CloudApplicationPropertiesManager.getArtefactVersion(namespaces);
        xmlVersion = CloudApplicationPropertiesManager.getXmlVersion(namespaces);

        if (artefactVersion == null) {
            logger.error("Cannot get the artefact version");
        }
        if (xmlVersion == null) {
            logger.error("Cannot get the xml version");
        }
    }

    /**
     * Get cloud-application
     * @return cloud-application
     */
    public Object getCloudApplication() {
        return cloudApplication;
    }

    /**
     * Load the cloud-application from the url urlCloudApplication using the schema from
     * schemaUrl.
     *
     * @param urlCloudApplication Url of the cloud-application on xml format
     * @throws Exception
     */
    public void loadCloudApplication(final URL urlCloudApplication) throws Exception {
        initCloudApplicationVersion(urlCloudApplication);
        setXsdUrls();
        XmlLoader xmlLoader = new XmlLoader(urlCloudApplication, this.cloudApplicationVersion, this.xsdUrls);
        this.cloudApplication = xmlLoader.getCloudApplication();
    }

    /**
     * Load the cloud-application from the xml string cloudApplication using the schema from
     * schemaUrl.
     *
     * @param cloudApplication Url of the cloud-application on xml format
     * @throws Exception
     */
    public void loadCloudApplication(final String cloudApplication) throws Exception {
        initCloudApplicationVersion(cloudApplication);
        setXsdUrls();
        XmlLoader xmlLoader = new XmlLoader(cloudApplication, this.cloudApplicationVersion, this.xsdUrls);
        this.cloudApplication = xmlLoader.getCloudApplication();
    }

    /**
     * Initialize the cloud-application version which is matching the given xml file
     * @param urlCloudApplication The cloud-application URL
     */
    private void initCloudApplicationVersion(final URL urlCloudApplication) {
        try {
            initCloudApplicationVersion(new File(urlCloudApplication.toURI()));
        } catch (URISyntaxException e) {
            logger.error("Cannot get the URI of the URL " + urlCloudApplication.getFile(), e);
        }
    }

    /**
     * Initialize the cloud-application version which is matching the given xml file
     * @param cloudApplicationFile The cloud-application file
     */
    private void initCloudApplicationVersion(final File cloudApplicationFile) {
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
                document = documentBuilder.parse(cloudApplicationFile);
            } catch (SAXException e) {
                logger.error("Cannot parse XML file " + cloudApplicationFile.getAbsolutePath(), e);
            } catch (IOException e) {
                logger.error("Cannot parse XML file " + cloudApplicationFile.getAbsolutePath(), e);
            }
        }
        initCloudApplicationVersion(document);
    }

    /**
     * Initialize the cloud-application version which is matching the given xml file
     * @param cloudApplication The content of the cloud-application
     */
    private void initCloudApplicationVersion(final String cloudApplication) {
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
                document = documentBuilder.parse(new InputSource(new StringReader(cloudApplication.trim())));
            } catch (SAXException e) {
                logger.error("Cannot parse XML content of " + cloudApplication, e);
            } catch (IOException e) {
                logger.error("Cannot parse XML content of " + cloudApplication, e);
            }
        }
        initCloudApplicationVersion(document);
    }

    /**
     * Initialize the cloud-application version which is matching the given xml file
     * @param document The {@link org.w3c.dom.Document} associated to the cloud-application
     */
    private void initCloudApplicationVersion(final Document document) {
        if (document != null) {
            getNamespace(document);
        }
        this.cloudApplicationVersion = CloudApplicationPropertiesManager.getCloudApplicationVersion(new ArrayList<String>(this.namespaces.keySet()));
    }
}
