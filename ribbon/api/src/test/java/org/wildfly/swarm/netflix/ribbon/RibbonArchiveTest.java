package org.wildfly.swarm.netflix.ribbon;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.Asset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.junit.Test;
import org.wildfly.swarm.container.JARArchive;
import org.wildfly.swarm.msc.ServiceActivatorArchive;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author Bob McWhirter
 */
public class RibbonArchiveTest {

    @Test
    public void testAdvertiseDefaultName() {
        JARArchive archive = ShrinkWrap.create(JARArchive.class, "myapp.war");
        archive.as(RibbonArchive.class)
                .advertise();

        Asset asset = archive.get(RibbonArchive.RIBBON_APP_CONF_PATH).getAsset();

        assertThat(asset).isNotNull();
        assertThat(asset).isInstanceOf(StringAsset.class);
        assertThat(((StringAsset) asset).getSource().trim()).isEqualTo("myapp");

        assertThat( archive.as(ServiceActivatorArchive.class).containsServiceActivator( RibbonArchiveImpl.SERVICE_ACTIVATOR_CLASS_NAME )).isTrue();
    }

    @Test
    public void testAdvertiseExplicitName() {
        JARArchive archive = ShrinkWrap.create(JARArchive.class, "myapp.war");
        archive.as(RibbonArchive.class)
                .advertise("myotherapp");

        Asset asset = archive.get(RibbonArchive.RIBBON_APP_CONF_PATH).getAsset();

        assertThat(asset).isNotNull();
        assertThat(asset).isInstanceOf(StringAsset.class);
        assertThat(((StringAsset) asset).getSource().trim()).isEqualTo("myotherapp");

        assertThat( archive.as(ServiceActivatorArchive.class).containsServiceActivator( RibbonArchiveImpl.SERVICE_ACTIVATOR_CLASS_NAME )).isTrue();
    }

    @Test
    public void testAdvertiseMultiple() {
        JARArchive archive = ShrinkWrap.create(JARArchive.class, "myapp.war");
        archive.as(RibbonArchive.class)
                .advertise("service-a")
                .advertise("service-b", "service-c");

        Asset asset = archive.get(RibbonArchive.RIBBON_APP_CONF_PATH).getAsset();

        assertThat(asset).isNotNull();
        assertThat(asset).isInstanceOf(StringAsset.class);

        String[] services = ((StringAsset) asset).getSource().split("\n");

        assertThat( services ).contains( "service-a" );
        assertThat( services ).contains( "service-b" );
        assertThat( services ).contains( "service-c" );

        assertThat( archive.as(ServiceActivatorArchive.class).containsServiceActivator( RibbonArchiveImpl.SERVICE_ACTIVATOR_CLASS_NAME )).isTrue();
    }

    @Test

    public void testNotAdvertise() {
        JARArchive archive = ShrinkWrap.create(JARArchive.class, "myapp.war");
        archive.as(RibbonArchive.class);

        assertThat( archive.as(ServiceActivatorArchive.class).containsServiceActivator( RibbonArchiveImpl.SERVICE_ACTIVATOR_CLASS_NAME )).isFalse();
    }

}
