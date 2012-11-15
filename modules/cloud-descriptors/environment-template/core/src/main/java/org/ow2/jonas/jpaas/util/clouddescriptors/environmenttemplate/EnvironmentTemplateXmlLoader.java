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

import org.ow2.jonas.jpaas.clouddescriptors.common.AbstractXmlLoader;
import org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.v1.generated.EnvironmentTemplateType;
import org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.connector.v1.generated.ConnectorRelationshipTemplateType;
import org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.datasource.v1.generated.DatasourceRelationshipTemplateType;
import org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.externaldb.v1.generated.ExternalDBNodeTemplateType;
import org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.jk.v1.generated.JkNodeTemplateType;
import org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.jonas.v1.generated.JonasNodeTemplateType;

import javax.xml.bind.JAXBElement;
import java.net.URL;
import java.util.List;

/**
 * Load the Xml environment-template
 * @author Mohammed Boukada
 */
public class EnvironmentTemplateXmlLoader extends AbstractXmlLoader {

    Object environmentTemplate;

    /**
     * Default constructor
     */
    public EnvironmentTemplateXmlLoader() {}

    /**
     * Constructor. Loads the environment-template
     *
     * @param environmentTemplateURL path to the xml cloud-application file
     * @param environmentTemplateVersion {@link EnvironmentTemplateVersion}
     * @param xsdURLs URLs of XSD schemas
     * @throws Exception
     */
    public EnvironmentTemplateXmlLoader(final URL environmentTemplateURL, final EnvironmentTemplateVersion environmentTemplateVersion,
                                     final List<URL> xsdURLs)
            throws Exception {
        this.environmentTemplate = loadSchemaAndFile(xsdURLs,
                EnvironmentTemplatePropertiesManager.getEnvironmentTemplateXMLNS(environmentTemplateVersion),
                EnvironmentTemplatePropertiesManager.getEnvironmentTemplateSchemaLocation(environmentTemplateVersion), "environment-template",
                environmentTemplateURL, getRootClasses(environmentTemplateVersion));
    }

    /**
     * Constructor. Loads the environment-template
     *
     * @param xml XML to load.
     * @param environmentTemplateVersion {@link EnvironmentTemplateVersion}
     * @param xsdURLs URLs of XSD schemas
     * @throws Exception
     */
    public EnvironmentTemplateXmlLoader(final String xml, final EnvironmentTemplateVersion environmentTemplateVersion,
                                     final List<URL> xsdURLs)
            throws Exception {
        this.environmentTemplate = loadSchemaAndFile(xsdURLs,
                EnvironmentTemplatePropertiesManager.getEnvironmentTemplateXMLNS(environmentTemplateVersion),
                EnvironmentTemplatePropertiesManager.getEnvironmentTemplateSchemaLocation(environmentTemplateVersion), "environment-template",
                xml, getRootClasses(environmentTemplateVersion));
    }

    /**
     * @param environmentTemplateVersion {@link EnvironmentTemplateVersion}
     * @return the correct root class according to the environment-template version
     */
    private Class[] getRootClasses(final EnvironmentTemplateVersion environmentTemplateVersion) {
        if (EnvironmentTemplateVersion.ENVIRONMENT_TEMPLATE_1.equals(environmentTemplateVersion)) {
            return new Class[]{EnvironmentTemplateType.class, ConnectorRelationshipTemplateType.class,
                            DatasourceRelationshipTemplateType.class, JkNodeTemplateType.class,
                            JonasNodeTemplateType.class, ExternalDBNodeTemplateType.class};
        }
        return null;
    }

    /**
     * Generate xml content
     * @param environmentTemplate root element
     * @param environmentTemplateVersion environment-template version
     * @return xml content
     * @throws javax.xml.bind.JAXBException
     */
    public String toXml(JAXBElement<EnvironmentTemplateType> environmentTemplate, EnvironmentTemplateVersion environmentTemplateVersion,
                        final List<URL> xsdURLs) throws Exception {
        return toXml(environmentTemplate, xsdURLs, new EnvironmentTemplateNSPrefixMapper(), getRootClasses(environmentTemplateVersion));
    }

    /**
     * Return the loaded environment-template
     *
     * @return the environment-template
     */
    public Object getEnvironmentTemplate() {
        return environmentTemplate;
    }
}
