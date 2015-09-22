package org.wildfly.swarm.messaging.clustered;

import org.wildfly.swarm.container.RuntimeModuleProvider;

public class MessagingClusteredRuntimeModuleProvider implements RuntimeModuleProvider {
    @Override
    public String getModuleName() {
        return "org.wildfly.swarm.messaging.clustered";
    }

    @Override
    public String getSlotName() {
        return "runtime";
    }
}
