package org.wildfly.swarm.hawkular;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bob McWhirter
 */
public class ResourceType {

    private final String name;
    private String resourceNameTemplate;
    private String path;

    private final List<AvailSet> availSets = new ArrayList<>();
    private final List<MetricSet> metricSets = new ArrayList<>();
    private final List<Config> configs = new ArrayList<>();

    private final List<ResourceType> parents = new ArrayList<>();

    public ResourceType(String name) {
        this.name = name;
    }

    public ResourceType parent(ResourceType parent) {
        this.parents.add( parent );
        return this;
    }

    public List<ResourceType> parents() {
        return this.parents;
    }

    public String name() {
        return this.name;
    }

    public ResourceType resourceNameTemplate(String resourceNameTemplate) {
        this.resourceNameTemplate = resourceNameTemplate;
        return this;
    }

    public String resourceNameTemplate() {
        return this.resourceNameTemplate;
    }

    public ResourceType path(String path) {
        this.path = path;
        return this;
    }

    public String path() {
        return this.path;
    }

    public ResourceType availSet(AvailSet availSet) {
        this.availSets.add( availSet );
        return this;
    }

    public List<AvailSet> availSets() {
        return this.availSets;
    }

    public ResourceType metricSet(MetricSet metricSet) {
        this.metricSets.add( metricSet );
        return this;
    }

    public List<MetricSet> metricSets() {
        return this.metricSets;
    }

    public ResourceType config(Config config) {
        this.configs.add( config );
        return this;
    }

    public List<Config> configs() {
        return this.configs;
    }
}
