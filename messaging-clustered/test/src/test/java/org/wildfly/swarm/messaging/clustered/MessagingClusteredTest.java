package org.wildfly.swarm.messaging.clustered;

import org.junit.Test;
import org.wildfly.swarm.container.Container;
import org.wildfly.swarm.messaging.MessagingFraction;
import org.wildfly.swarm.messaging.MessagingServer;

public class MessagingClusteredTest {

    @Test
    public void testSimple() throws Exception {
        Container container = new Container();
        container.fraction(new MessagingFraction().server(new MessagingServer()));
        container.start().stop();
    }
}
