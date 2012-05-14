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

package org.ow2.jonas.jpaas.util.clouddescriptors.deployment.test;

import org.junit.Assert;
import org.junit.Test;
import org.ow2.jonas.jpaas.util.clouddescriptors.deployment.DeploymentDesc;
import org.ow2.jonas.jpaas.util.clouddescriptors.deployment.v1.generated.DeploymentType;
import org.ow2.jonas.jpaas.util.clouddescriptors.deployment.v1.generated.DeploymentMapType;
import org.ow2.jonas.jpaas.util.clouddescriptors.deployment.v1.generated.EntryType;

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
}
