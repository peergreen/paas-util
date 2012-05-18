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
                getRootClass(environmentTemplateVersion), environmentTemplateURL);
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
                getRootClass(environmentTemplateVersion), xml);
    }

    /**
     * @param environmentTemplateVersion {@link EnvironmentTemplateVersion}
     * @return the correct root class according to the environment-template version
     */
    private Class getRootClass(final EnvironmentTemplateVersion environmentTemplateVersion) {
        if (EnvironmentTemplateVersion.ENVIRONMENT_TEMPLATE_1.equals(environmentTemplateVersion)) {
            return EnvironmentTemplateType.class;
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
        return toXml(environmentTemplate, xsdURLs, getRootClass(environmentTemplateVersion), new EnvironmentTemplateNSPrefixMapper());
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
