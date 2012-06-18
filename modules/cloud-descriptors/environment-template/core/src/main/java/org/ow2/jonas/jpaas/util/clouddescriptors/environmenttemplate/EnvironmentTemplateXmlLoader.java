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
