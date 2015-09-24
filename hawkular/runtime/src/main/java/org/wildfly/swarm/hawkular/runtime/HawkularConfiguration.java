package org.wildfly.swarm.hawkular.runtime;

import java.util.ArrayList;
import java.util.List;

import org.jboss.as.controller.PathAddress;
import org.jboss.as.controller.PathElement;
import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ValueExpression;
import org.wildfly.swarm.container.runtime.AbstractServerConfiguration;
import org.wildfly.swarm.hawkular.HawkularFraction;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.ADD;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.EXTENSION;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.OP;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.OP_ADDR;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.SUBSYSTEM;

/**
 * @author Bob McWhirter
 * @author Lance Ball
 */
public class HawkularConfiguration extends AbstractServerConfiguration<HawkularFraction> {

    public HawkularConfiguration() {
        super(HawkularFraction.class);
    }

    @Override
    public HawkularFraction defaultFraction() {
        String host = System.getProperty("swarm.hawkular.host", "localhost");
        String portStr = System.getProperty("swarm.hawkular.port", "8080");
        String username = System.getProperty("swarm.harkular.username");
        String password = System.getProperty("swarm.harkular.password");

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

        PathAddress address = PathAddress.pathAddress(PathElement.pathElement(SUBSYSTEM, "hawkular-monitor"));

        node = new ModelNode();
        node.get(OP_ADDR).set(address.toModelNode());
        node.get(OP).set(ADD);
        node.get("apiJndiName").set("java:global/hawkular/agent/monitor/api");
        node.get("numMetricSchedulerThreads").set(3);
        node.get("numAvailSchedulerThreads").set(3);
        node.get("enabled").set(true);
        list.add(node);

        node = new ModelNode();
        node.get(OP_ADDR).set(address.append("storage-adapter", "default").toModelNode());
        node.get(OP).set(ADD);
        node.get("type").set("HAWKULAR");
        node.get("username").set(fraction.username());
        node.get("password").set(fraction.password());
        node.get("serverOutboundSocketBindingRef").set("hawkular");
        list.add(node);

        node = new ModelNode();
        node.get(OP_ADDR).set(address.append("diagnostics", "default").toModelNode());
        node.get(OP).set(ADD);
        node.get("enabled").set(true);
        node.get("reportTo").set("LOG");
        node.get("interval").set(1);
        node.get("timeUnits").set("minutes");
        list.add(node);

        node = new ModelNode();
        node.get(OP_ADDR).set(address.append("managed-servers", "default").toModelNode());
        node.get(OP).set(ADD);
        list.add(node);

        node = new ModelNode();
        node.get(OP_ADDR).set(address.append("resource-type-set-dmr", "Main").toModelNode());
        node.get(OP).set(ADD);
        node.get("enabled").set(true);
        list.add(node);

        // avail
        node = new ModelNode();
        node.get(OP_ADDR).set(address.append("avail-set-dmr", "Server Availability").toModelNode());
        node.get(OP).set(ADD);
        node.get("enabled").set(true);
        list.add(node);

        node = new ModelNode();
        node.get(OP_ADDR).set(address.append("avail-set-dmr", "Server Availability").append("avail-dmr", "App Server").toModelNode());
        node.get(OP).set(ADD);
        node.get("interval").set(30);
        node.get("timeUnits").set("seconds");
        node.get("path").set("/");
        node.get("attribute").set("server-state");
        node.get("upRegex").set("run.*");
        list.add(node);

        // resource-type
        node = new ModelNode();
        node.get(OP_ADDR).set(address.append("resource-type-set-dmr", "Main").append("resource-type-dmr", "WildFly Server").toModelNode());
        node.get(OP).set(ADD);
        node.get("resourceNameTemplate").set(new ValueExpression("WildFly Server [%ManagedServerName] [${jboss.node.name:localhost}]"));
        node.get("path").set("/");
        node.get("availSets").set("Server Availability");
        list.add(node);

        node = new ModelNode();
        node.get(OP_ADDR).set(address.append("managed-servers", "default").append("local-dmr", "thisthing").toModelNode());
        node.get(OP).set(ADD);
        node.get("enabled").set(true);
        node.get("resourceTypeSets").set("Main");
        list.add(node);

        node = new ModelNode();
        node.get(OP_ADDR).set(PathAddress.pathAddress("socket-binding-group", "default-sockets").append("remote-destination-outbound-socket-binding", "hawkular").toModelNode());
        node.get(OP).set(ADD);
        node.get("host").set(fraction.host());
        node.get("port").set(fraction.port());
        list.add(node);

        System.err.println(list);

        return list;
    }
}
