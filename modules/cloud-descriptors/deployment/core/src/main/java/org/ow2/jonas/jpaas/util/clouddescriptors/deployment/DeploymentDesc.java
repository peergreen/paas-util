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

package org.ow2.jonas.jpaas.util.clouddescriptors.deployment;

import org.ow2.jonas.jpaas.clouddescriptors.common.AbstractDesc;
import org.ow2.jonas.jpaas.util.clouddescriptors.deployment.v1.generated.DeploymentType;
import org.ow2.util.log.Log;
import org.ow2.util.log.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.bind.JAXBElement;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Describe deployment
 * @author Mohammed Boukada
 */
public class DeploymentDesc extends AbstractDesc {
    /**
     * The logger
     */
    protected static Log logger = LogFactory.getLog(DeploymentDesc.class);

    /**
     * Cloud-application
     */
    protected Object deployment;

    /**
     * Version of deployment
     */
    protected DeploymentVersion deploymentVersion;

    /**
     * Properties file path (in properties path format)
     */
    private static final String DEPLOYMENT_PROPERTIES_NAME = "properties/deployment.properties";

    /**
     * Default deployment version
     */
    private static final DeploymentVersion DEFAULT_DEPLOYMENT_VERSION = DeploymentVersion.DEPLOYMENT_1;

    /**
     * Default constructor
     */
    public DeploymentDesc() throws Exception {
        init();
    }

    /**
     * Constructor with xml url
     * @param urlDeployment
     * @throws Exception
     */
    public DeploymentDesc(URL urlDeployment) throws Exception {
        init();
        loadDeployment(urlDeployment);
    }

    /**
     * Constructor with xml content
     * @param deployment
     * @throws Exception
     */
    public DeploymentDesc(String deployment) throws Exception {
        init();
        loadDeployment(deployment);
    }

    /**
     * Initialisation
     * @throws Exception
     */
    private void init() throws Exception {
        this.xsdUrls = new ArrayList<URL>();
        this.namespaces = new LinkedHashMap<String, Node>();
        DeploymentPropertiesManager.setProperties(loadProperties(DEPLOYMENT_PROPERTIES_NAME));
    }

    /**
     * Set XSD URLs
     * @throws Exception
     */
    private void setXsdUrls() throws Exception {
        setXsdUrls(deploymentVersion);
    }

    /**
     * Set XSD URLs
     * @param deploymentVersion
     * @throws Exception
     */
    private void setXsdUrls(DeploymentVersion deploymentVersion) throws Exception {
        String xsdDeployment = DeploymentPropertiesManager.getXsdDeploymentPath(deploymentVersion);
        Map<String, Class<?>> resources = new HashMap<String, Class<?>>();
        resources.put(xsdDeployment, DeploymentVersion.class);
        xsdUrls = getXsdURL(resources);
    }

    /**
     * Load the deployment from the url urlDeployment using the schema from
     * schemaUrl.
     *
     * @param urlDeployment Url of the deployment on xml format
     * @throws Exception
     */
    public void loadDeployment(final URL urlDeployment) throws Exception {
        initDeploymentVersion(urlDeployment);
        setXsdUrls();
        DeploymentXmlLoader deploymentXmlLoader = new DeploymentXmlLoader(urlDeployment, this.deploymentVersion, this.xsdUrls);
        this.deployment = deploymentXmlLoader.getDeployment();
    }

    /**
     * Load the deployment from the xml string deployment using the schema from
     * schemaUrl.
     *
     * @param deployment Url of the deployment on xml format
     * @throws Exception
     */
    public void loadDeployment(final String deployment) throws Exception {
        initDeploymentVersion(deployment);
        setXsdUrls();
        DeploymentXmlLoader deploymentXmlLoader = new DeploymentXmlLoader(deployment, this.deploymentVersion, this.xsdUrls);
        this.deployment = deploymentXmlLoader.getDeployment();
    }

    /**
     * Generate deployment xml
     * @param deployment
     * @return xml content
     * @throws Exception
     */
    public String generateDeployment(JAXBElement<DeploymentType> deployment) throws Exception {
        DeploymentXmlLoader deploymentXmlLoader = new DeploymentXmlLoader();
        setXsdUrls(DEFAULT_DEPLOYMENT_VERSION);
        return deploymentXmlLoader.toXml(deployment, DEFAULT_DEPLOYMENT_VERSION, this.xsdUrls);
    }

    /**
     * Initialize the deployment version which is matching the given xml file
     * @param urlDeployment The deployment URL
     */
    private void initDeploymentVersion(final URL urlDeployment) {
        initDeploymentVersion(getFile(urlDeployment));
    }

    /**
     * Initialize the deployment version which is matching the given xml file
     * @param deploymentFile The deployment file
     */
    private void initDeploymentVersion(final File deploymentFile) {
        initDeploymentVersion(getDocument(deploymentFile));
    }

    /**
     * Initialize the deployment version which is matching the given xml file
     * @param deployment The content of the deployment
     */
    private void initDeploymentVersion(final String deployment) {
        initDeploymentVersion(getDocument(deployment));
    }

    /**
     * Initialize the deployment version which is matching the given xml file
     * @param document The {@link org.w3c.dom.Document} associated to the deployment
     */
    private void initDeploymentVersion(final Document document) {
        if (document != null) {
            getNamespace(document);
        }
        this.deploymentVersion = DeploymentPropertiesManager.getDeploymentVersion(new ArrayList<String>(this.namespaces.keySet()));
    }

    /**
     * Return the loaded deployment
     * @return deployment
     */
    public Object getDeployment() {
        return this.deployment;
    }
}
