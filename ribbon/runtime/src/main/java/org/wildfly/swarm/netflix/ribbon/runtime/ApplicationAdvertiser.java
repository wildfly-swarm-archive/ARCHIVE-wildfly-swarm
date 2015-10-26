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
package org.wildfly.swarm.netflix.ribbon.runtime;

import org.jboss.msc.inject.Injector;
import org.jboss.msc.service.Service;
import org.jboss.msc.service.StartContext;
import org.jboss.msc.service.StartException;
import org.jboss.msc.service.StopContext;
import org.jboss.msc.value.InjectedValue;

/**
 * @author Bob McWhirter
 */
public class ApplicationAdvertiser implements Service<Void> {

    private final String appName;
    private InjectedValue<ClusterManager> clusterManagerInjector = new InjectedValue<ClusterManager>();


    public ApplicationAdvertiser(String appName) {
        this.appName = appName;
    }

    @Override
    public void start(StartContext startContext) throws StartException {
        this.clusterManagerInjector.getValue().advertise( this.appName );
    }

    @Override
    public void stop(StopContext stopContext) {
        this.clusterManagerInjector.getValue().unadvertise( this.appName );
    }

    @Override
    public Void getValue() throws IllegalStateException, IllegalArgumentException {
        return null;
    }

    public Injector<ClusterManager> getClusterManagerInjector() {
        return this.clusterManagerInjector;
    }
}
