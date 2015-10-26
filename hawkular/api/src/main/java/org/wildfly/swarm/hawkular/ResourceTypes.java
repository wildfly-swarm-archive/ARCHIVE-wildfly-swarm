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
package org.wildfly.swarm.hawkular;

/**
 * @author Bob McWhirter
 */
public class ResourceTypes {

    public static ResourceType WILDFLY_SERVER = new ResourceType("WildFly Server")
            .resourceNameTemplate("WildFly Server [%ManagedServerName] [${jboss.node.name:localhost}]")
            .path("/")
            .metricSet(MetricSets.WILDFLY_MEMORY_METRICS)
            .metricSet(MetricSets.WILDFLY_THREADING_METRICS)
            .metricSet(MetricSets.WILDFLY_AGGREGATED_WEB_METRICS)
            .availSet(AvailSets.SERVER_AVAILABILITY)
            .config(new Config("Hostname")
                    .path("/core-service=server-environment")
                    .attribute("qualified-host-name"))
            .config(new Config("Version")
                    .attribute("release-version"))
            .config(new Config("Bound Address")
                    .path("/socket-binding-group=standard-sockets/socket-binding=http")
                    .attribute("bound-address"));

    public static ResourceType DEPLOYMENT = new ResourceType("Deployment")
            .parent(WILDFLY_SERVER)
            .resourceNameTemplate("Deployment [%2]")
            .path("/deployment=*")
            .metricSet(MetricSets.UNDERTOW_METRICS)
            .availSet(AvailSets.DEPLOYMENT_STATUS);
}
