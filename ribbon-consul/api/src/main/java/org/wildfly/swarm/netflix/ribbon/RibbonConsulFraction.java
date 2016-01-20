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
package org.wildfly.swarm.netflix.ribbon;

import java.util.ArrayList;
import java.util.List;

import org.wildfly.swarm.container.Fraction;

/**
 * @author John Hovell
 */
public class RibbonConsulFraction implements Fraction {
    private final List<ConsulService> services = new ArrayList<>();
    // Default Consul port/host 
    private String url = "http://localhost:8500";
    
    public RibbonConsulFraction services(final List<ConsulService> services) {
        this.services.addAll(services);
        return this;
    }
    
    public List<ConsulService> services() {
        return services;
    }
    
    public RibbonConsulFraction url(final String url) {
        this.url = url;
        return this;
    }
    
    public String url() {
        return url;
    }
}
