/*
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

package org.wildfly.swarm.webservices;

import org.wildfly.swarm.container.Fraction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class WebServicesFraction implements Fraction {
    private HostInfo hostInfo = new HostInfo();
    private List<EndpointConfig> endpointConfigs = new ArrayList<>();
    private List<ClientConfig> clientConfigs = new ArrayList<>();
    public WebServicesFraction() {
        this(new HostInfo());
    }

    public WebServicesFraction(HostInfo hostInfo, Collection<EndpointConfig> endpointConfigs, Collection<ClientConfig> clientConfigs) {
        if(hostInfo != null) {
            this.hostInfo = hostInfo;
        }
        this.endpointConfigs.addAll(endpointConfigs);
        this.clientConfigs.addAll(clientConfigs);
    }

    public WebServicesFraction(HostInfo hostInfo) {
        this(hostInfo, Arrays.asList(EndpointConfig.STANDARD,EndpointConfig.RECORDING), Collections.singleton(ClientConfig.STANDARD));
    }

    public WebServicesFraction hostInfo(HostInfo hostInfo) {
        this.hostInfo = hostInfo;
        return this;
    }

    public WebServicesFraction endpointConfig(EndpointConfig endpointConfig) {
        this.endpointConfigs.add(endpointConfig);
        return this;
    }

    public WebServicesFraction clientConfig(ClientConfig clientConfig) {
        this.clientConfigs.add(clientConfig);
        return this;
    }

    public Collection<EndpointConfig> getEndpointConfigs() {
        return Collections.unmodifiableCollection(endpointConfigs);
    }

    public Collection<ClientConfig> getClientConfigs() {
        return Collections.unmodifiableCollection(clientConfigs);
    }

    public HostInfo getHostInfo() {
        return hostInfo;
    }

}
