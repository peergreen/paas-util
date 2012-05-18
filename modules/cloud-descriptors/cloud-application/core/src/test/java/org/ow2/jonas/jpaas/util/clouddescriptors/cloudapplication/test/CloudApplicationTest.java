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

package org.ow2.jonas.jpaas.util.clouddescriptors.cloudapplication.test;

import org.junit.Assert;
import org.junit.Test;
import org.ow2.jonas.jpaas.util.clouddescriptors.cloudapplication.CloudApplicationDesc;
import org.ow2.jonas.jpaas.util.clouddescriptors.cloudapplication.artefact.v1.generated.ArtefactDeployableType;
import org.ow2.jonas.jpaas.util.clouddescriptors.cloudapplication.artefact.v1.generated.ElasticityType;
import org.ow2.jonas.jpaas.util.clouddescriptors.cloudapplication.artefact.v1.generated.HealthType;
import org.ow2.jonas.jpaas.util.clouddescriptors.cloudapplication.artefact.v1.generated.SlaEnforcementType;
import org.ow2.jonas.jpaas.util.clouddescriptors.cloudapplication.v1.generated.CapabilitiesType;
import org.ow2.jonas.jpaas.util.clouddescriptors.cloudapplication.v1.generated.CloudApplicationType;
import org.ow2.jonas.jpaas.util.clouddescriptors.cloudapplication.v1.generated.DeployablesType;
import org.ow2.jonas.jpaas.util.clouddescriptors.cloudapplication.v1.generated.ObjectFactory;
import org.ow2.jonas.jpaas.util.clouddescriptors.cloudapplication.xml.v1.generated.XmlDeployableType;

import java.net.URL;
import java.util.List;

/**
 * Test cloud-application parser
 * @author Mohammed Boukada
 */
public class CloudApplicationTest {

    private final String PATH_EXAMPLE_1 = "xmlExamples/cloud-application-v6.xml";

    private final String PATH_EXAMPLE_2 = "xmlExamples/cloud-application-version-instance-v6.xml";

    private final String PATH_EXAMPLE_3 = "xmlExamples/cloud-application-version-v6.xml";

    @Test
    public void testCloudApplication1() throws Exception {
        URL urlCloudApplication = this.getClass().getClassLoader().getResource(PATH_EXAMPLE_1);
        CloudApplicationDesc desc = new CloudApplicationDesc(urlCloudApplication);
        Assert.assertNotNull("Descriptor cannot be null",desc);

        CloudApplicationType cloudApplication = (CloudApplicationType) desc.getCloudApplication();
        Assert.assertEquals("Wrong name", cloudApplication.getName(), "myapp4prod");
        Assert.assertEquals("Wrong description", cloudApplication.getDescription(), "My App");

        org.ow2.jonas.jpaas.util.clouddescriptors.cloudapplication.v1.generated.RequirementsType requirements = cloudApplication.getRequirements();
        Assert.assertNotNull("Requirements must be not empty", requirements);
        List<String> listRequirements = requirements.getRequirement();
        Assert.assertEquals("Wrong first requirement", listRequirements.get(0), "(multitenancy-level=SharedHardware)");

        CapabilitiesType capabilities = cloudApplication.getCapabilities();
        Assert.assertNotNull("Capabilities must be not empty", capabilities);
        Assert.assertEquals("Wrong application capability", capabilities.getApplication(), "com.myapp");
        Assert.assertEquals("Wrong service capability", capabilities.getService(), "customer-care");

        DeployablesType deployables = cloudApplication.getDeployables();
        Assert.assertNull("Deployables must be empty", deployables);
    }

    @Test
    public void testCloudApplication2() throws Exception {
        URL urlCloudApplication = this.getClass().getClassLoader().getResource(PATH_EXAMPLE_2);
        CloudApplicationDesc desc = new CloudApplicationDesc(urlCloudApplication);
        Assert.assertNotNull("Descriptor cannot be null",desc);

        CloudApplicationType cloudApplication = (org.ow2.jonas.jpaas.util.clouddescriptors.cloudapplication.v1.generated.CloudApplicationType) desc.getCloudApplication();
        Assert.assertEquals("Wrong name", cloudApplication.getName(), "myapp4prod");
        Assert.assertEquals("Wrong description", cloudApplication.getDescription(), "My App");
        Assert.assertEquals("Wrong version", cloudApplication.getVersion(), "1.2");
        Assert.assertEquals("Wrong instance", cloudApplication.getInstance(), "myapp4prod");

        org.ow2.jonas.jpaas.util.clouddescriptors.cloudapplication.v1.generated.RequirementsType requirements = cloudApplication.getRequirements();
        Assert.assertNotNull("Requirements must be not empty", requirements);
        List<String> listRequirements = requirements.getRequirement();
        Assert.assertEquals("Wrong first requirement", listRequirements.get(0), "(multitenancy-level=SharedHardware)");

        CapabilitiesType capabilities = cloudApplication.getCapabilities();
        Assert.assertNotNull("Capabilities must be not empty", capabilities);
        Assert.assertEquals("Wrong application capability", capabilities.getApplication(), "com.myapp");
        Assert.assertEquals("Wrong service capability", capabilities.getService(), "customer-care");

        // Gets deployables
        DeployablesType deployables = cloudApplication.getDeployables();
        Assert.assertNotNull("Deployables must be empty", deployables);
        List<Object> listDeployables = deployables.getXmlDeployableOrArtefactDeployable();

        // Artefact deployable
        Assert.assertTrue("Must be an artefact deployable", (listDeployables.get(0) instanceof ArtefactDeployableType));
        ArtefactDeployableType deployable1 = (ArtefactDeployableType) listDeployables.get(0);
        Assert.assertEquals("Wrong name", deployable1.getName(), "MyWar");
        Assert.assertEquals("Wrong id", deployable1.getId(), "a1");
        Assert.assertEquals("Wrong location", deployable1.getLocation(), "path/to/MyWar");

        org.ow2.jonas.jpaas.util.clouddescriptors.cloudapplication.artefact.v1.generated.RequirementsType artefactRequirements1 =
                deployable1.getRequirements();
        Assert.assertNotNull("Artefact requirements must be not null", artefactRequirements1);
        List<String> listArtefactRequirements1 = artefactRequirements1.getRequirement();
        Assert.assertEquals("Wrong first artefact requirement", listArtefactRequirements1.get(0), "(collocated-to=myear)");
        Assert.assertEquals("Wrong second artefact requirement", listArtefactRequirements1.get(1), "(specification=javaee5)");

        SlaEnforcementType slaEnforcement1 = deployable1.getSlaEnforcement();
        Assert.assertNotNull("Sla enforcement must be not null", slaEnforcement1);
        HealthType health1 = slaEnforcement1.getHealth();
        Assert.assertNotNull("Health must be not null", health1);
        Assert.assertEquals("Wrong url-pattern", health1.getUrlPattern(), "/checkapplication");
        Assert.assertEquals("Wrong jmx indicator", health1.getJmxIndicator(), "indicator");

        ElasticityType elasticity1 = slaEnforcement1.getElasticity();
        Assert.assertNotNull("Elasticity must be not null", elasticity1);
        Assert.assertEquals("Wrong elasticity min", elasticity1.getMin(), "1");
        Assert.assertEquals("Wrong elasticity max", elasticity1.getMax(), "1");

        // Xml deployable
        Assert.assertTrue("Must be an xml deployable", (listDeployables.get(1) instanceof XmlDeployableType));
        XmlDeployableType deployable2 = (XmlDeployableType) listDeployables.get(1);
        Assert.assertEquals("Wrong name", deployable2.getName(), "jmsobjects");
        Assert.assertEquals("Wrong id", deployable2.getId(), "a2");
        String xmlContent = deployable2.getXmlContent();
        Assert.assertEquals("Wrong xml content", xmlContent, "<xml></xml>");
        org.ow2.jonas.jpaas.util.clouddescriptors.cloudapplication.xml.v1.generated.RequirementsType xmlRequirements1 = deployable2.getRequirements();
        Assert.assertNotNull("Xml requirements must be not empty", xmlRequirements1);
        List<String> listXmlRequirements = xmlRequirements1.getRequirement();
        Assert.assertEquals("Wrong first xml requirement", listArtefactRequirements1.get(0), "(collocated-to=myear)");
    }

    @Test
    public void testCloudApplication3() throws Exception {
        URL urlCloudApplication = this.getClass().getClassLoader().getResource(PATH_EXAMPLE_3);
        CloudApplicationDesc desc = new CloudApplicationDesc(urlCloudApplication);
        Assert.assertNotNull("Descriptor cannot be null",desc);

        CloudApplicationType cloudApplication = (org.ow2.jonas.jpaas.util.clouddescriptors.cloudapplication.v1.generated.CloudApplicationType) desc.getCloudApplication();
        Assert.assertEquals("Wrong name", cloudApplication.getName(), "myapp4prod");
        Assert.assertEquals("Wrong description", cloudApplication.getDescription(), "My App");
        Assert.assertEquals("Wrong version", cloudApplication.getVersion(), "1.2");
        Assert.assertNull("Instance must be null in this example", cloudApplication.getInstance());

        org.ow2.jonas.jpaas.util.clouddescriptors.cloudapplication.v1.generated.RequirementsType requirements = cloudApplication.getRequirements();
        Assert.assertNotNull("Requirements must be not empty", requirements);
        List<String> listRequirements = requirements.getRequirement();
        Assert.assertEquals("Wrong first requirement", listRequirements.get(0), "(multitenancy-level=SharedHardware)");

        CapabilitiesType capabilities = cloudApplication.getCapabilities();
        Assert.assertNotNull("Capabilities must be not empty", capabilities);
        Assert.assertEquals("Wrong application capability", capabilities.getApplication(), "com.myapp");
        Assert.assertEquals("Wrong service capability", capabilities.getService(), "customer-care");

        // Gets deployables
        DeployablesType deployables = cloudApplication.getDeployables();
        Assert.assertNotNull("Deployables must be empty", deployables);
        List<Object> listDeployables = deployables.getXmlDeployableOrArtefactDeployable();

        // Artefact deployable
        Assert.assertTrue("Must be an artefact deployable", (listDeployables.get(0) instanceof ArtefactDeployableType));
        ArtefactDeployableType deployable1 = (ArtefactDeployableType) listDeployables.get(0);
        Assert.assertEquals("Wrong name", deployable1.getName(), "MyWar");
        Assert.assertNull("Id must be null in this example", deployable1.getId());
        Assert.assertNull("location must be null in this example", deployable1.getLocation());

        org.ow2.jonas.jpaas.util.clouddescriptors.cloudapplication.artefact.v1.generated.RequirementsType artefactRequirements1 =
                deployable1.getRequirements();
        Assert.assertNotNull("Artefact requirements must be not null", artefactRequirements1);
        List<String> listArtefactRequirements1 = artefactRequirements1.getRequirement();
        Assert.assertEquals("Wrong first artefact requirement", listArtefactRequirements1.get(0), "(collocated-to=myear)");
        Assert.assertEquals("Wrong second artefact requirement", listArtefactRequirements1.get(1), "(specification=javaee5)");
        SlaEnforcementType slaEnforcement1 = deployable1.getSlaEnforcement();
        Assert.assertNotNull("Sla enforcement must be not null", slaEnforcement1);
        Assert.assertNull("Health must be null in this example", slaEnforcement1.getHealth());
        Assert.assertNull("Elasticity must be null in this example", slaEnforcement1.getElasticity());
    }

    @Test
    public void testGenerateCloudApplication() throws Exception {
        CloudApplicationDesc desc = new CloudApplicationDesc();
        CloudApplicationType cloudApplicationType = new CloudApplicationType();
        cloudApplicationType.setName("Application name");
        cloudApplicationType.setDescription("My description");
        org.ow2.jonas.jpaas.util.clouddescriptors.cloudapplication.v1.generated.RequirementsType requirementsType =
                new org.ow2.jonas.jpaas.util.clouddescriptors.cloudapplication.v1.generated.RequirementsType();
        cloudApplicationType.setRequirements(requirementsType);
        CapabilitiesType capabilitiesType = new CapabilitiesType();
        capabilitiesType.setApplication("My application");
        capabilitiesType.setService("My service");
        cloudApplicationType.setCapabilities(capabilitiesType);
        XmlDeployableType xmlDeployableType = new XmlDeployableType();
        xmlDeployableType.setId("xmlid");
        xmlDeployableType.setName("xmlName");
        xmlDeployableType.setXmlContent("<content></content>");
        org.ow2.jonas.jpaas.util.clouddescriptors.cloudapplication.xml.v1.generated.RequirementsType requirementsType1 =
                new org.ow2.jonas.jpaas.util.clouddescriptors.cloudapplication.xml.v1.generated.RequirementsType();
        xmlDeployableType.setRequirements(requirementsType1);
        DeployablesType deployablesType = new DeployablesType();
        List<Object> listDeployables = deployablesType.getXmlDeployableOrArtefactDeployable();
        listDeployables.add(xmlDeployableType);
        cloudApplicationType.setDeployables(deployablesType);

        // Generate xml
        ObjectFactory objectFactory = new ObjectFactory();
        String xml = desc.generateCloudApplication(objectFactory.createCloudApplication(cloudApplicationType));

        CloudApplicationDesc desc1 = new CloudApplicationDesc(xml);
        CloudApplicationType cloudApplication = (CloudApplicationType) desc1.getCloudApplication();
        Assert.assertEquals("Wrong application name", cloudApplication.getName(), "Application name");
        Assert.assertEquals("Wrong application description", cloudApplication.getDescription(), "My description");
    }
}
