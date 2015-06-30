package org.wildfly.swarm.webservices;

/**
 * @author John D. Ament
 */
public class Handler {
    private final String name;
    private final String clazz;

    public static final Handler RECORDING = new Handler("RecordingHandler","org.jboss.ws.common.invocation.RecordingServerHandler");

    public Handler(String name, String clazz) {
        this.name = name;
        this.clazz = clazz;
    }

    public String getClazz() {
        return clazz;
    }

    public String getName() {
        return name;
    }
}
