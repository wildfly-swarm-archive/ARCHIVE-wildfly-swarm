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
