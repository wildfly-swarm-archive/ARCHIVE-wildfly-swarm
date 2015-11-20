package org.wildfly.swarm.fractionlist;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Bob McWhirter
 */
public class FractionList {

    private Map<String,FractionDescriptor> descriptors = new HashMap<>();

    public FractionList() {
        try (BufferedReader reader = new BufferedReader( new InputStreamReader(getClass().getClassLoader().getResourceAsStream("fraction-list.txt") ))) {

            String line = null;

            while ( ( line = reader.readLine()) != null ) {
                line = line.trim();
                if ( line.isEmpty() ) {
                    continue;
                }

                String[] sides = line.split("=");

                String lhs = sides[0].trim();

                FractionDescriptor desc = this.descriptors.get( lhs );
                if ( desc == null ) {
                    String[] gavParts = lhs.split(":");
                    desc = new FractionDescriptor( gavParts[0], gavParts[1] );
                    this.descriptors.put( lhs, desc );
                }

                if ( sides.length > 1 ) {
                    String rhs = sides[1].trim();
                    String[] deps = rhs.split(",");

                    for (String dep : deps) {
                        dep = dep.trim();
                        if (dep.isEmpty()) {
                            continue;
                        }

                        FractionDescriptor depDesc = this.descriptors.get(dep);
                        if (depDesc == null) {
                            String[] gavParts = dep.split(":");
                            depDesc = new FractionDescriptor(gavParts[0], gavParts[1]);
                        }
                        desc.addDependency(depDesc);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Collection<FractionDescriptor> getFractionDescriptors() {
        return Collections.unmodifiableCollection(this.descriptors.values());
    }

    public FractionDescriptor getFractionDescriptor(final String groupId, final String artifactId) {
        return this.descriptors.get(groupId + ":" + artifactId);
    }
}
