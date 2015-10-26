/**
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
package org.wildfly.swarm.netflix.ribbon;

import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.impl.base.ArchiveBase;
import org.jboss.shrinkwrap.impl.base.AssignableBase;
import org.wildfly.swarm.container.JARArchive;
import org.wildfly.swarm.msc.ServiceActivatorArchive;

import javax.xml.bind.util.JAXBSource;

/**
 * @author Bob McWhirter
 */
public class RibbonArchiveImpl extends AssignableBase<ArchiveBase<?>> implements RibbonArchive {

    /**
     * Constructs a new instance using the underlying specified archive, which is required
     *
     * @param archive
     */
    public RibbonArchiveImpl(ArchiveBase<?> archive) {
        super(archive);
        //as(ServiceActivatorArchive.class).addServiceActivator("org.wildfly.swarm.runtime.netflix.ribbon.ClusterManagerActivator");
        /*
        as(JARArchive.class).addModule("org.wildfly.clustering.api");
        as(JARArchive.class).addModule("com.netflix.ribbon");
        as(JARArchive.class).addModule("com.netflix.archaius");
        as(JARArchive.class).addModule("com.netflix.hystrix");
        as(JARArchive.class).addModule("io.reactivex.rxjava");
        as(JARArchive.class).addModule("io.reactivex.rxnetty");
        as(JARArchive.class).addModule("io.netty");
        as(JARArchive.class).addModule("org.picketbox");
        as(JARArchive.class).add(new RibbonConfigAsset() );
        */
    }

    @Override
    public void setApplicationName(String name) {
        as(ServiceActivatorArchive.class).addServiceActivator("org.wildfly.swarm.netflix.ribbon.runtime.ApplicationAdvertiserActivator");
        as(JARArchive.class).addModule("org.wildfly.swarm.netflix.ribbon", "runtime");
        as(JARArchive.class).add(new StringAsset(name), "META-INF/netflix-ribbon-application.conf");
    }


}
