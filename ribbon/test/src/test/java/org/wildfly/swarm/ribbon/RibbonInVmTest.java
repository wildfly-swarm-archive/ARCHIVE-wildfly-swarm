package org.wildfly.swarm.ribbon;

import org.junit.Test;
import org.wildfly.swarm.container.Container;
import org.wildfly.swarm.netflix.ribbon.RibbonFraction;

/**
 * @author Bob McWhirter
 */
public class RibbonInVmTest {

    @Test
    public void testSimple() throws Exception {
        Container container = new Container();
        container.fraction( new RibbonFraction() );
        container.start().stop();
    }
}
