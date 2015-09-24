package org.wildfly.swarm.hawkular;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bob McWhirter
 */
public class AvailSet {

    private final String name;
    private boolean enabled = true;
    private List<Avail> avails = new ArrayList<>();

    public AvailSet(String name) {
        this.name = name;
    }

    public String name() {
        return this.name;
    }

    public AvailSet enabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public boolean enabled() {
        return this.enabled;
    }

    public AvailSet avail(Avail avail) {
        this.avails.add( avail );
        return this;
    }

    public List<Avail> avails() {
        return this.avails;
    }


}
