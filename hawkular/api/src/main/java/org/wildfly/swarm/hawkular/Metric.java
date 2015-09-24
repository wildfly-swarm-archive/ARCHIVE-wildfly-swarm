package org.wildfly.swarm.hawkular;

/**
 * @author Bob McWhirter
 */
public class Metric extends Sampler<Metric>{

    private String units;

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

}
