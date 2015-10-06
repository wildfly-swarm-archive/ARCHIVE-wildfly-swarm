package org.wildfly.swarm.hawkular;

import org.junit.Test;
import org.wildfly.swarm.container.Container;
import org.wildfly.swarm.hawkular.HawkularFraction;

/**
 * @author Bob McWhirter
 */
public class HawkularInVmTest {

    @Test
    public void testSimple() throws Exception {
        Container container = new Container();
        container.fraction(HawkularFraction.createDefaultHawkularFraction() );
        container.start().stop();
    }
}
