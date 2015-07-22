package org.wildfly.swarm.webservices;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author John D. Ament
 */
public class PreHandlerChain {
    public static final PreHandlerChain RECORDING = new PreHandlerChain("recording-handlers",
            "##SOAP11_HTTP ##SOAP11_HTTP_MTOM ##SOAP12_HTTP ##SOAP12_HTTP_MTOM",
            Collections.singleton(Handler.RECORDING));
    private final String name;
    private final String protocolBindings;
    private final Set<Handler> handlers = new LinkedHashSet<>();

    public PreHandlerChain(String name, String protocolBindings, Collection<Handler> handlers) {
        this.name = name;
        this.protocolBindings = protocolBindings;
        this.handlers.addAll(handlers);
    }

    public String getName() {
        return name;
    }

    public String getProtocolBindings() {
        return protocolBindings;
    }

    public Set<Handler> getHandlers() {
        return Collections.unmodifiableSet(handlers);
    }
}
