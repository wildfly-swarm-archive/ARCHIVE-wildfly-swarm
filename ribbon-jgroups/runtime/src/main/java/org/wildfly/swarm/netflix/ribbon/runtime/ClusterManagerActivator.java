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
package org.wildfly.swarm.netflix.ribbon.runtime;

import org.jboss.as.naming.ImmediateManagedReferenceFactory;
import org.jboss.as.naming.ServiceBasedNamingStore;
import org.jboss.as.naming.deployment.ContextNames;
import org.jboss.as.naming.service.BinderService;
import org.jboss.as.network.SocketBinding;
import org.jboss.msc.service.ServiceActivator;
import org.jboss.msc.service.ServiceActivatorContext;
import org.jboss.msc.service.ServiceController;
import org.jboss.msc.service.ServiceName;
import org.jboss.msc.service.ServiceRegistryException;
import org.jboss.msc.service.ServiceTarget;
import org.wildfly.clustering.dispatcher.CommandDispatcherFactory;

/**
 * @author Bob McWhirter
 */
public class ClusterManagerActivator implements ServiceActivator {

    @Override
    public void activate(ServiceActivatorContext context) throws ServiceRegistryException {
        ServiceTarget target = context.getServiceTarget();

        System.setProperty("ribbon.NIWSServerListClassName", "org.wildfly.swarm.netflix.ribbon.runtime.ClusterServerList");
        System.setProperty("ribbon.NFLoadBalancerRuleClassName", "com.netflix.loadbalancer.RoundRobinRule");

        ClusterManager manager = new ClusterManager();

        target.addService(ClusterManager.SERVICE_NAME, manager)
                .addDependency(ServiceName.parse("jboss.clustering.dispatcher.default"), CommandDispatcherFactory.class, manager.getCommandDispatcherFactoryInjector())
                .addDependency(ServiceName.parse("org.wildfly.network.socket-binding.http"), SocketBinding.class, manager.getSocketBindingInjector())
                .install();

        String name = "ribbon/cluster";

        BinderService binderService = new BinderService(name, null, true);
        target.addService(ContextNames.buildServiceName(ContextNames.JBOSS_CONTEXT_SERVICE_NAME, name), binderService)
                .addDependency(ContextNames.JBOSS_CONTEXT_SERVICE_NAME, ServiceBasedNamingStore.class, binderService.getNamingStoreInjector())
                .addInjection(binderService.getManagedObjectInjector(), new ImmediateManagedReferenceFactory(ClusterRegistry.INSTANCE))
                        //.addDependency(ClusterManager.SERVICE_NAME)
                .setInitialMode(ServiceController.Mode.ACTIVE)
                .install();

    }
}
