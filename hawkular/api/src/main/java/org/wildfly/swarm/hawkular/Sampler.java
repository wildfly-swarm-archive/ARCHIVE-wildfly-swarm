package org.wildfly.swarm.hawkular;

import java.util.concurrent.TimeUnit;

/**
 * @author Bob McWhirter
 */
public class Sampler<T extends Sampler> {

    private String name;

    private String path;
    private String attribute;

    private long interval;
    private TimeUnit timeUnit;

    protected Sampler(String name) {
        this.name = name;
    }

    public String name() {
        return this.name;
    }

    public T every(long interval, TimeUnit timeUnit) {
        this.interval = interval;
        this.timeUnit = timeUnit;
        return (T) this;
    }

    public long interval() {
        return this.interval;
    }

    public TimeUnit timeUnit() {
        return this.timeUnit;
    }

    public T path(String path) {
        this.path = path;
        return (T) this;
    }

    public String path() {
        return this.path;
    }

    public T attribtue(String attribute) {
        this.attribute = attribute;
        return (T) this;
    }

    public String attribute() {
        return this.attribute;
    }


}
