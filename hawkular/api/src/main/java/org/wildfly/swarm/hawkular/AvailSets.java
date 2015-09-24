package org.wildfly.swarm.hawkular;

import static org.wildfly.swarm.hawkular.Avails.*;


/**
 * @author Bob McWhirter
 */
public class AvailSets {

    public static AvailSet SERVER_AVAILABILITY = new AvailSet("Server Availability")
            .avail(APP_SERVER);
}
