/*
 * Copyright 2015 Red Hat, Inc, and individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wildfly.swarm.webservices;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

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
