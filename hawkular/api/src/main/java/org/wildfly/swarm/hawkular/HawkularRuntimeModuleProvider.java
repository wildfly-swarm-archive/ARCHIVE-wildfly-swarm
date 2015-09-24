package org.wildfly.swarm.hawkular;

import org.wildfly.swarm.container.RuntimeModuleProvider;

/**
 * @author Bob McWhirter
 */
public class HawkularRuntimeModuleProvider implements RuntimeModuleProvider {

    @Override
    public String getModuleName() {
        return "org.wildfly.swarm.hawkular";
    }

    @Override
    public String getSlotName() {
        return "runtime";
    }
}
