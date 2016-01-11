/**
 * Copyright 2015 Red Hat, Inc, and individual contributors.
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
import org.jboss.shrinkwrap.api.Archive;
import org.wildfly.swarm.Swarm;
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
    }

    @Override
    public HawkularServerFraction defaultFraction() {
        return new HawkularServerFraction();
    }

    @Override
    public List<Archive> getImplicitDeployments(HawkularServerFraction fraction) throws Exception {
        List<Archive> list = new ArrayList<>();
        list.add(Swarm.artifact("org.hawkular.commons:hawkular-commons-embedded-cassandra-ear:ear:*", "hawkular-commons-embedded-cassandra-ear.ear"));
        list.add(Swarm.artifact("org.keycloak.secretstore:secret-store:war:*", "hawkular-accounts-secret-store.war"));
        list.add(Swarm.artifact("org.hawkular.accounts:hawkular-accounts-events-backend:war:*", "hawkular-accounts-events-backend.war"));
        list.add(Swarm.artifact("org.hawkular.accounts:hawkular-accounts:war:*", "hawkular-accounts.war"));
        list.add(Swarm.artifact("org.hawkular:hawkular-console:war:*", "hawkular-console.war"));
        return list;
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
                "${hawkular.metrics.waitForService:True}"));

        list.add(property("hawkular.backend",
                "${hawkular.backend:embedded_cassandra}"));

        list.add(property("keycloak.server.url",
                "${keycloak.server.url:http://localhost:8080/auth}"));
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
