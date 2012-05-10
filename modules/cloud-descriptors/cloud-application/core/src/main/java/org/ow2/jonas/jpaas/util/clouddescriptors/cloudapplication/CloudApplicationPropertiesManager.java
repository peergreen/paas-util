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

import org.ow2.jonas.jpaas.util.clouddescriptors.cloudapplication.deployable.artefact.ArtefactVersion;
import org.ow2.jonas.jpaas.util.clouddescriptors.cloudapplication.deployable.xml.XmlVersion;

import java.util.List;
import java.util.Properties;

/**
 * Reads and manages the properties files
 * @author Mohammed Boukada
 */
public class CloudApplicationPropertiesManager {

    /**
     * Cloud-application properties
     */
    private static Properties properties;

    /**
     * Key for the cloud-application 1.x XSD path in the properties file
     */
    private static String CLOUD_APPLICATION_1_KEY_XSD = "xsdCloudApplicationV1";

    /**
     * Key for the cloud-application 1.x's XML namespace in the properties file
     */
    private static String CLOUD_APPLICATION_1_KEY_XMLNS = "xmlnsCloudApplicationV1";

    /**
     * Key for the cloud-application XML namespace in the properties file
     */
    private static String CLOUD_APPLICATION_1_KEY_SCHEMA_LOCATION = "schemaLocationCloudApplicationV1";

    /**
     * Key for the artefact 1.x XSD path in the properties file
     */
    private static String ARTEFACT_1_KEY_XSD = "xsdArtefactV1";

    /**
     * Key for the artefact 1.x's XML namespace in the properties file
     */
    private static String ARTEFACT_1_KEY_XMLNS = "xmlnsArtefactV1";

    /**
     * Key for the artefact XML namespace in the properties file
     */
    private static String ARTEFACT_1_KEY_SCHEMA_LOCATION = "schemaLocationArtefactV1";

    /**
     * Key for the xml 1.x XSD path in the properties file
     */
    private static String XML_1_KEY_XSD = "xsdXmlV1";

    /**
     * Key for the xml 1.x's XML namespace in the properties file
     */
    private static String XML_1_KEY_XMLNS = "xmlnsXmlV1";

    /**
     * Key for the xml XML namespace in the properties file
     */
    private static String XML_1_KEY_SCHEMA_LOCATION = "schemaLocationXmlV1";


    /**
     * Return the cloud-application schema path
     * @param cloudApplicationVersion {@link CloudApplicationVersion}
     * @return the cloud-application schema path
     */
    public static String getXsdCloudApplicationPath(final CloudApplicationVersion cloudApplicationVersion) {
        if (CloudApplicationVersion.CLOUD_APPLICATION_1.equals(cloudApplicationVersion)) {
            return properties.getProperty(CloudApplicationPropertiesManager.CLOUD_APPLICATION_1_KEY_XSD);
        }
        return null;
    }

    /**
     * Return the artefact schema path
     * @param artefactVersion {@link ArtefactVersion}
     * @return the artefact schema path
     */
    public static String getXsdArtefactPath(final ArtefactVersion artefactVersion) {
        if (ArtefactVersion.ARTEFACT_1.equals(artefactVersion)) {
            return properties.getProperty(CloudApplicationPropertiesManager.ARTEFACT_1_KEY_XSD);
        }
        return null;
    }

    /**
     * Return the xml schema path
     * @param xmlVersion {@link XmlVersion}
     * @return the xml schema path
     */
    public static String getXsdXmlPath(final XmlVersion xmlVersion) {
        if (XmlVersion.XML_1.equals(xmlVersion)) {
            return properties.getProperty(CloudApplicationPropertiesManager.XML_1_KEY_XSD);
        }
        return null;
    }

    /**
     * Return the cloud-application XML namespace
     * @param cloudApplicationVersion {@link CloudApplicationVersion}
     * @return the cloud-application XML namespace
     */
    public static String getCloudApplicationXMLNS(final CloudApplicationVersion cloudApplicationVersion) {
        if (CloudApplicationVersion.CLOUD_APPLICATION_1.equals(cloudApplicationVersion)) {
            return properties.getProperty(CloudApplicationPropertiesManager.CLOUD_APPLICATION_1_KEY_XMLNS);
        }
        return null;
    }

    /**
     * Return the artefact XML namespace
     * @param artefactVersion {@link ArtefactVersion}
     * @return the artefact XML namespace
     */
    public static String getArtefactXMLNS(ArtefactVersion artefactVersion) {
        if (ArtefactVersion.ARTEFACT_1.equals(artefactVersion)) {
            return properties.getProperty(CloudApplicationPropertiesManager.ARTEFACT_1_KEY_XMLNS);
        }
        return null;
    }

    /**
     * Return the xml XML namespace
     * @param xmlVersion {@link XmlVersion}
     * @return the xml XML namespace
     */
    public static String getXmlXMLNS(final XmlVersion xmlVersion) {
        if (XmlVersion.XML_1.equals(xmlVersion)) {
            return properties.getProperty(CloudApplicationPropertiesManager.XML_1_KEY_XMLNS);
        }
        return null;
    }

    /**
     * Return the cloud-application schema location
     * @param cloudApplicationVersion {@link CloudApplicationVersion}
     * @return the cloud-application schema location
     */
    public static String getCloudApplicationSchemaLocation(final CloudApplicationVersion cloudApplicationVersion) {
        if (CloudApplicationVersion.CLOUD_APPLICATION_1.equals(cloudApplicationVersion)) {
            return properties.getProperty(CloudApplicationPropertiesManager.CLOUD_APPLICATION_1_KEY_SCHEMA_LOCATION);
        }
        return null;
    }

    /**
     * Return the artefact schema location
     * @param artefactVersion {@link ArtefactVersion}
     * @return the artefact schema location
     */
    public static String getArtefactSchemaLocation(final ArtefactVersion artefactVersion) {
        if (ArtefactVersion.ARTEFACT_1.equals(artefactVersion)) {
            return properties.getProperty(CloudApplicationPropertiesManager.ARTEFACT_1_KEY_SCHEMA_LOCATION);
        }
        return null;
    }

    /**
     * Return the xml schema location
     * @param xmlVersion {@link XmlVersion}
     * @return the xml schema location
     */
    public static String getCloudApplicationSchemaLocation(final XmlVersion xmlVersion) {
        if (XmlVersion.XML_1.equals(xmlVersion)) {
            return properties.getProperty(CloudApplicationPropertiesManager.XML_1_KEY_SCHEMA_LOCATION);
        }
        return null;
    }

    /**
     * @param namespace A XML namesapce
     * @return the correct cloud-application version according to the given namespace
     */
    public static CloudApplicationVersion getCloudApplicationVersion(final String namespace) {
        if (properties.getProperty(CloudApplicationPropertiesManager.CLOUD_APPLICATION_1_KEY_XMLNS).equals(namespace)) {
            return CloudApplicationVersion.CLOUD_APPLICATION_1;
        }
        return null;
    }

    /**
     * @param namespace A XML namesapce
     * @return the correct artefact version according to the given namespace
     */
    public static ArtefactVersion getArtefactVersion(final String namespace) {
        if (properties.getProperty(CloudApplicationPropertiesManager.ARTEFACT_1_KEY_XMLNS).equals(namespace)) {
            return ArtefactVersion.ARTEFACT_1;
        }
        return null;
    }

    /**
     * @param namespace A XML namesapce
     * @return the correct xml version according to the given namespace
     */
    public static XmlVersion getXmlVersion(final String namespace) {
        if (properties.getProperty(CloudApplicationPropertiesManager.XML_1_KEY_XMLNS).equals(namespace)) {
            return XmlVersion.XML_1;
        }
        return null;
    }

    /**
     * @param namespaces A list of namespaces
     * @return the correct cloud-application version according to the given list of namespaces
     */
    public static CloudApplicationVersion getCloudApplicationVersion(final List<String> namespaces) {
        CloudApplicationVersion cloudApplicationVersion = null;
        for (String namespace: namespaces) {
            cloudApplicationVersion = getCloudApplicationVersion(namespace);
            if (cloudApplicationVersion != null) {
                return cloudApplicationVersion;
            }
        }
        return null;
    }

    /**
     * @param namespaces A list of namespaces
     * @return the correct artefact version according to the given list of namespaces
     */
    public static ArtefactVersion getArtefactVersion(final List<String> namespaces) {
        ArtefactVersion artefactVersion = null;
        for (String namespace: namespaces) {
            artefactVersion = getArtefactVersion(namespace);
            if (artefactVersion != null) {
                return artefactVersion;
            }
        }
        return null;
    }

    /**
     * @param namespaces A list of namespaces
     * @return the correct xml version according to the given list of namespaces
     */
    public static XmlVersion getXmlVersion(final List<String> namespaces) {
        XmlVersion xmlVersion = null;
        for (String namespace: namespaces) {
            xmlVersion = getXmlVersion(namespace);
            if (xmlVersion != null) {
                return xmlVersion;
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
