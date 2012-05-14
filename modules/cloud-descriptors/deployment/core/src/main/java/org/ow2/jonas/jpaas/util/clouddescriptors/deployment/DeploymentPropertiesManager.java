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

import java.util.List;
import java.util.Properties;

/**
 * Reads and manages the properties files
 * @author Mohammed Boukada
 */
public class DeploymentPropertiesManager {

    /**
     * Cloud-application properties
     */
    private static Properties properties;

    /**
     * Key for the deployment 1.x XSD path in the properties file
     */
    private static String DEPLOYMENT_1_KEY_XSD = "xsdDeploymentV1";

    /**
     * Key for the deployment 1.x's XML namespace in the properties file
     */
    private static String DEPLOYMENT_1_KEY_XMLNS = "xmlnsDeploymentV1";

    /**
     * Key for the deployment XML namespace in the properties file
     */
    private static String DEPLOYMENT_1_KEY_SCHEMA_LOCATION = "schemaLocationDeploymentV1";

    /**
     * Return the deployment schema path
     * @param deploymentVersion {@link DeploymentVersion}
     * @return the deployment schema path
     */
    public static String getXsdDeploymentPath(final DeploymentVersion deploymentVersion) {
        if (DeploymentVersion.DEPLOYMENT_1.equals(deploymentVersion)) {
            return properties.getProperty(DeploymentPropertiesManager.DEPLOYMENT_1_KEY_XSD);
        }
        return null;
    }

    /**
     * Return the deployment XML namespace
     * @param deploymentVersion {@link DeploymentVersion}
     * @return the deployment XML namespace
     */
    public static String getDeploymentXMLNS(final DeploymentVersion deploymentVersion) {
        if (DeploymentVersion.DEPLOYMENT_1.equals(deploymentVersion)) {
            return properties.getProperty(DeploymentPropertiesManager.DEPLOYMENT_1_KEY_XMLNS);
        }
        return null;
    }

    /**
     * Return the deployment schema location
     * @param deploymentVersion {@link DeploymentVersion}
     * @return the deployment schema location
     */
    public static String getDeploymentSchemaLocation(final DeploymentVersion deploymentVersion) {
        if (DeploymentVersion.DEPLOYMENT_1.equals(deploymentVersion)) {
            return properties.getProperty(DeploymentPropertiesManager.DEPLOYMENT_1_KEY_SCHEMA_LOCATION);
        }
        return null;
    }

    /**
     * @param namespace A XML namesapce
     * @return the correct deployment version according to the given namespace
     */
    public static DeploymentVersion getDeploymentVersion(final String namespace) {
        if (properties.getProperty(DeploymentPropertiesManager.DEPLOYMENT_1_KEY_XMLNS).equals(namespace)) {
            return DeploymentVersion.DEPLOYMENT_1;
        }
        return null;
    }

    /**
     * @param namespaces A list of namespaces
     * @return the correct deployment version according to the given list of namespaces
     */
    public static DeploymentVersion getDeploymentVersion(final List<String> namespaces) {
        DeploymentVersion deploymentVersion = null;
        for (String namespace: namespaces) {
            deploymentVersion = getDeploymentVersion(namespace);
            if (deploymentVersion != null) {
                return deploymentVersion;
            }
        }
        return null;
    }

    /**
     * Set properties
     * @param props
     */
    public static void setProperties(Properties props) {
        properties = props;
    }
}
