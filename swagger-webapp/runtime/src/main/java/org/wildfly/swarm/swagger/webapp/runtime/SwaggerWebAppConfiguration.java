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
package org.wildfly.swarm.swagger.webapp.runtime;

import org.wildfly.swarm.container.Container;
import org.wildfly.swarm.container.runtime.AbstractServerConfiguration;
import org.wildfly.swarm.swagger.webapp.SwaggerProperties;
import org.wildfly.swarm.swagger.webapp.SwaggerWebAppFraction;
import org.wildfly.swarm.undertow.WARArchive;


/**
 * @author Lance Ball
 */
public class SwaggerWebAppConfiguration extends AbstractServerConfiguration<SwaggerWebAppFraction> {

    private static final String DEFAULT_CONTEXT = "/swagger-ui";

    public SwaggerWebAppConfiguration() {
        super(SwaggerWebAppFraction.class);

        deployment("org.wildfly.swarm:swagger-webapp-ui:war:" + Container.VERSION)
                .as("swagger-webapp-ui.war")
                .configure((fraction, archive) -> {
                    archive.as(WARArchive.class).setContextRoot(getContext());
                });
    }

    private static String getContext() {
        String context = System.getProperty(SwaggerProperties.CONTEXT_PATH);
        if (context == null || context == "") context = DEFAULT_CONTEXT;
        return context;
    }

    @Override
    public SwaggerWebAppFraction defaultFraction() {
        return new SwaggerWebAppFraction();
    }
}
