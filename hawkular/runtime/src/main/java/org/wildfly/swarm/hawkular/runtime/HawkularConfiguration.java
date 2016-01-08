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
package org.wildfly.swarm.hawkular.runtime;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.jboss.as.controller.PathAddress;
import org.jboss.as.controller.PathElement;
import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ValueExpression;
import org.wildfly.swarm.container.runtime.AbstractServerConfiguration;
import org.wildfly.swarm.container.runtime.Configuration;
import org.wildfly.swarm.hawkular.Avail;
import org.wildfly.swarm.hawkular.AvailSet;
import org.wildfly.swarm.hawkular.Config;
import org.wildfly.swarm.hawkular.HawkularFraction;
import org.wildfly.swarm.hawkular.Metric;
import org.wildfly.swarm.hawkular.MetricSet;
import org.wildfly.swarm.hawkular.ResourceType;
import org.wildfly.swarm.hawkular.ResourceTypeSet;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.ADD;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.EXTENSION;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.OP;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.OP_ADDR;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.SUBSYSTEM;

/**
 * @author Bob McWhirter
 * @author Lance Ball
 */
@Configuration
public class HawkularConfiguration extends AbstractServerConfiguration<HawkularFraction> {

    PathAddress address = PathAddress.pathAddress(PathElement.pathElement(SUBSYSTEM, "hawkular-monitor"));

    private Set<MetricSet> seenMetricSets = new HashSet<>();

    private Set<AvailSet> seenAvailSets = new HashSet<>();

    public HawkularConfiguration() {
        super(HawkularFraction.class);
    }

    @Override
    public HawkularFraction defaultFraction() {
        String host = System.getProperty("swarm.hawkular.host", "localhost");
        String portStr = System.getProperty("swarm.hawkular.port", "8080");
        String username = System.getProperty("swarm.hawkular.username");
        String password = System.getProperty("swarm.hawkular.password");

        int port = Integer.parseInt(portStr);

        return HawkularFraction.createDefaultHawkularFraction(host, port, username, password);
    }

    @Override
    public List<ModelNode> getList(HawkularFraction fraction) {
        if (fraction == null) {
            fraction = defaultFraction();
        }


        List<ModelNode> list = new ArrayList<>();

        ModelNode node = new ModelNode();
        node.get(OP_ADDR).set(EXTENSION, "org.hawkular.agent.monitor");
        node.get(OP).set(ADD);
        list.add(node);

        node = new ModelNode();
        node.get(OP_ADDR).set(this.address.toModelNode());
        node.get(OP).set(ADD);
        node.get("apiJndiName").set("java:global/hawkular/agent/monitor/api");
        node.get("numMetricSchedulerThreads").set(3);
        node.get("numAvailSchedulerThreads").set(3);
        node.get("enabled").set(true);
        list.add(node);

        node = new ModelNode();
        node.get(OP_ADDR).set(this.address.append("storage-adapter", "default").toModelNode());
        node.get(OP).set(ADD);
        node.get("type").set("HAWKULAR");
        node.get("username").set(fraction.username());
        node.get("password").set(fraction.password());
        node.get("serverOutboundSocketBindingRef").set("hawkular");
        list.add(node);

        node = new ModelNode();
        node.get(OP_ADDR).set(this.address.append("diagnostics", "default").toModelNode());
        node.get(OP).set(ADD);
        node.get("enabled").set(true);
        node.get("reportTo").set("LOG");
        node.get("interval").set(1);
        node.get("timeUnits").set("minutes");
        list.add(node);

        addResourceTypeSets(fraction, list);

        node = new ModelNode();
        node.get(OP_ADDR).set(this.address.append("managed-servers", "default").toModelNode());
        node.get(OP).set(ADD);
        list.add(node);

        node = new ModelNode();
        node.get(OP_ADDR).set(this.address.append("managed-servers", "default").append("local-dmr", fraction.name()).toModelNode());
        node.get(OP).set(ADD);
        node.get("enabled").set(true);
        List<String> setNames = fraction.resourceTypeSets().stream().map(e -> e.name()).collect(Collectors.toList());
        node.get("resourceTypeSets").set(String.join(",", setNames));
        list.add(node);

        node = new ModelNode();
        node.get(OP_ADDR).set(PathAddress.pathAddress("socket-binding-group", "default-sockets").append("remote-destination-outbound-socket-binding", "hawkular").toModelNode());
        node.get(OP).set(ADD);
        node.get("host").set(fraction.host());
        node.get("port").set(fraction.port());
        list.add(node);

        return list;
    }


    private void addMetricSets(ResourceTypeSet resourceTypeSet, List<ModelNode> list) {
        for (ResourceType resourceType : resourceTypeSet.resourceTypes()) {
            for (MetricSet metricSet : resourceType.metricSets()) {
                addMetricSet(metricSet, list);
            }
        }
    }

    private void addMetricSet(MetricSet metricSet, List<ModelNode> list) {
        if (this.seenMetricSets.contains(metricSet)) {
            return;
        }
        this.seenMetricSets.add(metricSet);

        ModelNode node = new ModelNode();
        PathAddress setAddr = this.address.append("metric-set-dmr", metricSet.name());
        node.get(OP_ADDR).set(setAddr.toModelNode());
        node.get(OP).set(ADD);
        node.get("enabled").set(true);
        list.add(node);

        for (Metric metric : metricSet.metrics()) {
            addMetric(setAddr, metric, list);
        }
    }

    private void addMetric(PathAddress setAddr, Metric metric, List<ModelNode> list) {
        ModelNode node = new ModelNode();
        node.get(OP_ADDR).set(setAddr.append("metric-dmr", metric.name()).toModelNode());
        node.get(OP).set(ADD);
        node.get("interval").set(metric.interval());
        node.get("timeUnits").set(toString(metric.timeUnit()));
        node.get("path").set(metric.path());
        node.get("attribute").set(metric.attribute());
        if (metric.units() != null) {
            node.get("metricUnits").set(metric.units());
        }
        if (metric.type() != null) {
            node.get("metricType").set(metric.type());
        }
        list.add(node);
    }

    private void addAvailSets(ResourceTypeSet resourceTypeSet, List<ModelNode> list) {
        for (ResourceType resourceType : resourceTypeSet.resourceTypes()) {
            for (AvailSet availSet : resourceType.availSets()) {
                addAvailSet(availSet, list);
            }
        }
    }

    private void addAvailSet(AvailSet availSet, List<ModelNode> list) {
        if (this.seenAvailSets.contains(availSet)) {
            return;
        }
        this.seenAvailSets.add(availSet);

        ModelNode node = new ModelNode();
        PathAddress setAddr = this.address.append("avail-set-dmr", availSet.name());
        node.get(OP_ADDR).set(setAddr.toModelNode());
        node.get(OP).set(ADD);
        node.get("enabled").set(true);
        list.add(node);

        for (Avail avail : availSet.avails()) {
            addAvail(setAddr, avail, list);
        }
    }

    private void addAvail(PathAddress setAddr, Avail avail, List<ModelNode> list) {
        ModelNode node = new ModelNode();
        node.get(OP_ADDR).set(setAddr.append("avail-dmr", avail.name()).toModelNode());
        node.get(OP).set(ADD);
        node.get("interval").set(avail.interval());
        node.get("timeUnits").set(toString(avail.timeUnit()));
        node.get("path").set(avail.path());
        node.get("attribute").set(avail.attribute());
        node.get("upRegex").set(avail.upRegex());
        list.add(node);
    }

    private static String toString(TimeUnit unit) {
        switch (unit) {
            case NANOSECONDS:
                return "nanoseconds";
            case MICROSECONDS:
                return "microseconds";
            case MILLISECONDS:
                return "milliseconds";
            case SECONDS:
                return "seconds";
            case MINUTES:
                return "minutes";
            case HOURS:
                return "hours";
            case DAYS:
                return "days";
        }
        return null;
    }

    protected void addResourceTypeSets(HawkularFraction fraction, List<ModelNode> list) {
        for (ResourceTypeSet resourceTypeSet : fraction.resourceTypeSets()) {
            addMetricSets(resourceTypeSet, list);
            addAvailSets(resourceTypeSet, list);
            addResourceTypeSet(resourceTypeSet, list);
        }
    }

    private void addResourceTypeSet(ResourceTypeSet resourceTypeSet, List<ModelNode> list) {
        ModelNode node = new ModelNode();
        PathAddress setAddr = this.address.append("resource-type-set-dmr", resourceTypeSet.name());
        node.get(OP_ADDR).set(setAddr.toModelNode());
        node.get(OP).set(ADD);
        node.get("enabled").set(true);
        list.add(node);

        for (ResourceType resourceType : resourceTypeSet.resourceTypes()) {
            addResourceType(setAddr, resourceType, list);
        }
    }

    private void addResourceType(PathAddress setAddr, ResourceType resourceType, List<ModelNode> list) {

        ModelNode node = new ModelNode();
        node.get(OP_ADDR).set(setAddr.append("resource-type-dmr", resourceType.name()).toModelNode());
        node.get(OP).set(ADD);
        node.get("resourceNameTemplate").set(new ValueExpression(resourceType.resourceNameTemplate()));
        node.get("path").set(resourceType.path());
        List<String> availSetNames = resourceType.availSets().stream().map(e -> e.name()).collect(Collectors.toList());
        node.get("availSets").set(String.join(",", availSetNames));
        List<String> metricSetNames = resourceType.metricSets().stream().map(e -> e.name()).collect(Collectors.toList());
        node.get("metricSets").set(String.join(",", metricSetNames));
        list.add(node);
        List<String> parentNames = resourceType.parents().stream().map(e -> e.name()).collect(Collectors.toList());
        if ( ! parentNames.isEmpty() ) {
            node.get("parents").set(String.join(",", parentNames));
        }

        for (Config config : resourceType.configs()) {
            node = new ModelNode();
            node.get(OP_ADDR).set(setAddr.append("resource-type-dmr", resourceType.name()).append("resource-config-dmr", config.name()).toModelNode());
            node.get(OP).set(ADD);
            if ( config.path() != null ) {
                node.get( "path" ).set( config.path() );
            }
            if ( config.attribute() != null ) {
                node.get( "attribute" ).set( config.attribute() );
            }
            list.add( node );
        }

    }

}
