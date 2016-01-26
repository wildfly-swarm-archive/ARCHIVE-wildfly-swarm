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

import java.util.List;
import java.util.stream.Collectors;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractServerList;
import com.netflix.loadbalancer.Server;
import org.wildfly.swarm.topology.runtime.Registration;
import org.wildfly.swarm.topology.runtime.TopologyManager;

/**
 * @author Bob McWhirter
 */
public class TopologyServerList extends AbstractServerList<Server> {

    private String appName;

    @Override
    public void initWithNiwsConfig(IClientConfig config) {
        this.appName = config.getClientName();
    }

    @Override
    public List<Server> getInitialListOfServers() {
        return TopologyManager.INSTANCE.registrationsForService(this.appName)
                .stream().flatMap(e -> e.endPoints(Registration.EndPoint.Visibility.PUBLIC)
                        .stream()
                        .map(ep -> new Server(ep.getAddress(), ep.getPort())))
                .collect(Collectors.toList());
    }

    @Override
    public List<Server> getUpdatedListOfServers() {
        return TopologyManager.INSTANCE.registrationsForService(this.appName)
                .stream().flatMap(e -> e.endPoints(Registration.EndPoint.Visibility.PUBLIC)
                        .stream()
                        .map(ep -> new Server(ep.getAddress(), ep.getPort())))
                .collect(Collectors.toList());
    }
}
