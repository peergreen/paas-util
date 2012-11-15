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
        DeploymentPropertiesManager.setProperties(loadProperties(DEPLOYMENT_PROPERTIES_NAME, getClass().getClassLoader()));
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
