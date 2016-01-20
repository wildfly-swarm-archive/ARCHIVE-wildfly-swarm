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
package org.wildfly.swarm.netflix.ribbon;

/**
 * @author John Hovell
 */
public class ConsulService {
    private final String name;
    private final boolean matchDatacenter;
    private final String tag;
    
    public ConsulService(final String name, final boolean matchDatacenter, final String tag) {
        super();
        this.name = name;
        this.matchDatacenter = matchDatacenter;
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public boolean isMatchDatacenter() {
        return matchDatacenter;
    }

    public String getTag() {
        return tag;
    }
}
