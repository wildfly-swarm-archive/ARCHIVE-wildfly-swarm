package org.wildfly.swarm.hawkular;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bob McWhirter
 */
public class ResourceTypeSet {

    private final String name;
    private boolean enabled = true;
    private List<ResourceType> resourceTypes = new ArrayList<>();

    public ResourceTypeSet(String name) {
        this.name = name;
    }

    public String name() {
        return this.name;
    }

    public ResourceTypeSet enabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public boolean enabled() {
        return this.enabled;
    }

    public ResourceTypeSet resourceType(ResourceType resourceType) {
        this.resourceTypes.add( resourceType );
        return this;
    }

    public List<ResourceType> resourceTypes() {
        return this.resourceTypes;
    }
}
