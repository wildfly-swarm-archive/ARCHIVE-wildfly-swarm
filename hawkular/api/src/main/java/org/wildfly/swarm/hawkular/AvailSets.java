package org.wildfly.swarm.hawkular;


/**
 * @author Bob McWhirter
 */
public class AvailSets {

    public static AvailSet SERVER_AVAILABILITY = new AvailSet("Server Availability")
            .avail(Avails.APP_SERVER);

    public static AvailSet DEPLOYMENT_STATUS = new AvailSet("Deployment Status" )
            .avail(Avails.DEPLOYMENT_STATUS);
}
