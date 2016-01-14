/**
 * Copyright 2015-2016 Red Hat, Inc, and individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wildfly.swarm.hawkular.server.runtime;

import java.util.ArrayList;
import java.util.List;

import org.jboss.as.controller.PathAddress;
import org.jboss.as.controller.PathElement;
import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ValueExpression;
import org.wildfly.swarm.SwarmProperties;
import org.wildfly.swarm.container.runtime.AbstractServerConfiguration;
import org.wildfly.swarm.hawkular.server.HawkularServerFraction;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.ADD;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.EXTENSION;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.OP;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.OP_ADDR;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.SUBSYSTEM;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.VALUE;

/**
 * @author Bob McWhirter
 */
public class HawkularServerConfiguration extends AbstractServerConfiguration<HawkularServerFraction> {

    public HawkularServerConfiguration() {
        super(HawkularServerFraction.class);

        deployment("org.hawkular.commons:hawkular-commons-embedded-cassandra-ear:ear:*")
                .as("hawkular-commons-embedded-cassandra-ear.ear");

        deployment("org.keycloak.secretstore:secret-store:war:*")
                .as("hawkular-accounts-secret-store.war");

        deployment("org.hawkular.accounts:hawkular-accounts-events-backend:war:*")
                .as("hawkular-accounts-events-backend.war");

        deployment("org.hawkular.accounts:hawkular-accounts:war:*")
                .as("hawkular-accounts.war");

        deployment("org.hawkular:hawkular-console:war:*")
                .as("hawkular-console.war");
    }

    @Override
    public List<ModelNode> getList(HawkularServerFraction fraction) {
        List<ModelNode> list = new ArrayList<>();

        getNestList(fraction, list);

        return list;
    }

    protected void getNestList(HawkularServerFraction fraction, List<ModelNode> list) {
        ModelNode node = new ModelNode();
        node.get(OP_ADDR).set(EXTENSION, "org.hawkular.nest");
        node.get(OP).set(ADD);
        list.add(node);

        PathAddress address = PathAddress.pathAddress(PathElement.pathElement(SUBSYSTEM, "hawkular-nest"));

        node = new ModelNode();
        node.get(OP_ADDR).set(address.toModelNode());
        node.get(OP).set(ADD);
        node.get("nest-name").set("autogenerate");
        node.get("enabled").set(true);
        list.add(node);

        list.add(property("hawkular.metrics.waitForService",
                          SwarmProperties.propertyVar("hawkular.metrics.waitForService", "True")));

        list.add(property("hawkular.backend",
                          SwarmProperties.propertyVar("hawkular.backend", "embedded_cassandra")));

        list.add(property("keycloak.server.url",
                          SwarmProperties.propertyVar("keycloak.server.url", "http://localhost:8080/auth")));
    }

    protected ModelNode property(String name, String value) {
        ModelNode node = new ModelNode();

        node.get(OP_ADDR).set("system-property", name);
        node.get(OP).set(ADD);
        node.get(VALUE).set(new ValueExpression(value));

        return node;
    }


    /*
    protected void getBusList(HawkularServerFraction fraction, List<ModelNode> list) {
        ModelNode node = new ModelNode();
        //node.get(OP_ADDR).set(EXTENSION, "org.hawkular.bus.broker");
        //node.get(OP).set(ADD);
        //list.add(node);

        PathAddress address = PathAddress.pathAddress(PathElement.pathElement(SUBSYSTEM, "hawkular-bus-broker"));

        node = new ModelNode();
        node.get(OP_ADDR).set(address.toModelNode());
        node.get(OP).set(ADD);
        node.get("enabled").set(true);
        list.add(node);
    }
    */
}
