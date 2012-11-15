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

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

/**
 * Customize namespace prefixes generated by JAXB
 * @author Mohammed Boukada
 */
public class CloudApplicationNSPrefixMapper extends NamespacePrefixMapper {

    /**
     * Cloud application namespace prefix
     */
    private final String CLOUD_APPLICATION_PREFIX = "";

    /**
     * Artefact namespace prefix
     */
    private final String ARTEFACT_PREFIX = "artefact";

    /**
     * Xml namespace prefix
     */
    private final String XML_PREFIX = "xml-embedded";

    @Override
    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
        Object version = CloudApplicationPropertiesManager.getCloudApplicationVersion(namespaceUri);
        if (version != null) {
            // namespaceURI corresponds to cloud-application
            return CLOUD_APPLICATION_PREFIX;
        } else {
            version = CloudApplicationPropertiesManager.getArtefactVersion(namespaceUri);
            if (version != null) {
                // namespaceURI corresponds to artefact
                return ARTEFACT_PREFIX;
            } else {
                version = CloudApplicationPropertiesManager.getXmlVersion(namespaceUri);
                if (version != null) {
                    // namespaceURI corresponds to xml
                    return XML_PREFIX;
                }
            }
        }
        return suggestion;
    }
}
