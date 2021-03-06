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

package org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.test;

import org.junit.Assert;
import org.junit.Test;
import org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.EnvironmentTemplateDesc;
import org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.connector.v1.generated.ConnectorRelationshipTemplateType;
import org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.datasource.v1.generated.DatasourceRelationshipTemplateType;
import org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.externaldb.v1.generated.ExternalDBNodeTemplateType;
import org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.jk.v1.generated.JkNodeTemplateType;
import org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.jonas.v1.generated.JonasNodeTemplateType;
import org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.v1.generated.EnvironmentTemplateType;
import org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.v1.generated.TopologyTemplateType;
import org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.v1.generated.ObjectFactory;

import java.net.URL;
import java.util.List;

/**
 * Test environment-template parser
 * @author Mohammed Boukada
 */
public class EnvironmentTemplateTest {

    private final String PATH_EXAMPLE_1 = "xmlExamples/environment-template-v6.xml";

    @Test
    public void testEnvironmentTemplate() throws Exception {
        URL urlEnvironmentTemplate = this.getClass().getClassLoader().getResource(PATH_EXAMPLE_1);
        EnvironmentTemplateDesc desc = new EnvironmentTemplateDesc(urlEnvironmentTemplate);
        Assert.assertNotNull("Descriptor cannot be null", desc);

        EnvironmentTemplateType environmentTemplate = (EnvironmentTemplateType) desc.getEnvironmentTemplate();
        Assert.assertNotNull("Object cannot be null", environmentTemplate);
        Assert.assertEquals("Wrong name", environmentTemplate.getName(), "myEnv");
        Assert.assertEquals("Wrong description", environmentTemplate.getDescription(), "My Env for prod");
        Assert.assertEquals("Wrong multitenancy-level", environmentTemplate.getMultitenancyLevel(), "SharedHardware");

        TopologyTemplateType topologyTemplate = environmentTemplate.getTopologyTemplate();
        Assert.assertNotNull("Topology-template cannot be null", topologyTemplate);
        List<Object> listTopologyTemplate = topologyTemplate.getNodesOrRelationshipsTemplates();

        // jk
        Assert.assertTrue("First element must be instance of JkNodeTemplateType", (listTopologyTemplate.get(0) instanceof JkNodeTemplateType));
        JkNodeTemplateType jkNodeTemplate = (JkNodeTemplateType) listTopologyTemplate.get(0);
        Assert.assertEquals("Wrong type", jkNodeTemplate.getType(), "router");
        Assert.assertEquals("Wrong id", jkNodeTemplate.getId(), "n1");
        org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.jk.v1.generated.ConfigurationType jkConfig = jkNodeTemplate.getConfiguration();
        Assert.assertNotNull("jk configuration cannot be null", jkConfig);
        Assert.assertEquals("Wrong jk-ref", jkConfig.getJkRef(), "apache-2.2-jk-1.2.25");
        org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.jk.v1.generated.SlaEnforcementType jkSla = jkNodeTemplate.getSlaEnforcement();
        Assert.assertNotNull("jk Sla-enforcement cannot be null", jkSla);
        org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.jk.v1.generated.HealthType jkHealth = jkSla.getHealth();
        Assert.assertNotNull("jk health cannot be null", jkHealth);
        org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.jk.v1.generated.ElasticityType jkElasticity = jkSla.getElasticity();
        Assert.assertNotNull("jk elasticity cannot be null", jkElasticity);
        Assert.assertEquals("Wrong min attribute", jkElasticity.getMin(), 1);
        Assert.assertEquals("Wrong max attribute", jkElasticity.getMax(), 1);

        // jonas
        Assert.assertTrue("Second element must be instance of JonasNodeTemplateType", (listTopologyTemplate.get(1) instanceof JonasNodeTemplateType));
        JonasNodeTemplateType jonasNodeTemplate = (JonasNodeTemplateType) listTopologyTemplate.get(1);
        Assert.assertEquals("Wrong type", jonasNodeTemplate.getType(), "container");
        Assert.assertEquals("Wrong id", jonasNodeTemplate.getId(), "n2");
        org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.jonas.v1.generated.ConfigurationType jonasConfig = jonasNodeTemplate.getConfiguration();
        Assert.assertNotNull("jonas configuration cannot be null", jonasConfig);
        Assert.assertEquals("Wrong java-ref", jonasConfig.getJavaRef(), "openjdk-1.6");
        Assert.assertEquals("Wrong java-options", jonasConfig.getJavaOptions(), "");
        Assert.assertEquals("Wrong jonas-ref", jonasConfig.getJonasRef(), "jonas-full-5.3-javaee5-tomcat7");
        org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.jonas.v1.generated.SlaEnforcementType jonasSla = jonasNodeTemplate.getSlaEnforcement();
        Assert.assertNotNull("jonas Sla-enforcement cannot be null", jonasSla);
        org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.jonas.v1.generated.HealthType jonasHealth = jonasSla.getHealth();
        Assert.assertNotNull("jonas Health cannot be null", jonasHealth);
        Assert.assertEquals("Wrong jmx indicator", jonasHealth.getJmxIndicator(), "jmx1");
        org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.jonas.v1.generated.ElasticityType jonasElasticity = jonasSla.getElasticity();
        Assert.assertNotNull("jonas Elasticity cannot be null", jonasElasticity);
        Assert.assertEquals("Wrong min attribute", jonasElasticity.getMin(), 1);
        Assert.assertEquals("Wrong max attribute", jonasElasticity.getMax(), 5);
        org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.jonas.v1.generated.SessionType jonasSession = jonasElasticity.getSession();
        Assert.assertEquals("Wrong session persistence", jonasSession.isPersistence(), false);
        Assert.assertEquals("Wrong session replication", jonasSession.isReplication(), false);
        org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.jonas.v1.generated.AversionType jonasAversion = jonasElasticity.getAversion();
        Assert.assertNotNull("jonas Aversion cannot be null", jonasAversion);
        org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.jonas.v1.generated.ScalingRulesType jonasScalingRules = jonasElasticity.getScalingRules();
        Assert.assertNotNull("jonas scaling-rules cannot be null", jonasScalingRules);
        org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.jonas.v1.generated.ThroughputType jonasThroughput = jonasScalingRules.getThroughput();
        Assert.assertNotNull("jonas Throughput cannot be null", jonasThroughput);
        Assert.assertEquals("Wrong jmx-indicator", jonasThroughput.getJmxIndicator(), "jmx2");
        Assert.assertEquals("Wrong scaleup-threshold", jonasThroughput.getScaleupThreshold(), "1");
        Assert.assertEquals("Wrong scaledown-threshold", jonasThroughput.getScaledownThreshold(), "2");
        org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.jonas.v1.generated.ResponseTimeType jonasResponseTime = jonasScalingRules.getResponseTime();
        Assert.assertNotNull("jonas ResponseTime cannot be null", jonasResponseTime);
        Assert.assertEquals("Wrong jmx-indicator", jonasResponseTime.getJmxIndicator(), "jmx3");
        Assert.assertEquals("Wrong scaleup-threshold", jonasResponseTime.getScaleupThreshold(), "3");
        Assert.assertEquals("Wrong scaledown-threshold", jonasResponseTime.getScaledownThreshold(), "4");

        // connector
        Assert.assertTrue("Third element must be instance of ConnectorType", (listTopologyTemplate.get(2) instanceof ConnectorRelationshipTemplateType));
        ConnectorRelationshipTemplateType connectorRelationshipTemplate = (ConnectorRelationshipTemplateType) listTopologyTemplate.get(2);
        Assert.assertEquals("Wrong name", connectorRelationshipTemplate.getName(), "router2container");
        Assert.assertEquals("Wrong id", connectorRelationshipTemplate.getId(), "r1");
        org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.connector.v1.generated.RouterType connectorRouter = connectorRelationshipTemplate.getRouter();
        Assert.assertNotNull("connector Router cannot be null", connectorRouter);
        Assert.assertEquals("Wrong router id", connectorRouter.getId(), "n1");
        org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.connector.v1.generated.ContainerType connectorContainer = connectorRelationshipTemplate.getContainer();
        Assert.assertNotNull("connector Container cannot be null", connectorContainer);
        Assert.assertEquals("Wrong container id", connectorContainer.getId(), "n2");
        org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.connector.v1.generated.ConfigurationType connectorConf = connectorRelationshipTemplate.getConfiguration();
        Assert.assertNotNull("connector Configuration cannot be null", connectorConf);

        // external-db
        Assert.assertTrue("Fourth element must be instance of ExternalDBType", (listTopologyTemplate.get(3) instanceof ExternalDBNodeTemplateType));
        ExternalDBNodeTemplateType externalDBNodeTemplate = (ExternalDBNodeTemplateType) listTopologyTemplate.get(3);
        Assert.assertEquals("Wrong type", externalDBNodeTemplate.getType(), "database");
        Assert.assertEquals("Wrong name", externalDBNodeTemplate.getName(), "mydb1");
        Assert.assertEquals("Wrong id", externalDBNodeTemplate.getId(), "n3");
        org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.externaldb.v1.generated.ConfigurationType externalDBConf = externalDBNodeTemplate.getConfiguration();
        Assert.assertNotNull("external-db configuration cannot be null", externalDBConf);
        Assert.assertEquals("Wrong driver location", externalDBConf.getDriverLocation(), "http://jdbc.postgresql.org/download/postgresql-9.1-901.jdbc4.jar");
        Assert.assertEquals("Wrong url", externalDBConf.getUrl(), "url");
        Assert.assertEquals("Wrong classname", externalDBConf.getClassname(), "classname");
        Assert.assertEquals("Wrong user", externalDBConf.getUser(), "user");
        Assert.assertEquals("Wrong pwd", externalDBConf.getPwd(), "password");

        // datasource
        Assert.assertTrue("Fifth element must instance of DatasourceType", (listTopologyTemplate.get(4) instanceof DatasourceRelationshipTemplateType));
        DatasourceRelationshipTemplateType datasourceRelationshipTemplate = (DatasourceRelationshipTemplateType) listTopologyTemplate.get(4);
        Assert.assertEquals("Wrong name", datasourceRelationshipTemplate.getName(), "container2database");
        Assert.assertEquals("Wrong id", datasourceRelationshipTemplate.getId(), "r2");
        org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.datasource.v1.generated.ContainerType datasourceContainer = datasourceRelationshipTemplate.getContainer();
        Assert.assertNotNull("datasource Container cannot be null", datasourceContainer);
        Assert.assertEquals("Wrong container id", datasourceContainer.getId(), "n2");
        org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.datasource.v1.generated.DatabaseType datasourceDatabase = datasourceRelationshipTemplate.getDatabase();
        Assert.assertNotNull("datasource database cannot be null", datasourceDatabase);
        Assert.assertEquals("Wrong database id", datasourceDatabase.getId(), "n3");
        org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.datasource.v1.generated.ConfigurationType datasourceConf = datasourceRelationshipTemplate.getConfiguration();
        Assert.assertNotNull("datasource Configuration cannot be null", datasourceConf);
        Assert.assertEquals("Wronf jndi", datasourceConf.getJndi(), "jdbc_1");
    }

    @Test
    public void testGenerateEnvironmentTemplate() throws Exception {
        EnvironmentTemplateDesc desc = new EnvironmentTemplateDesc();
        EnvironmentTemplateType environmentTemplateType = new EnvironmentTemplateType();
        environmentTemplateType.setName("env name");
        environmentTemplateType.setDescription("env description");
        environmentTemplateType.setMultitenancyLevel("SharedHardware");
        TopologyTemplateType topologyTemplate = new TopologyTemplateType();
        List<Object> listTopologyTemplate = topologyTemplate.getNodesOrRelationshipsTemplates();
        JkNodeTemplateType jkNodeTemplateType = new JkNodeTemplateType();
        jkNodeTemplateType.setId("n1");
        jkNodeTemplateType.setName("myrouter");
        jkNodeTemplateType.setType("container");
        org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.jk.v1.generated.ConfigurationType jkConfig =
                new org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.jk.v1.generated.ConfigurationType();
        jkConfig.setJkRef("apache-2.2-jk-1.2.25");
        org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.jk.v1.generated.SlaEnforcementType jkSla =
                new org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.jk.v1.generated.SlaEnforcementType();
        org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.jk.v1.generated.HealthType jkHealth =
                new org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.jk.v1.generated.HealthType();

        org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.jk.v1.generated.ElasticityType jkElasticity =
                new org.ow2.jonas.jpaas.util.clouddescriptors.environmenttemplate.jk.v1.generated.ElasticityType();
        jkElasticity.setMin(1);
        jkElasticity.setMax(1);
        jkSla.setHealth(jkHealth);
        jkSla.setElasticity(jkElasticity);
        jkNodeTemplateType.setConfiguration(jkConfig);
        jkNodeTemplateType.setSlaEnforcement(jkSla);
        listTopologyTemplate.add(jkNodeTemplateType);
        environmentTemplateType.setTopologyTemplate(topologyTemplate);

        // Generate xml
        ObjectFactory objectFactory = new ObjectFactory();
        String xml = desc.generateEnvironmentTemplate(objectFactory.createEnvironmentTemplate(environmentTemplateType));

        EnvironmentTemplateDesc desc1 = new EnvironmentTemplateDesc(xml);
        EnvironmentTemplateType environmentTemplate = (EnvironmentTemplateType) desc1.getEnvironmentTemplate();
        Assert.assertEquals("Wrong name", environmentTemplate.getName(), "env name");
        Assert.assertEquals("Wrong description", environmentTemplate.getDescription(), "env description");
        Assert.assertEquals("Wrong multitenancy level", environmentTemplate.getMultitenancyLevel(), "SharedHardware");
    }
    
    @Test
    public void testNodesAndRelationships() throws Exception {
        URL urlEnvironmentTemplate = this.getClass().getClassLoader().getResource(PATH_EXAMPLE_1);
        EnvironmentTemplateDesc desc = new EnvironmentTemplateDesc(urlEnvironmentTemplate);
        EnvironmentTemplateType environmentTemplate = (EnvironmentTemplateType) desc.getEnvironmentTemplate();

        TopologyTemplateType topologyTemplate = environmentTemplate.getTopologyTemplate();
        List<Object> listTopologyTemplate = topologyTemplate.getNodesOrRelationshipsTemplates();

        List<Object> listNodesTemplate = desc.getNodesTemplate(listTopologyTemplate);
        Assert.assertTrue("Missing object in nodes template list", listNodesTemplate.contains(listTopologyTemplate.get(0)));
        Assert.assertTrue("Must be an instance of JkNodeTemplate", desc.isJkNodeTemplate(listTopologyTemplate.get(0)));
        Assert.assertTrue("Missing object in nodes template list", listNodesTemplate.contains(listTopologyTemplate.get(1)));
        Assert.assertTrue("Must be an instance of JonasNodeTemplate", desc.isJonasNodeTemplate(listTopologyTemplate.get(1)));
        Assert.assertTrue("Missing object in nodes template list", listNodesTemplate.contains(listTopologyTemplate.get(3)));
        Assert.assertTrue("Must be an instance of ExternalDBNodeTemplate", desc.isExternalDBNodeTemplate(listTopologyTemplate.get(3)));

        List<Object> listRelationshipsTemplate = desc.getRelationshipsTemplate(listTopologyTemplate);
        Assert.assertTrue("Missing object in relationships template list", listRelationshipsTemplate.contains(listTopologyTemplate.get(2)));
        Assert.assertTrue("Must be an instance of ConnectorRelationshipTemplate", desc.isConnectorRelationshipTemplate(listTopologyTemplate.get(2)));
        Assert.assertTrue("Missing object in relationships template list", listRelationshipsTemplate.contains(listTopologyTemplate.get(4)));
        Assert.assertTrue("Must be an instance of DatasouceRelationshipTemplate", desc.isDatasourceRelationshipTemplate(listTopologyTemplate.get(4)));
    }
}
