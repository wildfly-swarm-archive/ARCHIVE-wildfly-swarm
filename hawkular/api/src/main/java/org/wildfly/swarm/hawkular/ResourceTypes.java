package org.wildfly.swarm.hawkular;

/**
 * @author Bob McWhirter
 */
public class ResourceTypes {

    public static ResourceType WILDFLY_SERVER = new ResourceType("WildFly Server")
            .resourceNameTemplate("WildFly Server [%ManagedServerName] [${jboss.node.name:localhost}]")
            .path("/")
            .metricSet(MetricSets.WILDFLY_MEMORY_METRICS)
            .metricSet(MetricSets.WILDFLY_THREADING_METRICS)
            .metricSet(MetricSets.WILDFLY_AGGREGATED_WEB_METRICS)
            .availSet(AvailSets.SERVER_AVAILABILITY)
            .config(new Config("Hostname")
                    .path("/core-service=server-environment")
                    .attribute("qualified-host-name"))
            .config(new Config("Version")
                    .attribute("release-version"))
            .config(new Config("Bound Address")
                    .path("/socket-binding-group=standard-sockets/socket-binding=http")
                    .attribute("bound-address"));

    public static ResourceType DEPLOYMENT = new ResourceType("Deployment")
            .parent(WILDFLY_SERVER)
            .resourceNameTemplate("Deployment [%2]")
            .path("/deployment=*")
            .metricSet(MetricSets.UNDERTOW_METRICS)
            .availSet(AvailSets.DEPLOYMENT_STATUS);
}
