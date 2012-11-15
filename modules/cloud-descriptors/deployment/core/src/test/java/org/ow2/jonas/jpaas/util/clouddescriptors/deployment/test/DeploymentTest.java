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

package org.ow2.jonas.jpaas.util.clouddescriptors.deployment.test;

import org.junit.Assert;
import org.junit.Test;
import org.ow2.jonas.jpaas.util.clouddescriptors.deployment.DeploymentDesc;
import org.ow2.jonas.jpaas.util.clouddescriptors.deployment.v1.generated.DeploymentMapType;
import org.ow2.jonas.jpaas.util.clouddescriptors.deployment.v1.generated.DeploymentType;
import org.ow2.jonas.jpaas.util.clouddescriptors.deployment.v1.generated.EntryType;
import org.ow2.jonas.jpaas.util.clouddescriptors.deployment.v1.generated.ObjectFactory;

import java.net.URL;
import java.util.List;

/**
 * Test deployment parser
 * @author Mohammed Boukada
 */
public class DeploymentTest {

    private final String PATH_EXAMPLE_1 = "xmlExamples/deployment-v6.xml";

    @Test
    public void testDeployment() throws Exception {
        URL urlDeployment = this.getClass().getClassLoader().getResource(PATH_EXAMPLE_1);
        DeploymentDesc desc = new DeploymentDesc(urlDeployment);
        Assert.assertNotNull("Descriptor cannot be null", desc);

        DeploymentType deployment = (DeploymentType) desc.getDeployment();
        Assert.assertEquals("Wrong application", deployment.getApplication(), "myapp4prod");
        Assert.assertEquals("Wrong environment", deployment.getEnvironment(), "MyEnv");

        DeploymentMapType map = deployment.getDeploymentMap();
        Assert.assertNotNull("Map cannot be null", map);

        List<EntryType> listEntries = map.getEntry();
        Assert.assertNotNull("Entries list cannot be null", listEntries);
        
        EntryType entry1 = listEntries.get(0);
        Assert.assertEquals("Wrong deployable for the first entry", entry1.getDeployable(), "a1");
        Assert.assertEquals("Wrong node for the first entry", entry1.getNode(), "n1");
        EntryType entry2 = listEntries.get(1);
        Assert.assertEquals("Wrong deployable for the second entry", entry2.getDeployable(), "a2");
        Assert.assertEquals("Wrong node for the second entry", entry2.getNode(), "n1");
        EntryType entry3 = listEntries.get(2);
        Assert.assertEquals("Wrong deployable for the third entry", entry3.getDeployable(), "a3");
        Assert.assertEquals("Wrong node for the third entry", entry3.getNode(), "n2");
        EntryType entry4 = listEntries.get(3);
        Assert.assertEquals("Wrong deployable for the fourth entry", entry4.getDeployable(), "a4");
        Assert.assertEquals("Wrong node for the fourth entry", entry4.getNode(), "n2");
    }
    
    @Test
    public void testGenerateDeployment() throws Exception {
        DeploymentDesc desc = new DeploymentDesc();
        DeploymentType deploymentType = new DeploymentType();
        deploymentType.setApplication("My app");
        deploymentType.setEnvironment("My env");
        DeploymentMapType deploymentMap = new DeploymentMapType();
        List<EntryType> listEntries = deploymentMap.getEntry();
        EntryType entry1 = new EntryType();
        entry1.setDeployable("dep1");
        entry1.setNode("node1");
        listEntries.add(entry1);
        EntryType entry2 = new EntryType();
        entry2.setDeployable("dep2");
        entry2.setNode("node2");
        listEntries.add(entry2);
        deploymentType.setDeploymentMap(deploymentMap);
        
        // Generate xml 
        ObjectFactory objectFactory = new ObjectFactory();
        String xml = desc.generateDeployment(objectFactory.createDeployment(deploymentType));

        DeploymentDesc desc1 = new DeploymentDesc(xml);
        DeploymentType deployment = (DeploymentType) desc1.getDeployment();
        Assert.assertEquals("Wrong application name", deployment.getApplication(), "My app");
        Assert.assertEquals("Wrong environment name", deployment.getEnvironment(), "My env");

    }
}
