package org.wildfly.swarm.webservices;

/**
 * @author John D. Ament
 */
public class EndpointConfig {
    private final String name;
    private final PreHandlerChain preHandlerChain;
    public static final EndpointConfig STANDARD = new EndpointConfig("Standard-Endpoint-Config");
    public static final EndpointConfig RECORDING = new EndpointConfig("Recording-Endpoint-Config",PreHandlerChain.RECORDING);

    public EndpointConfig(String name) {
        this(name,null);
    }

    public EndpointConfig(String name, PreHandlerChain preHandlerChain) {
        this.name = name;
        this.preHandlerChain = preHandlerChain;
    }

    public String getName() {
        return name;
    }
}
