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
