package org.wildfly.swarm.hawkular;

/**
 * @author Bob McWhirter
 */
public class Config {

    private String name;
    private String path;
    private String attribute;

    public Config(String name) {
        this.name = name;
    }

    public String name() {
        return this.name;
    }

    public Config path(String path) {
        this.path = path;
        return this;
    }

    public String path() {
        return this.path;
    }

    public Config attribute(String attribute) {
        this.attribute = attribute;
        return this;
    }

    public String attribute() {
        return this.attribute;
    }
}
