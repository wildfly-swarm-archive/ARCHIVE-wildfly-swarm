package org.wildfly.swarm.hawkular;
import static org.wildfly.swarm.hawkular.MetricSets.*;
import static org.wildfly.swarm.hawkular.AvailSets.*;

/**
 * @author Bob McWhirter
 */
public class ResourceTypes {

    public static ResourceType WILDFLY_SERVER = new ResourceType("WildFly Server")
            .resourceNameTemplate("WildFly Server [%ManagedServerName] [${jboss.node.name:localhost}]")
            .path("/")
            .metricSet(WILDFLY_MEMORY_METRICS)
            .availSet( SERVER_AVAILABILITY );
}
