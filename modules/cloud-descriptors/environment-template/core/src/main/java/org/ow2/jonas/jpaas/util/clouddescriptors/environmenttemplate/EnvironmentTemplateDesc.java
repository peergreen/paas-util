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

package org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate;

import org.ow2.jonas.jpaas.clouddescriptors.common.AbstractDesc;
import org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.v1.generated.EnvironmentTemplateType;
import org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.connector.v1.generated.ConnectorRelationshipTemplateType;
import org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.datasource.v1.generated.DatasourceRelationshipTemplateType;
import org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.externaldb.v1.generated.ExternalDBNodeTemplateType;
import org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.jk.v1.generated.JkNodeTemplateType;
import org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.jonas.v1.generated.JonasNodeTemplateType;
import org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.nodetemplate.externaldb.ExternalDBVersion;
import org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.nodetemplate.jk.JkVersion;
import org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.nodetemplate.jonas.JonasVersion;
import org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.topology.connector.ConnectorVersion;
import org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.topology.datasource.DatasourceVersion;
import org.ow2.util.log.Log;
import org.ow2.util.log.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.bind.JAXBElement;
import java.io.File;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Describe environment-template
 * @author Mohammed Boukada
 */
public class EnvironmentTemplateDesc extends AbstractDesc {
    /**
    * The logger
    */
    protected static Log logger = LogFactory.getLog(EnvironmentTemplateDesc.class);

    /**
     * Environment-template
     */
    protected Object environmentTemplate;

    /**
     * Version of environment-template
     */
    protected EnvironmentTemplateVersion environmentTemplateVersion;

    /**
     * Version of jk
     */
    protected JkVersion jkVersion;

    /**
     * Version of jonas
     */
    protected JonasVersion jonasVersion;

    /**
     * Version of external-db
     */
    protected ExternalDBVersion externalDBVersion;

    /**
     * Version of connector
     */
    protected ConnectorVersion connectorVersion;

    /**
     * Version of datasource
     */
    protected DatasourceVersion datasourceVersion;

    /**
     * Properties file path (in properties path format)
     */
    private static final String ENVIRONMENT_TEMPLATE_PROPERTIES_NAME = "properties/environment-template.properties";

    /**
     * Default environment-template version
     */
    private static final EnvironmentTemplateVersion DEFAULT_ENVIRONMENT_TEMPLATE_VERSION = EnvironmentTemplateVersion.ENVIRONMENT_TEMPLATE_1;

    /**
     * Default jk version
     */
    private static final JkVersion DEFAULT_JK_VERSION = JkVersion.JK_1;

    /**
     * Default jonas version
     */
    private static final JonasVersion DEFAULT_JONAS_VERSION = JonasVersion.JONAS_1;

    /**
     * Default external-db version
     */
    private static final ExternalDBVersion DEFAULT_EXTERNAL_DB_VERSION = ExternalDBVersion.EXTERNAL_DB_1;

    /**
     * Default connector version
     */
    private static final ConnectorVersion DEFAULT_CONNECTOR_VERSION = ConnectorVersion.CONNECTOR_1;

    /**
     * Default datasource version
     */
    private static final DatasourceVersion DEFAULT_DATASOURCE_VERSION = DatasourceVersion.DATASOURCE_1;

    /**
     * Default constructor
     */
    public EnvironmentTemplateDesc() throws Exception {
        init();
    }

    /**
     * Constructor with xml url
     * @param urlEnvironmentTemplate
     * @throws Exception
     */
    public EnvironmentTemplateDesc(URL urlEnvironmentTemplate) throws Exception {
        init();
        loadEnvironmentTemplate(urlEnvironmentTemplate);
    }

    /**
     * Constructor with xml content
     * @param environmentTemplate
     * @throws Exception
     */
    public EnvironmentTemplateDesc(String environmentTemplate) throws Exception {
        init();
        loadEnvironmentTemplate(environmentTemplate);
    }

    /**
     * Initialization
     * @throws Exception
     */
    private void init() throws Exception {
        this.xsdUrls = new LinkedList<URL> ();
        this.namespaces = new LinkedHashMap<String, Node>();
        EnvironmentTemplatePropertiesManager.setProperties(loadProperties(ENVIRONMENT_TEMPLATE_PROPERTIES_NAME, getClass().getClassLoader()));
    }

    /**
     * Set XSD URLs
     * @throws Exception
     */
    private void setXsdUrls() throws Exception {
        initVersions();
        setXsdUrls(environmentTemplateVersion, jkVersion, jonasVersion, externalDBVersion, connectorVersion, datasourceVersion);
    }

    /**
     * Set XSD URLs
     * @param environmentTemplateVersion
     * @param jkVersion
     * @param jonasVersion
     * @param externalDBVersion
     * @param connectorVersion
     * @param datasourceVersion
     * @throws Exception
     */
    private void setXsdUrls(EnvironmentTemplateVersion environmentTemplateVersion, JkVersion jkVersion, JonasVersion jonasVersion,
                            ExternalDBVersion externalDBVersion, ConnectorVersion connectorVersion, DatasourceVersion datasourceVersion)
                            throws Exception {

        // Get xsd path
        String xsdEnvironmentTemplate = EnvironmentTemplatePropertiesManager.getXsdEnvironmentTemplatePath(environmentTemplateVersion);
        String xsdJk = EnvironmentTemplatePropertiesManager.getXsdJkPath(jkVersion);
        String xsdJonas = EnvironmentTemplatePropertiesManager.getXsdJonasPath(jonasVersion);
        String xsdExternalDB = EnvironmentTemplatePropertiesManager.getXsdExternalDBPath(externalDBVersion);
        String xsdConnector = EnvironmentTemplatePropertiesManager.getXsdConnectorPath(connectorVersion);
        String xsdDatasource = EnvironmentTemplatePropertiesManager.getXsdDatasourcePath(datasourceVersion);

        // Store paths with there corresponding class (classloader)
        Map<String, Class<?>> resources = new LinkedHashMap<String, Class<?>>();
        // Start by adding imported xsd
        resources.put(xsdJk, JkVersion.class);
        resources.put(xsdJonas, JonasVersion.class);
        resources.put(xsdExternalDB, ExternalDBVersion.class);
        resources.put(xsdConnector, ConnectorVersion.class);
        resources.put(xsdDatasource, DatasourceVersion.class);
        resources.put(xsdEnvironmentTemplate, EnvironmentTemplateVersion.class);    // Must be the last added to the map

        xsdUrls = getXsdURL(resources);
    }

    /**
     * Initialize jk, jonas, external-db, connector and datasource versions
     */
    private void initVersions() {
        List<String> namespaces = new LinkedList<String>();
        for (Map.Entry<String, Node> entry : this.namespaces.entrySet()) {
            namespaces.add(entry.getKey());
        }
        jkVersion = EnvironmentTemplatePropertiesManager.getJkVersion(namespaces);
        jonasVersion = EnvironmentTemplatePropertiesManager.getJonasVersion(namespaces);
        externalDBVersion = EnvironmentTemplatePropertiesManager.getExternalDBVersion(namespaces);
        connectorVersion = EnvironmentTemplatePropertiesManager.getConnectorVersion(namespaces);
        datasourceVersion = EnvironmentTemplatePropertiesManager.getDatasourceVersion(namespaces);

        if (jkVersion == null) {
            logger.error("Cannot get the jk version");
        }
        if (jonasVersion == null) {
            logger.error("Cannot get the jonas version");
        }
        if (externalDBVersion == null) {
            logger.error("Cannot get the external-db version");
        }
        if (connectorVersion == null) {
            logger.error("Cannot get the connector version");
        }
        if (datasourceVersion == null) {
            logger.error("Cannot get the datasource version");
        }
    }

    /**
     * Get environment-template
     * @return environment-template
     */
    public Object getEnvironmentTemplate() {
        return environmentTemplate;
    }

    /**
     * Load the environment-template from the url urlEnvironmentTemplate using the schema from
     * schemaUrl.
     *
     * @param urlEnvironmentTemplate Url of the environment-template on xml format
     * @throws Exception
     */
    public void loadEnvironmentTemplate(final URL urlEnvironmentTemplate) throws Exception {
        initEnvironmentTemplateVersion(urlEnvironmentTemplate);
        setXsdUrls();
        EnvironmentTemplateXmlLoader environmentTemplateXmlLoader = new EnvironmentTemplateXmlLoader(urlEnvironmentTemplate,
                this.environmentTemplateVersion, this.xsdUrls);
        this.environmentTemplate = environmentTemplateXmlLoader.getEnvironmentTemplate();
    }

    /**
     * Load the environment-template from the xml string environmentTemplate using the schema from
     * schemaUrl.
     *
     * @param environmentTemplate Url of the environment-template on xml format
     * @throws Exception
     */
    public void loadEnvironmentTemplate(final String environmentTemplate) throws Exception {
        initEnvironmentTemplateVersion(environmentTemplate);
        setXsdUrls();
        EnvironmentTemplateXmlLoader environmentTemplateXmlLoader = new EnvironmentTemplateXmlLoader(environmentTemplate,
                this.environmentTemplateVersion, this.xsdUrls);
        this.environmentTemplate = environmentTemplateXmlLoader.getEnvironmentTemplate();
    }

    /**
     * Generate environment-template xml
     * @param environmentTemplate
     * @return xml content
     * @throws Exception
     */
    public String generateEnvironmentTemplate(JAXBElement<EnvironmentTemplateType> environmentTemplate) throws Exception {
        EnvironmentTemplateXmlLoader environmentTemplateXmlLoader = new EnvironmentTemplateXmlLoader();
        setXsdUrls(DEFAULT_ENVIRONMENT_TEMPLATE_VERSION, DEFAULT_JK_VERSION, DEFAULT_JONAS_VERSION, DEFAULT_EXTERNAL_DB_VERSION,
                   DEFAULT_CONNECTOR_VERSION, DEFAULT_DATASOURCE_VERSION);
        return environmentTemplateXmlLoader.toXml(environmentTemplate, DEFAULT_ENVIRONMENT_TEMPLATE_VERSION, this.xsdUrls);
    }

    /**
     * Initialize the environment-template version which is matching the given xml file
     * @param urlEnvironmentTemplate The environment-template URL
     */
    private void initEnvironmentTemplateVersion(final URL urlEnvironmentTemplate) {
        initEnvironmentTemplateVersion(getFile(urlEnvironmentTemplate));
    }

    /**
     * Initialize the environment-template version which is matching the given xml file
     * @param environmentTemplateFile The environment-template file
     */
    private void initEnvironmentTemplateVersion(final File environmentTemplateFile) {
        initEnvironmentTemplateVersion(getDocument(environmentTemplateFile));
    }

    /**
     * Initialize the environment-template version which is matching the given xml file
     * @param environmentTemplate The content of the environment-template
     */
    private void initEnvironmentTemplateVersion(final String environmentTemplate) {
        initEnvironmentTemplateVersion(getDocument(environmentTemplate));
    }

    /**
     * Initialize the environment-template version which is matching the given xml file
     * @param document The {@link org.w3c.dom.Document} associated to the environment-template
     */
    private void initEnvironmentTemplateVersion(final Document document) {
        if (document != null) {
            getNamespace(document);
        }
        this.environmentTemplateVersion = EnvironmentTemplatePropertiesManager.getEnvironmentTemplateVersion(new LinkedList<String>(this.namespaces.keySet()));
    }

    /**
     * Get nodes template from environment-template
     * @param topologyTemplateList
     * @return
     */
    public List<Object> getNodesTemplate(List<Object> topologyTemplateList) {
        List<Object> nodesTemplate = new LinkedList<Object>();
        for (Object object : topologyTemplateList) {
            if (object instanceof JkNodeTemplateType
                    || object instanceof ExternalDBNodeTemplateType
                    || object instanceof JonasNodeTemplateType) {
                nodesTemplate.add(object);
            }
        }
        return nodesTemplate;
    }

    /**
     * Get relationships template from environment-template
     * @param topologyTemplateList
     * @return
     */
    public List<Object> getRelationshipsTemplate(List<Object> topologyTemplateList) {
        List<Object> relationshipsTemplate = new LinkedList<Object>();
        for (Object object : topologyTemplateList) {
            if (object instanceof ConnectorRelationshipTemplateType
                    || object instanceof DatasourceRelationshipTemplateType) {
                relationshipsTemplate.add(object);
            }
        }
        return relationshipsTemplate;
    }

    /**
     * Whether the object is an instance of JkNodeTemplate
     * @param object
     * @return
     */
    public boolean isJkNodeTemplate(Object object) {
        return object instanceof JkNodeTemplateType;
    }

    /**
     * Whether the object is an instance of ExternalDBNoteTemplate
     * @param object
     * @return
     */
    public boolean isExternalDBNodeTemplate(Object object) {
        return object instanceof ExternalDBNodeTemplateType;
    }

    /**
     * Whether the object is instance of JonasNoteTemplate
     * @param object
     * @return
     */
    public boolean isJonasNodeTemplate(Object object) {
        return object instanceof JonasNodeTemplateType;
    }

    /**
     * Whether the object is an instance of ConnectorRelationshipTemplate
     * @param object
     * @return
     */
    public boolean isConnectorRelationshipTemplate(Object object) {
        return object instanceof ConnectorRelationshipTemplateType;
    }

    /**
     * Whether the object is an instance of DatasourceRelationshipTemplate
     * @param object
     * @return
     */
    public boolean isDatasourceRelationshipTemplate(Object object) {
        return object instanceof DatasourceRelationshipTemplateType;
    }
}
