package org.wildfly.swarm.hawkular;

import static org.wildfly.swarm.hawkular.Metrics.*;

/**
 * @author Bob McWhirter
 */
public class MetricSets {

    public static MetricSet WILDFLY_MEMORY_METRICS = new MetricSet("WildFly Memory Metrics")
            .metric(HEAP_USED)
            .metric(HEAP_COMMITTED)
            .metric(NON_HEAP_USED)
            .metric(NON_HEAP_COMMITTED);
}
