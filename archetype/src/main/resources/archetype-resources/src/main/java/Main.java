package ${package}

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.container.Container;

/** * WildFly Swarm!
 */
public class Main
{
    public static void main( String[] args )
    {
        Container container = new Container();
        container.start();

        // WARArchive archive = ShrinkWrap.create(WARArchive.class);
        // archive.addClass(...)

        // container.deploy( archive );
    }
}
