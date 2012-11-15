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

import org.ow2.jonas.jpaas.clouddescriptors.common.AbstractXmlLoader;
import org.ow2.jonas.jpaas.util.clouddescriptors.deployment.v1.generated.DeploymentType;

import javax.xml.bind.JAXBElement;
import java.net.URL;
import java.util.List;

/**
 * Load the Xml deployment
 * @author Mohammed Boukada
 */
public class DeploymentXmlLoader extends AbstractXmlLoader {

    Object deployment;

    /**
     * Default constructor
     */
    public DeploymentXmlLoader() {}

    /**
     * Constructor. Loads the deployment
     *
     * @param deploymentURL path to the xml cloud-application file
     * @param deploymentVersion {@link DeploymentVersion}
     * @param xsdURLs URLs of XSD schemas
     * @throws Exception
     */
    public DeploymentXmlLoader(final URL deploymentURL, final DeploymentVersion deploymentVersion,
                                     final List<URL> xsdURLs)
            throws Exception {
        this.deployment = loadSchemaAndFile(xsdURLs,
                DeploymentPropertiesManager.getDeploymentXMLNS(deploymentVersion),
                DeploymentPropertiesManager.getDeploymentSchemaLocation(deploymentVersion), "deployment",
                deploymentURL, getRootClass(deploymentVersion));
    }

    /**
     * Constructor. Loads the deployment
     *
     * @param xml XML to load.
     * @param deploymentVersion {@link DeploymentVersion}
     * @param xsdURLs URLs of XSD schemas
     * @throws Exception
     */
    public DeploymentXmlLoader(final String xml, final DeploymentVersion deploymentVersion,
                                     final List<URL> xsdURLs)
            throws Exception {
        this.deployment = loadSchemaAndFile(xsdURLs,
                DeploymentPropertiesManager.getDeploymentXMLNS(deploymentVersion),
                DeploymentPropertiesManager.getDeploymentSchemaLocation(deploymentVersion), "deployment",
                xml, getRootClass(deploymentVersion));
    }

    /**
     * @param deploymentVersion {@link DeploymentVersion}
     * @return the correct root class according to the deployment version
     */
    private Class getRootClass(final DeploymentVersion deploymentVersion) {
        if (DeploymentVersion.DEPLOYMENT_1.equals(deploymentVersion)) {
            return DeploymentType.class;
        }
        return null;
    }

    /**
     * Generate xml content
     * @param deployment root element
     * @param deploymentVersion deployment version
     * @return xml content
     * @throws javax.xml.bind.JAXBException
     */
    public String toXml(JAXBElement<DeploymentType> deployment, DeploymentVersion deploymentVersion,
                        final List<URL> xsdURLs) throws Exception {
        return toXml(deployment, xsdURLs, null ,getRootClass(deploymentVersion));
    }

    /**
     * Returns the loaded deployment
     * @return deployment
     */
    public Object getDeployment() {
        return this.deployment;
    }
}
