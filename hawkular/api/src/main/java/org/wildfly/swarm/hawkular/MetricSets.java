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

import static org.wildfly.swarm.hawkular.Metrics.*;

/**
 * @author Bob McWhirter
 */
public class MetricSets {

    public static MetricSet WILDFLY_MEMORY_METRICS = new MetricSet("WildFly Memory Metrics")
            .metric(Memory.HEAP_USED)
            .metric(Memory.HEAP_COMMITTED)
            .metric(Memory.HEAP_MAX)
            .metric(Memory.NON_HEAP_USED)
            .metric(Memory.NON_HEAP_COMMITTED)
            .metric(Memory.ACCUMULATED_GC_DURATION);

    public static MetricSet WILDFLY_THREADING_METRICS = new MetricSet("WildFly Threading Metrics")
            .metric(Threading.THREAD_COUNT);

    public static MetricSet WILDFLY_AGGREGATED_WEB_METRICS = new MetricSet("WildFly Aggregated Web Metrics")
            .metric(AggregatedWeb.AGGREGATED_ACTIVE_WEB_SESSIONS)
            .metric(AggregatedWeb.AGGREGATED_EXPIRED_WEB_SESSIONS)
            .metric(AggregatedWeb.AGGREGATED_MAX_ACTIVE_WEB_SESSIONS)
            .metric(AggregatedWeb.AGGREGATED_REJECTED_WEB_SESSIONS)
            .metric(AggregatedWeb.AGGREGATED_SERVLET_REQUEST_COUNT)
            .metric(AggregatedWeb.AGGREGATED_SERVLET_REQUEST_TIME);

    public static MetricSet UNDERTOW_METRICS = new MetricSet("Undertow Metrics")
            .metric(Undertow.ACTIVE_SESSIONS)
            .metric(Undertow.SESSIONS_CREATED)
            .metric(Undertow.EXPIRED_SESSIONS)
            .metric(Undertow.REJECTED_SESSIONS)
            .metric(Undertow.MAX_ACTIVE_SESSIONS);

    public static MetricSet SERVLET_METRICS = new MetricSet("Servlet Metrics" )
            .metric(Servlet.MAX_REQUEST_TIME)
            .metric(Servlet.MIN_REQUEST_TIME)
            .metric(Servlet.REQUEST_COUNT)
            .metric(Servlet.TOTAL_REQUEST_TIME);
}
