package org.wildfly.swarm.hawkular;


/**
 * @author Bob McWhirter
 */
public class ResourceTypeSets {

    public static ResourceTypeSet MAIN = new ResourceTypeSet("Main")
            .resourceType(ResourceTypes.WILDFLY_SERVER);

    public static ResourceTypeSet DEPLOYMENT = new ResourceTypeSet("Deployment")
            .resourceType(ResourceTypes.DEPLOYMENT);
}
