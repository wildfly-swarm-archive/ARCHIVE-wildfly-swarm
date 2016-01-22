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

import com.orbitz.consul.Consul;
import com.orbitz.consul.HealthClient;
import com.orbitz.consul.cache.ServiceHealthCache;
import com.orbitz.consul.option.CatalogOptions;
import com.orbitz.consul.option.ImmutableCatalogOptions;
import com.orbitz.consul.util.Jackson;

import java.util.concurrent.TimeUnit;

import org.jboss.msc.service.ServiceActivator;
import org.jboss.msc.service.ServiceActivatorContext;
import org.jboss.msc.service.ServiceRegistryException;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wildfly.swarm.netflix.ribbon.RibbonConsulFraction;

/**
 * @author John Hovell
 */
public class ConsulClusterManagerActivator implements ServiceActivator {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsulClusterManagerActivator.class);

    private final RibbonConsulFraction fraction;

    public ConsulClusterManagerActivator(final RibbonConsulFraction fraction) {
        this.fraction = fraction;
    }
    
    @Override
    public void activate(final ServiceActivatorContext context) throws ServiceRegistryException {
        System.setProperty("ribbon.NIWSServerListClassName", "org.wildfly.swarm.netflix.ribbon.runtime.ClusterServerList");
        System.setProperty("ribbon.NFLoadBalancerRuleClassName", "com.netflix.loadbalancer.RoundRobinRule");

        LOGGER.info("Activitating consul cluster manager...");
        final ResteasyClientBuilder clientBuilder = new ResteasyClientBuilder();
        final HealthClient healthClient;
        try {
            final Consul consul = Consul.newClient(fraction.url(),
                    clientBuilder, Jackson.MAPPER);
            healthClient = consul.healthClient();
        } catch (final Exception e) {
            LOGGER.error("Could not initialize Consul. Ribbon will not function. Is consul running?", e);
            return;
        }
        fraction.services().parallelStream().forEach((s) -> {
            // TODO add datacenter here
            final CatalogOptions options = ImmutableCatalogOptions.builder().tag(s.getTag()).build();
            final ServiceHealthCache healthCache = ServiceHealthCache.newCache(healthClient,
                    s.getName(), true, options, 5);
            healthCache.addListener(new RibbonConsulCacheListener(s));
            try {
               healthCache.start();        
               healthCache.awaitInitialized(5, TimeUnit.SECONDS);
            } catch (final Exception e) {
                LOGGER.info("Did not start consul for service " + s.getName(), e);
            }
        });
    }
}
