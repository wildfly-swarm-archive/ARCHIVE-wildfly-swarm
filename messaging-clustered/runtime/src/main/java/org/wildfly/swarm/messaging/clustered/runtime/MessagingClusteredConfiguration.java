package org.wildfly.swarm.messaging.clustered.runtime;

import org.jboss.as.controller.PathAddress;
import org.jboss.dmr.ModelNode;
import org.wildfly.swarm.messaging.MessagingServer;
import org.wildfly.swarm.messaging.runtime.MessagingConfiguration;

import java.util.Arrays;
import java.util.List;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.*;

public class MessagingClusteredConfiguration extends MessagingConfiguration {

    @Override
    public int priority() {
        return 1;
    }

    @Override
    protected void addServer(MessagingServer server, List<ModelNode> list) {
        super.addServer(server, list);

        PathAddress serverAddress = this.address.append("server", server.name());

        // TODO: lots of hardcoded assumptions here

        ModelNode node = new ModelNode();
        node.get(OP_ADDR).set(serverAddress.toModelNode());
        node.get(OP).set(WRITE_ATTRIBUTE_OPERATION);
        node.get("name").set("cluster-password");
        node.get("value").set("CHANGE ME !!");
        list.add(node);

        node = new ModelNode();
        node.get(OP_ADDR).set(serverAddress.append("http-connector", "http-connector").toModelNode());
        node.get(OP).set(ADD);
        node.get(SOCKET_BINDING).set("http");
        node.get("params").get("http-upgrade-endpoint").set("http-acceptor");
        list.add(node);

        node = new ModelNode();
        node.get(OP_ADDR).set(serverAddress.append("http-acceptor", "http-acceptor").toModelNode());
        node.get(OP).set(ADD);
        node.get("http-listener").set("default");
        list.add(node);

        node = new ModelNode();
        node.get(OP_ADDR).set(serverAddress.append("broadcast-group", "bg-group1").toModelNode());
        node.get(OP).set(ADD);
        node.get("connectors").setEmptyList().add("http-connector");
        node.get("jgroups-stack").set("udp");
        node.get("jgroups-channel").set("activemq-cluster");
        list.add(node);

        node = new ModelNode();
        node.get(OP_ADDR).set(serverAddress.append("discovery-group", "dg-group1").toModelNode());
        node.get(OP).set(ADD);
        node.get("jgroups-stack").set("udp");
        node.get("jgroups-channel").set("activemq-cluster");
        list.add(node);

        node = new ModelNode();
        node.get(OP_ADDR).set(serverAddress.append("cluster-connection", "my-cluster").toModelNode());
        node.get(OP).set(ADD);
        node.get("cluster-connection-address").set("jms");
        node.get("connector-name").set("http-connector");
        node.get("discovery-group").set("dg-group1");
        list.add(node);

        node = new ModelNode();
        node.get(OP_ADDR).set(serverAddress.append("connection-factory", "RemoteConnectionFactory").toModelNode());
        node.get(OP).set(ADD);
        node.get("ha").set(true);
        node.get("block-on-acknowledge").set(true);
        node.get("reconnect-attempts").set(-1);
        node.get("connectors").setEmptyList().add("http-connector");
        node.get("entries").setEmptyList().add("java:jboss/exported/jms/RemoteConnectionFactory");
        list.add(node);
    }
}
