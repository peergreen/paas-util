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

import java.net.URL;
import java.util.List;

/**
 * Load the Xml cloud-application
 * @author Mohammed Boukada
 */
public class XmlLoader extends AbstractXmlLoader {

    Object cloudApplication;

    /**
     * Constructor. Loads the cloud-application
     *
     * @param cloudApplicationURL path to the xml cloud-application file
     * @param cloudApplicationVersion {@link CloudApplicationVersion}
     * @param xsdURLs URLs of XSD schemas
     * @throws Exception
     */
    public XmlLoader(final URL cloudApplicationURL, final CloudApplicationVersion cloudApplicationVersion,
                     final List<URL> xsdURLs)
                     throws Exception {
        this.cloudApplication = loadSchemaAndFile(xsdURLs,
                CloudApplicationPropertiesManager.getCloudApplicationXMLNS(cloudApplicationVersion),
                CloudApplicationPropertiesManager.getCloudApplicationSchemaLocation(cloudApplicationVersion), "cloud-application",
                getRootClass(cloudApplicationVersion), cloudApplicationURL);
    }

    /**
     * Constructor. Loads the cloud-application
     *
     * @param xml XML to load.
     * @param cloudApplicationVersion {@link CloudApplicationVersion}
     * @param xsdURLs URLs of XSD schemas
     * @throws Exception
     */
    public XmlLoader(final String xml, final CloudApplicationVersion cloudApplicationVersion,
                     final List<URL> xsdURLs)
            throws Exception {
        this.cloudApplication = loadSchemaAndFile(xsdURLs,
                CloudApplicationPropertiesManager.getCloudApplicationXMLNS(cloudApplicationVersion),
                CloudApplicationPropertiesManager.getCloudApplicationSchemaLocation(cloudApplicationVersion), "cloud-application",
                getRootClass(cloudApplicationVersion), xml);
    }

    /**
     * @param cloudApplicationVersion {@link CloudApplicationVersion}
     * @return the correct root class according to the deployme version
     */
    private Class getRootClass(final CloudApplicationVersion cloudApplicationVersion) {
        if (CloudApplicationVersion.CLOUD_APPLICATION_1.equals(cloudApplicationVersion)) {
            return CloudApplicationType.class;
        }
        return null;
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