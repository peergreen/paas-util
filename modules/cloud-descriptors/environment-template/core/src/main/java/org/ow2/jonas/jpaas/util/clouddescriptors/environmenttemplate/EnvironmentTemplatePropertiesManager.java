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

package org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate;

import org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.nodetemplate.externaldb.ExternalDBVersion;
import org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.nodetemplate.jk.JkVersion;
import org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.nodetemplate.jonas.JonasVersion;
import org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.topology.connector.ConnectorVersion;
import org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.topology.datasource.DatasourceVersion;

import java.util.List;
import java.util.Properties;

/**
 * Reads and manages the properties files
 * @author Mohammed Boukada
 */
public class EnvironmentTemplatePropertiesManager {

    /**
     * Cloud-application properties
     */
    private static Properties properties;

    /**
     * Key for the environment-template 1.x XSD path in the properties file
     */
    private static String ENVIRONMENT_TEMPLATE_1_KEY_XSD = "xsdEnvironmentTemplateV1";

    /**
     * Key for the environment-template 1.x's XML namespace in the properties file
     */
    private static String ENVIRONMENT_TEMPLATE_1_KEY_XMLNS = "xmlnsEnvironmentTemplateV1";

    /**
     * Key for the environment-template XML namespace in the properties file
     */
    private static String ENVIRONMENT_TEMPLATE_1_KEY_SCHEMA_LOCATION = "schemaLocationEnvironmentTemplateV1";

    /**
     * Key for the jk 1.x XSD path in the properties file
     */
    private static String JK_1_KEY_XSD = "xsdJkV1";

    /**
     * Key for the jk 1.x's XML namespace in the properties file
     */
    private static String JK_1_KEY_XMLNS = "xmlnsJkV1";

    /**
     * Key for the jk XML namespace in the properties file
     */
    private static String JK_1_KEY_SCHEMA_LOCATION = "schemaLocationJkV1";

    /**
     * Key for the jonas 1.x XSD path in the properties file
     */
    private static String JONAS_1_KEY_XSD = "xsdJonasV1";

    /**
     * Key for the jonas 1.x's XML namespace in the properties file
     */
    private static String JONAS_1_KEY_XMLNS = "xmlnsJonasV1";

    /**
     * Key for the jonas XML namespace in the properties file
     */
    private static String JONAS_1_KEY_SCHEMA_LOCATION = "schemaLocationJonasV1";

    /**
     * Key for the external-db 1.x XSD path in the properties file
     */
    private static String EXTERNAL_DB_1_KEY_XSD = "xsdExternalDBV1";

    /**
     * Key for the external-db 1.x's XML namespace in the properties file
     */
    private static String EXTERNAL_DB_1_KEY_XMLNS = "xmlnsExternalDBV1";

    /**
     * Key for the external-db XML namespace in the properties file
     */
    private static String EXTERNAL_DB_1_KEY_SCHEMA_LOCATION = "schemaLocationExternalDBV1";

    /**
     * Key for the connector 1.x XSD path in the properties file
     */
    private static String CONNECTOR_1_KEY_XSD = "xsdConnectorV1";

    /**
     * Key for the connector 1.x's XML namespace in the properties file
     */
    private static String CONNECTOR_1_KEY_XMLNS = "xmlnsConnectorV1";

    /**
     * Key for the connector XML namespace in the properties file
     */
    private static String CONNECTOR_1_KEY_SCHEMA_LOCATION = "schemaLocationConnectorV1";

    /**
     * Key for the datasource 1.x XSD path in the properties file
     */
    private static String DATASOURCE_1_KEY_XSD = "xsdDatasourceV1";

    /**
     * Key for the datasource 1.x's XML namespace in the properties file
     */
    private static String DATASOURCE_1_KEY_XMLNS = "xmlnsDatasourceV1";

    /**
     * Key for the datasource XML namespace in the properties file
     */
    private static String DATASOURCE_1_KEY_SCHEMA_LOCATION = "schemaLocationDatasourceV1";

    /**
     * Return the environment-template schema path
     * @param environmentTemplateVersion {@link EnvironmentTemplateVersion}
     * @return the environment-template schema path
     */
    public static String getXsdEnvironmentTemplatePath(final EnvironmentTemplateVersion environmentTemplateVersion) {
        if (EnvironmentTemplateVersion.ENVIRONMENT_TEMPLATE_1.equals(environmentTemplateVersion)) {
            return properties.getProperty(EnvironmentTemplatePropertiesManager.ENVIRONMENT_TEMPLATE_1_KEY_XSD);
        }
        return null;
    }

    /**
     * Return the jk schema path
     * @param jkVersion {@link JkVersion}
     * @return the jk schema path
     */
    public static String getXsdJkPath(final JkVersion jkVersion) {
        if (JkVersion.JK_1.equals(jkVersion)) {
            return properties.getProperty(EnvironmentTemplatePropertiesManager.JK_1_KEY_XSD);
        }
        return null;
    }

    /**
     * Return the jonas schema path
     * @param jonasVersion {@link JonasVersion}
     * @return the jonas schema path
     */
    public static String getXsdJonasPath(final JonasVersion jonasVersion) {
        if (JonasVersion.JONAS_1.equals(jonasVersion)) {
            return properties.getProperty(EnvironmentTemplatePropertiesManager.JONAS_1_KEY_XSD);
        }
        return null;
    }

    /**
     * Return the external-db schema path
     * @param externalDBVersion {@link ExternalDBVersion}
     * @return the external-db schema path
     */
    public static String getXsdExternalDBPath(final ExternalDBVersion externalDBVersion) {
        if (ExternalDBVersion.EXTERNAL_DB_1.equals(externalDBVersion)) {
            return properties.getProperty(EnvironmentTemplatePropertiesManager.EXTERNAL_DB_1_KEY_XSD);
        }
        return null;
    }

    /**
     * Return the connector schema path
     * @param connectorVersion {@link ConnectorVersion}
     * @return the connector schema path
     */
    public static String getXsdConnectorPath(final ConnectorVersion connectorVersion) {
        if (ConnectorVersion.CONNECTOR_1.equals(connectorVersion)) {
            return properties.getProperty(EnvironmentTemplatePropertiesManager.CONNECTOR_1_KEY_XSD);
        }
        return null;
    }

    /**
     * Return the datasource schema path
     * @param datasourceVersion {@link DatasourceVersion}
     * @return the datasource schema path
     */
    public static String getXsdDatasourcePath(final DatasourceVersion datasourceVersion) {
        if (DatasourceVersion.DATASOURCE_1.equals(datasourceVersion)) {
            return properties.getProperty(EnvironmentTemplatePropertiesManager.DATASOURCE_1_KEY_XSD);
        }
        return null;
    }

    /**
     * Return the environment-template XML namespace
     * @param environmentTemplateVersion {@link EnvironmentTemplateVersion}
     * @return the environment-template XML namespace
     */
    public static String getEnvironmentTemplateXMLNS(final EnvironmentTemplateVersion environmentTemplateVersion) {
        if (EnvironmentTemplateVersion.ENVIRONMENT_TEMPLATE_1.equals(environmentTemplateVersion)) {
            return properties.getProperty(EnvironmentTemplatePropertiesManager.ENVIRONMENT_TEMPLATE_1_KEY_XMLNS);
        }
        return null;
    }

    /**
     * Return the jk XML namespace
     * @param jkVersion {@link JkVersion}
     * @return the jk XML namespace
     */
    public static String getJkXMLNS(final JkVersion jkVersion) {
        if (JkVersion.JK_1.equals(jkVersion)) {
            return properties.getProperty(EnvironmentTemplatePropertiesManager.JK_1_KEY_XMLNS);
        }
        return null;
    }

    /**
     * Return the jonas XML namespace
     * @param jonasVersion {@link JonasVersion}
     * @return the jonas XML namespace
     */
    public static String getJonasXMLNS(final JonasVersion jonasVersion) {
        if (JonasVersion.JONAS_1.equals(jonasVersion)) {
            return properties.getProperty(EnvironmentTemplatePropertiesManager.JONAS_1_KEY_XMLNS);
        }
        return null;
    }

    /**
     * Return the external-db XML namespace
     * @param externalDBVersion {@link ExternalDBVersion}
     * @return the external-db XML namespace
     */
    public static String getExternalDBXMLNS(final ExternalDBVersion externalDBVersion) {
        if (ExternalDBVersion.EXTERNAL_DB_1.equals(externalDBVersion)) {
            return properties.getProperty(EnvironmentTemplatePropertiesManager.EXTERNAL_DB_1_KEY_XMLNS);
        }
        return null;
    }

    /**
     * Return the connector XML namespace
     * @param connectorVersion {@link ConnectorVersion}
     * @return the connector XML namespace
     */
    public static String getConnectorXMLNS(final ConnectorVersion connectorVersion) {
        if (ConnectorVersion.CONNECTOR_1.equals(connectorVersion)) {
            return properties.getProperty(EnvironmentTemplatePropertiesManager.CONNECTOR_1_KEY_XMLNS);
        }
        return null;
    }

    /**
     * Return the datasource XML namespace
     * @param datasourceVersion {@link DatasourceVersion}
     * @return the datasource XML namespace
     */
    public static String getDatasourceXMLNS(final DatasourceVersion datasourceVersion) {
        if (DatasourceVersion.DATASOURCE_1.equals(datasourceVersion)) {
            return properties.getProperty(EnvironmentTemplatePropertiesManager.DATASOURCE_1_KEY_XMLNS);
        }
        return null;
    }

    /**
     * Return the environment-template schema location
     * @param environmentTemplateVersion {@link EnvironmentTemplateVersion}
     * @return the environment-template schema location
     */
    public static String getEnvironmentTemplateSchemaLocation(final EnvironmentTemplateVersion environmentTemplateVersion) {
        if (EnvironmentTemplateVersion.ENVIRONMENT_TEMPLATE_1.equals(environmentTemplateVersion)) {
            return properties.getProperty(EnvironmentTemplatePropertiesManager.ENVIRONMENT_TEMPLATE_1_KEY_SCHEMA_LOCATION);
        }
        return null;
    }

    /**
     * Return the jk schema location
     * @param jkVersion {@link JkVersion}
     * @return the jk schema location
     */
    public static String getJkSchemaLocation(final JkVersion jkVersion) {
        if (JkVersion.JK_1.equals(jkVersion)) {
            return properties.getProperty(EnvironmentTemplatePropertiesManager.JK_1_KEY_SCHEMA_LOCATION);
        }
        return null;
    }

    /**
     * Return the jonas schema location
     * @param jonasVersion {@link JonasVersion}
     * @return the jonas schema location
     */
    public static String getJonasSchemaLocation(final JonasVersion jonasVersion) {
        if (JonasVersion.JONAS_1.equals(jonasVersion)) {
            return properties.getProperty(EnvironmentTemplatePropertiesManager.JONAS_1_KEY_SCHEMA_LOCATION);
        }
        return null;
    }

    /**
     * Return the external-db schema location
     * @param externalDBVersion {@link ExternalDBVersion}
     * @return the external-db schema location
     */
    public static String getExternalDBSchemaLocation(final ExternalDBVersion externalDBVersion) {
        if (ExternalDBVersion.EXTERNAL_DB_1.equals(externalDBVersion)) {
            return properties.getProperty(EnvironmentTemplatePropertiesManager.EXTERNAL_DB_1_KEY_SCHEMA_LOCATION);
        }
        return null;
    }

    /**
     * Return the connector schema location
     * @param connectorVersion {@link ConnectorVersion}
     * @return the connector schema location
     */
    public static String getConnectorSchemaLocation(final ConnectorVersion connectorVersion) {
        if (ConnectorVersion.CONNECTOR_1.equals(connectorVersion)) {
            return properties.getProperty(EnvironmentTemplatePropertiesManager.CONNECTOR_1_KEY_SCHEMA_LOCATION);
        }
        return null;
    }

    /**
     * Return the datasource schema location
     * @param datasourceVersion {@link DatasourceVersion}
     * @return the datasource schema location
     */
    public static String getDatasourceSchemaLocation(final DatasourceVersion datasourceVersion) {
        if (DatasourceVersion.DATASOURCE_1.equals(datasourceVersion)) {
            return properties.getProperty(EnvironmentTemplatePropertiesManager.DATASOURCE_1_KEY_SCHEMA_LOCATION);
        }
        return null;
    }

    /**
     * @param namespace A XML namesapce
     * @return the correct environment-template version according to the given namespace
     */
    public static EnvironmentTemplateVersion getEnvironmentTemplateVersion(final String namespace) {
        if (properties.getProperty(EnvironmentTemplatePropertiesManager.ENVIRONMENT_TEMPLATE_1_KEY_XMLNS).equals(namespace)) {
            return EnvironmentTemplateVersion.ENVIRONMENT_TEMPLATE_1;
        }
        return null;
    }

    /**
     * @param namespace A XML namesapce
     * @return the correct jk version according to the given namespace
     */
    public static JkVersion getJkVersion(final String namespace) {
        if (properties.getProperty(EnvironmentTemplatePropertiesManager.JK_1_KEY_XMLNS).equals(namespace)) {
            return JkVersion.JK_1;
        }
        return null;
    }

    /**
     * @param namespace A XML namesapce
     * @return the correct jonas version according to the given namespace
     */
    public static JonasVersion getJonasVersion(final String namespace) {
        if (properties.getProperty(EnvironmentTemplatePropertiesManager.JONAS_1_KEY_XMLNS).equals(namespace)) {
            return JonasVersion.JONAS_1;
        }
        return null;
    }

    /**
     * @param namespace A XML namesapce
     * @return the correct external-db version according to the given namespace
     */
    public static ExternalDBVersion getExternalDBVersion(final String namespace) {
        if (properties.getProperty(EnvironmentTemplatePropertiesManager.EXTERNAL_DB_1_KEY_XMLNS).equals(namespace)) {
            return ExternalDBVersion.EXTERNAL_DB_1;
        }
        return null;
    }

    /**
     * @param namespace A XML namesapce
     * @return the correct connector version according to the given namespace
     */
    public static ConnectorVersion getConnectorVersion(final String namespace) {
        if (properties.getProperty(EnvironmentTemplatePropertiesManager.CONNECTOR_1_KEY_XMLNS).equals(namespace)) {
            return ConnectorVersion.CONNECTOR_1;
        }
        return null;
    }

    /**
     * @param namespace A XML namesapce
     * @return the correct datasource version according to the given namespace
     */
    public static DatasourceVersion getDatasourceVersion(final String namespace) {
        if (properties.getProperty(EnvironmentTemplatePropertiesManager.DATASOURCE_1_KEY_XMLNS).equals(namespace)) {
            return DatasourceVersion.DATASOURCE_1;
        }
        return null;
    }

    /**
     * @param namespaces A list of namespaces
     * @return the correct environment-template version according to the given list of namespaces
     */
    public static EnvironmentTemplateVersion getEnvironmentTemplateVersion(final List<String> namespaces) {
        EnvironmentTemplateVersion environmentTemplateVersion = null;
        for (String namespace: namespaces) {
            environmentTemplateVersion = getEnvironmentTemplateVersion(namespace);
            if (environmentTemplateVersion != null) {
                return environmentTemplateVersion;
            }
        }
        return null;
    }

    /**
     * @param namespaces A list of namespaces
     * @return the correct jk version according to the given list of namespaces
     */
    public static JkVersion getJkVersion(final List<String> namespaces) {
        JkVersion jkVersion = null;
        for (String namespace: namespaces) {
            jkVersion = getJkVersion(namespace);
            if (jkVersion != null) {
                return jkVersion;
            }
        }
        return null;
    }

    /**
     * @param namespaces A list of namespaces
     * @return the correct jonas version according to the given list of namespaces
     */
    public static JonasVersion getJonasVersion(final List<String> namespaces) {
        JonasVersion jonasVersion = null;
        for (String namespace: namespaces) {
            jonasVersion = getJonasVersion(namespace);
            if (jonasVersion != null) {
                return jonasVersion;
            }
        }
        return null;
    }

    /**
     * @param namespaces A list of namespaces
     * @return the correct external-db version according to the given list of namespaces
     */
    public static ExternalDBVersion getExternalDBVersion(final List<String> namespaces) {
        ExternalDBVersion externalDBVersion = null;
        for (String namespace: namespaces) {
            externalDBVersion = getExternalDBVersion(namespace);
            if (externalDBVersion != null) {
                return externalDBVersion;
            }
        }
        return null;
    }

    /**
     * @param namespaces A list of namespaces
     * @return the correct connector version according to the given list of namespaces
     */
    public static ConnectorVersion getConnectorVersion(final List<String> namespaces) {
        ConnectorVersion connectorVersion = null;
        for (String namespace: namespaces) {
            connectorVersion = getConnectorVersion(namespace);
            if (connectorVersion != null) {
                return connectorVersion;
            }
        }
        return null;
    }

    /**
     * @param namespaces A list of namespaces
     * @return the correct datasource version according to the given list of namespaces
     */
    public static DatasourceVersion getDatasourceVersion(final List<String> namespaces) {
        DatasourceVersion datasourceVersion = null;
        for (String namespace: namespaces) {
            datasourceVersion = getDatasourceVersion(namespace);
            if (datasourceVersion != null) {
                return datasourceVersion;
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
