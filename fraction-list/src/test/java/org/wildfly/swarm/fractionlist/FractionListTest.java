package org.wildfly.swarm.fractionlist;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author Bob McWhirter
 */
public class FractionListTest {

    @Test
    public void testList() throws IOException {
        FractionList list = new FractionList();

        Collection<FractionDescriptor> descriptors = list.getFractionDescriptors();

        FractionDescriptor logstash = descriptors.stream().filter(e -> e.getArtifactId().equals("wildfly-swarm-logstash")).findFirst().get();

        assertThat(list.getFractionDescriptor("org.wildfly.swarm", "wildfly-swarm-logstash")).isEqualTo(logstash);

        assertThat( logstash.getGroupId() ).isEqualTo("org.wildfly.swarm" );
        assertThat( logstash.getArtifactId() ).isEqualTo("wildfly-swarm-logstash" );
        assertThat( logstash.getDependencies() ).hasSize(2);

        assertThat( logstash.getDependencies().stream().filter( e->e.getArtifactId().equals ("wildfly-swarm-container" ) ).collect(Collectors.toList())).isNotEmpty();
        assertThat( logstash.getDependencies().stream().filter( e->e.getArtifactId().equals ("wildfly-swarm-logging" ) ).collect(Collectors.toList())).isNotEmpty();
    }

}
