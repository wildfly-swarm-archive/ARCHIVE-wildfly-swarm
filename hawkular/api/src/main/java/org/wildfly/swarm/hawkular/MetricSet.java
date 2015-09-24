package org.wildfly.swarm.hawkular;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bob McWhirter
 */
public class MetricSet {

    private final String name;
    private boolean enabled = true;
    private List<Metric> metrics = new ArrayList<>();

    public MetricSet(String name) {
        this.name = name;
    }

    public String name() {
        return this.name;
    }

    public MetricSet enabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public boolean enabled() {
        return this.enabled;
    }

    public MetricSet metric(Metric metric) {
        this.metrics.add( metric );
        return this;
    }

    public List<Metric> metrics() {
        return this.metrics;
    }


}
