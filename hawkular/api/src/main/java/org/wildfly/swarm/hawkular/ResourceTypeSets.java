package org.wildfly.swarm.hawkular;

import static org.wildfly.swarm.hawkular.ResourceTypes.*;

/**
 * @author Bob McWhirter
 */
public class ResourceTypeSets {

    public static ResourceTypeSet MAIN = new ResourceTypeSet("Main")
            .resourceType( WILDFLY_SERVER );
}
