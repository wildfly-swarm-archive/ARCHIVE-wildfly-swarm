package org.wildfly.swarm.webservices;

/**
 * @author John D. Ament
 */
public class ClientConfig {
    private final String name;

    public static final ClientConfig STANDARD = new ClientConfig("Standard-Client-Config");

    public ClientConfig(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
