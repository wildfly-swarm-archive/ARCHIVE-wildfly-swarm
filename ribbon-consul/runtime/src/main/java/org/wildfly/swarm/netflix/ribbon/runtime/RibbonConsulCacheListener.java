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
import java.util.Map;
import java.util.stream.Collectors;

import org.wildfly.swarm.netflix.ribbon.ConsulService;

import com.google.common.net.HostAndPort;
import com.netflix.loadbalancer.Server;
import com.orbitz.consul.cache.ConsulCache;
import com.orbitz.consul.model.health.ServiceHealth;

public class RibbonConsulCacheListener implements ConsulCache.Listener<HostAndPort, ServiceHealth> {
    private final ConsulService consulService;

    public RibbonConsulCacheListener(final ConsulService consulService) {
        super();
        this.consulService = consulService;
    }

    @Override
    public void notify(final Map<HostAndPort, ServiceHealth> newValues) {
        final List<String> previousHostPorts = ClusterRegistry.INSTANCE.getServers(consulService.getName()).stream()
                .map(Server::getHostPort).collect(Collectors.toList());
        final List<String> newHostPorts = newValues.keySet().stream()
                .map(HostAndPort::toString).collect(Collectors.toList());
        // Remove services which are no longer found
        previousHostPorts.stream().filter(h -> !newHostPorts.contains(h))
                .forEach(h -> ClusterRegistry.INSTANCE.unregister(h, consulService.getName()));
        // Add new services that are found
        newValues.forEach((k, v) -> {
            final String newHostPort = k.toString();
            if (!previousHostPorts.contains(newHostPort)) {
                ClusterRegistry.INSTANCE.register(newHostPort, 
                        consulService.getName(), new Server(k.getHostText(), 
                        k.getPort()));
            }
        });
    }

}
