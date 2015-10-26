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
package org.wildfly.swarm.hawkular;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bob McWhirter
 */
public class AvailSet {

    private final String name;
    private boolean enabled = true;
    private List<Avail> avails = new ArrayList<>();

    public AvailSet(String name) {
        this.name = name;
    }

    public String name() {
        return this.name;
    }

    public AvailSet enabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public boolean enabled() {
        return this.enabled;
    }

    public AvailSet avail(Avail avail) {
        this.avails.add( avail );
        return this;
    }

    public List<Avail> avails() {
        return this.avails;
    }


}
