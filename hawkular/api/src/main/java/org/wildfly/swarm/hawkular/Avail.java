package org.wildfly.swarm.hawkular;

/**
 * @author Bob McWhirter
 */
public class Avail extends Sampler<Avail> {

    private String upRegex;

    public Avail(String name) {
        super( name );
    }

    public Avail upRegex(String upRegex) {
        this.upRegex = upRegex;
        return this;
    }

    public String upRegex() {
        return this.upRegex;
    }

}
