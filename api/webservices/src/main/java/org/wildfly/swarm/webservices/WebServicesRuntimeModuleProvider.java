package org.wildfly.swarm.webservices;

import org.wildfly.swarm.container.RuntimeModuleProvider;

/**
 * @author John D. Ament
 */
public class WebServicesRuntimeModuleProvider implements RuntimeModuleProvider {
    @Override
    public String getModuleName() {
        return "org.wildfly.swarm.runtime.webservices";
    }
}
