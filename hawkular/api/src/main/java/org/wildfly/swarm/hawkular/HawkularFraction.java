package org.wildfly.swarm.hawkular;

import java.util.ArrayList;
import java.util.List;

import org.wildfly.swarm.container.Fraction;

/**
 * @author Bob McWhirter
 */
public class HawkularFraction implements Fraction {

    private String username;
    private String password;
    private String host = "localhost";
    private int port = 8080;

    private List<ResourceTypeSet> resourceTypeSets = new ArrayList<>();

    public HawkularFraction() {

    }

    public HawkularFraction username(String username) {
        this.username = username;
        return this;
    }

    public String username() {
        return this.username;
    }

    public HawkularFraction password(String password) {
        this.password = password;
        return this;
    }

    public String password() {
        return this.password;
    }

    public HawkularFraction host(String host) {
        this.host = host;
        return this;
    }

    public String host() {
        return this.host;
    }

    public HawkularFraction port(int port) {
        this.port = port;
        return this;
    }

    public int port() {
        return this.port;
    }

    public HawkularFraction resourceTypeSet(ResourceTypeSet resourceTypeSet) {
        this.resourceTypeSets.add( resourceTypeSet );
        return this;
    }

    public List<ResourceTypeSet> resourceTypeSets() {
        return this.resourceTypeSets;
    }

    public static HawkularFraction createDefaultHawkularFraction() {
        return new HawkularFraction()
                .resourceTypeSet( ResourceTypeSets.MAIN );
    }

    public static HawkularFraction createDefaultHawkularFraction(String host, int port, String username, String password) {
        return createDefaultHawkularFraction()
                .host(host)
                .port(port)
                .username(username)
                .password(password);
    }
}
