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

import org.ow2.jonas.jpaas.clouddescriptors.common.AbstractXmlLoader;
import org.ow2.jonas.jpaas.util.clouddescriptors.cloudapplication.v1.generated.CloudApplicationType;
import org.ow2.jonas.jpaas.util.clouddescriptors.cloudapplication.artefact.v1.generated.ArtefactDeployableType;
import org.ow2.jonas.jpaas.util.clouddescriptors.cloudapplication.xml.v1.generated.XmlDeployableType;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import java.net.URL;
import java.util.List;

/**
 * Load the Xml cloud-application
 * @author Mohammed Boukada
 */
public class CloudApplicationXmlLoader extends AbstractXmlLoader {

    Object cloudApplication;

    /**
     * Default contructor
     */
    public CloudApplicationXmlLoader() {}

    /**
     * Constructor. Loads the cloud-application
     *
     * @param cloudApplicationURL path to the xml cloud-application file
     * @param cloudApplicationVersion {@link CloudApplicationVersion}
     * @param xsdURLs URLs of XSD schemas
     * @throws Exception
     */
    public CloudApplicationXmlLoader(final URL cloudApplicationURL, final CloudApplicationVersion cloudApplicationVersion,
                                     final List<URL> xsdURLs)
                     throws Exception {
        this.cloudApplication = loadSchemaAndFile(xsdURLs,
                CloudApplicationPropertiesManager.getCloudApplicationXMLNS(cloudApplicationVersion),
                CloudApplicationPropertiesManager.getCloudApplicationSchemaLocation(cloudApplicationVersion), "cloud-application",
                cloudApplicationURL, getRootClasses(cloudApplicationVersion));
    }

    /**
     * Constructor. Loads the cloud-application
     *
     * @param xml XML to load.
     * @param cloudApplicationVersion {@link CloudApplicationVersion}
     * @param xsdURLs URLs of XSD schemas
     * @throws Exception
     */
    public CloudApplicationXmlLoader(final String xml, final CloudApplicationVersion cloudApplicationVersion,
                                     final List<URL> xsdURLs)
            throws Exception {
        this.cloudApplication = loadSchemaAndFile(xsdURLs,
                CloudApplicationPropertiesManager.getCloudApplicationXMLNS(cloudApplicationVersion),
                CloudApplicationPropertiesManager.getCloudApplicationSchemaLocation(cloudApplicationVersion), "cloud-application",
                xml, getRootClasses(cloudApplicationVersion));
    }

    /**
     * @param cloudApplicationVersion {@link CloudApplicationVersion}
     * @return the correct root class according to the cloud-application version
     */
    private Class[] getRootClasses(final CloudApplicationVersion cloudApplicationVersion) {
        if (CloudApplicationVersion.CLOUD_APPLICATION_1.equals(cloudApplicationVersion)) {
            return new Class[]{CloudApplicationType.class, ArtefactDeployableType.class, XmlDeployableType.class};
        }
        return null;
    }

    /**
     * Generate xml content
     * @param cloudApplication root element
     * @param cloudApplicationVersion cloud-application version
     * @return xml content
     * @throws JAXBException
     */
    public String toXml(JAXBElement<CloudApplicationType> cloudApplication, CloudApplicationVersion cloudApplicationVersion,
                        final List<URL> xsdURLs) throws Exception {
        return toXml(cloudApplication, xsdURLs, new CloudApplicationNSPrefixMapper(), getRootClasses(cloudApplicationVersion));
    }

    /**
     * Return the loaded cloud-application
     *
     * @return the cloud-application
     */
    public Object getCloudApplication() {
        return cloudApplication;
    }
}