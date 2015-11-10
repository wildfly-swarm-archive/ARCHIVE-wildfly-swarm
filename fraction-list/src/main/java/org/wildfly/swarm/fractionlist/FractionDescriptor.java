package org.wildfly.swarm.fractionlist;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Bob McWhirter
 */
public class FractionDescriptor {

    private final String groupId;
    private final String artifactId;

    private Set<FractionDescriptor> dependencies = new HashSet<>();

    FractionDescriptor(String groupId, String artifactId) {
        this.groupId = groupId;
        this.artifactId = artifactId;
    }

    void addDependency(FractionDescriptor dep) {
        this.dependencies.add( dep );
    }

    public String getGroupId() {
        return this.groupId;
    }

    public String getArtifactId() {
        return this.artifactId;
    }

    public Set<FractionDescriptor> getDependencies() {
        return Collections.unmodifiableSet(this.dependencies);
    }
}
