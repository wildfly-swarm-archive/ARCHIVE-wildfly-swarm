/**
 * Copyright 2015-2016 Red Hat, Inc, and individual contributors.
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
package org.wildfly.swarm.keycloak.runtime;

import org.keycloak.KeycloakSecurityContext;

/**
 * @author Bob McWhirter
 */
public class KeycloakSecurityContextAssociation {

    private static ThreadLocal<KeycloakSecurityContext> SECURITY_CONTEXT = new ThreadLocal<>();

    public static KeycloakSecurityContext get() {
        KeycloakSecurityContext context = SECURITY_CONTEXT.get();
        return context;
    }

    public static void associate(KeycloakSecurityContext context) {
        SECURITY_CONTEXT.set(context);
    }

    public static void disassociate() {
        SECURITY_CONTEXT.remove();
    }
}
