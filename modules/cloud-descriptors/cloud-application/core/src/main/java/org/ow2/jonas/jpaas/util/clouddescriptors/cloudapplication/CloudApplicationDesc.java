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

import java.io.File;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.LinkedList;
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
        this.xsdUrls = new LinkedList<URL>();
        this.namespaces = new LinkedHashMap<String, Node> ();
        CloudApplicationPropertiesManager.setProperties(loadProperties(CLOUD_APPLICATION_PROPERTIES_NAME));
    }

    /**
     * Set XSD URLs
     * @throws Exception
     */
    private void setXsdUrls() throws Exception {
        initArtefactAndXmlVersions();

        // Get xsd paths
        String xsdCloudApplication = CloudApplicationPropertiesManager.getXsdCloudApplicationPath(cloudApplicationVersion);
        String xsdArtefact = CloudApplicationPropertiesManager.getXsdArtefactPath(artefactVersion);
        String xsdXml = CloudApplicationPropertiesManager.getXsdXmlPath(xmlVersion);

        // Store paths with there corresponding class (classloader)
        Map<String, Class<?>> resources = new LinkedHashMap<String, Class<?>>();
        // Start by adding imported xsd
        resources.put(xsdXml, XmlVersion.class);
        resources.put(xsdArtefact, ArtefactVersion.class);
        resources.put(xsdCloudApplication, CloudApplicationVersion.class);  // Must be the last added to the map

        xsdUrls = getXsdURL(resources);
    }

    /**
     * Initialize artefact and xml version
     */
    private void initArtefactAndXmlVersions() {
        List<String> namespaces = new LinkedList<String>();
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
        CloudApplicationXmlLoader cloudApplicationXmlLoader = new CloudApplicationXmlLoader(urlCloudApplication, this.cloudApplicationVersion, this.xsdUrls);
        this.cloudApplication = cloudApplicationXmlLoader.getCloudApplication();
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
        CloudApplicationXmlLoader cloudApplicationXmlLoader = new CloudApplicationXmlLoader(cloudApplication, this.cloudApplicationVersion, this.xsdUrls);
        this.cloudApplication = cloudApplicationXmlLoader.getCloudApplication();
    }

    /**
     * Initialize the cloud-application version which is matching the given xml file
     * @param urlCloudApplication The cloud-application URL
     */
    private void initCloudApplicationVersion(final URL urlCloudApplication) {
        initCloudApplicationVersion(getFile(urlCloudApplication));
    }

    /**
     * Initialize the cloud-application version which is matching the given xml file
     * @param cloudApplicationFile The cloud-application file
     */
    private void initCloudApplicationVersion(final File cloudApplicationFile) {
        initCloudApplicationVersion(getDocument(cloudApplicationFile));
    }

    /**
     * Initialize the cloud-application version which is matching the given xml file
     * @param cloudApplication The content of the cloud-application
     */
    private void initCloudApplicationVersion(final String cloudApplication) {
        initCloudApplicationVersion(getDocument(cloudApplication));
    }

    /**
     * Initialize the cloud-application version which is matching the given xml file
     * @param document The {@link org.w3c.dom.Document} associated to the cloud-application
     */
    private void initCloudApplicationVersion(final Document document) {
        if (document != null) {
            getNamespace(document);
        }
        this.cloudApplicationVersion = CloudApplicationPropertiesManager.getCloudApplicationVersion(new LinkedList<String>(this.namespaces.keySet()));
    }
}