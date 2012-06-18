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
