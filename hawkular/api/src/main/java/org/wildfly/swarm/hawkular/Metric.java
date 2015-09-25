package org.wildfly.swarm.hawkular;

/**
 * @author Bob McWhirter
 */
public class Metric extends Sampler<Metric>{

    private String units;
    private String type;

    public Metric(String name) {
        super( name );
    }

    public Metric units(String units) {
        this.units = units;
        return this;
    }

    public String units() {
        return this.units;
    }

    public Metric type(String type) {
        this.type = type;
        return this;
    }

    public String type() {
        return this.type;
    }



}
