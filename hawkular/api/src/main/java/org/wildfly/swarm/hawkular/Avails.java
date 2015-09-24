package org.wildfly.swarm.hawkular;


import static java.util.concurrent.TimeUnit.*;

/**
 * @author Bob McWhirter
 */
public class Avails {

    public static Avail APP_SERVER = new Avail("App Server")
            .every(30, SECONDS)
            .path("/")
            .attribtue("server-state")
            .upRegex("run.*");
}
