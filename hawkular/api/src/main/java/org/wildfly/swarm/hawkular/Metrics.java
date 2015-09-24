package org.wildfly.swarm.hawkular;

import java.util.concurrent.TimeUnit;

/**
 * @author Bob McWhirter
 */
public class Metrics {

    public static Metric HEAP_USED = new Metric("Heap Used").every(30, TimeUnit.SECONDS)
            .units("bytes")
            .path("/core-service=platform-mbean/type=memory")
            .attribtue("heap-memory-usage#used");

    public static Metric HEAP_COMMITTED = new Metric("Heap Committed").every(1, TimeUnit.MINUTES)
            .path("/core-service=platform-mbean/type=memory")
            .attribtue("heap-memory-usage#committed");

    public static Metric HEAP_MAX = new Metric("Heap Committed").every(1, TimeUnit.MINUTES)
            .path("/core-service=platform-mbean/type=memory")
            .attribtue("heap-memory-usage#max");

    public static Metric NON_HEAP_USED = new Metric("NonHeap Used").every(30, TimeUnit.SECONDS)
            .units("bytes")
            .path("/core-service=platform-mbean/type=memory")
            .attribtue("non-heap-memory-usage#used");

    public static Metric NON_HEAP_COMMITTED = new Metric("NonHeap Committed").every(1, TimeUnit.MINUTES)
            .units("bytes")
            .path("/core-service=platform-mbean/type=memory")
            .attribtue("non-heap-memory-usage#committed");
}
