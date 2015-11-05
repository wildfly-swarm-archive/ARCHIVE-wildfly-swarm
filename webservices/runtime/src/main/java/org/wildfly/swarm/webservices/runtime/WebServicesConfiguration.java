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

package org.wildfly.swarm.webservices.runtime;

import org.jboss.dmr.ModelNode;
import org.wildfly.swarm.container.runtime.AbstractServerConfiguration;
import org.wildfly.swarm.webservices.WebServicesFraction;

import java.util.ArrayList;
import java.util.List;

public class WebServicesConfiguration extends AbstractServerConfiguration<WebServicesFraction> {
    public WebServicesConfiguration() {
        super(WebServicesFraction.class);
    }

    @Override
    public WebServicesFraction defaultFraction() {
        return new WebServicesFraction();
    }

    @Override
    public List<ModelNode> getList(WebServicesFraction fraction) {
        return new ArrayList<>();
    }
}
