package org.wildfly.swarm.webservices;

import org.wildfly.swarm.container.Fraction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author John D. Ament
 */
public class WebServicesFraction implements Fraction {
    private HostInfo hostInfo = new HostInfo();
    private List<EndpointConfig> endpointConfigs = new ArrayList<>();
    private List<ClientConfig> clientConfigs = new ArrayList<>();
    public WebServicesFraction() {
        this(null);
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
