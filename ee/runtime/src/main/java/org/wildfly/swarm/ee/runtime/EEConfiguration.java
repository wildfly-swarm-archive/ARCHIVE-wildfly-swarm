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
package org.wildfly.swarm.ee.runtime;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.xml.namespace.QName;

import org.jboss.dmr.ModelNode;
import org.jboss.staxmapper.XMLElementReader;
import org.wildfly.swarm.container.runtime.AbstractParserFactory;
import org.wildfly.swarm.container.runtime.MarshallingServerConfiguration;
import org.wildfly.swarm.ee.EEFraction;

/**
 * @author Bob McWhirter
 * @author Lance Ball
 */
public class EEConfiguration extends MarshallingServerConfiguration<EEFraction> {

    public static String EXTENSION_MODULE = "org.jboss.as.ee";

    public EEConfiguration() {
        super(EEFraction.class, EXTENSION_MODULE);
    }

    @Override
    public EEFraction defaultFraction() {
        return EEFraction.createDefaultFraction();
    }

    @Override
    public Optional<Map<QName, XMLElementReader<List<ModelNode>>>> getSubsystemParsers() throws Exception {
        return AbstractParserFactory.mapParserNamespaces(new EEParserFactory());
    }
}
