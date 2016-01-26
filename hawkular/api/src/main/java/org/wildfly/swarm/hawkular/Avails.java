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
package org.wildfly.swarm.hawkular;


import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author Bob McWhirter
 */
public class Avails {

    public static Avail APP_SERVER = new Avail("App Server")
            .every(30, SECONDS)
            .path("/")
            .attribtue("server-state")
            .upRegex("run.*");

    public static Avail DEPLOYMENT_STATUS = new Avail("Deployment Status")
            .every(1, MINUTES)
            .path("/")
            .attribtue("status")
            .upRegex("OK");
}
