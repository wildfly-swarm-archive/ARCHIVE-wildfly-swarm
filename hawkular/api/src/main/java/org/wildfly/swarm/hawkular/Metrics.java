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

import static java.util.concurrent.TimeUnit.*;

/**
 * @author Bob McWhirter
 */
public interface Metrics {

    public interface Memory {
        Metric HEAP_USED = new Metric("Heap Used").every(30, SECONDS)
                .units("bytes")
                .path("/core-service=platform-mbean/type=memory")
                .attribtue("heap-memory-usage#used");

        Metric HEAP_COMMITTED = new Metric("Heap Committed").every(1, MINUTES)
                .path("/core-service=platform-mbean/type=memory")
                .attribtue("heap-memory-usage#committed");

        Metric HEAP_MAX = new Metric("Heap Max").every(1, MINUTES)
                .path("/core-service=platform-mbean/type=memory")
                .attribtue("heap-memory-usage#max");

        Metric NON_HEAP_USED = new Metric("NonHeap Used").every(30, SECONDS)
                .units("bytes")
                .path("/core-service=platform-mbean/type=memory")
                .attribtue("non-heap-memory-usage#used");

        Metric NON_HEAP_COMMITTED = new Metric("NonHeap Committed").every(1, MINUTES)
                .units("bytes")
                .path("/core-service=platform-mbean/type=memory")
                .attribtue("non-heap-memory-usage#committed");

        Metric ACCUMULATED_GC_DURATION = new Metric("Accumulated GC Duration").every(1, MINUTES)
                .path("/core-service=platform-mbean/type=garbage-collector/name=*")
                .attribtue("collection-time");
    }


    public interface Threading {
        public static Metric THREAD_COUNT = new Metric("Thread Count").every(2, MINUTES)
                .path("/core-service=platform-mbean/type=threading")
                .attribtue("thread-count");
    }


    public interface AggregatedWeb {

        public static Metric AGGREGATED_ACTIVE_WEB_SESSIONS = new Metric("Aggregated Active Web Sessions").every(1, MINUTES)
                .path("/deployment=*/subsystem=undertow")
                .attribtue("active-sessions");

        public static Metric AGGREGATED_MAX_ACTIVE_WEB_SESSIONS = new Metric("Aggregated Max Active Web Sessions").every(1, MINUTES)
                .path("/deployment=*/subsystem=undertow")
                .attribtue("max-active-sessions");

        public static Metric AGGREGATED_EXPIRED_WEB_SESSIONS = new Metric("Aggregated Expired Web Sessions").every(1, MINUTES)
                .type("counter")
                .path("/deployment=*/subsystem=undertow")
                .attribtue("expired-sessions");

        public static Metric AGGREGATED_REJECTED_WEB_SESSIONS = new Metric("Aggregated Rejected Web Sessions").every(1, MINUTES)
                .type("counter")
                .path("/deployment=*/subsystem=undertow")
                .attribtue("rejected-sessions");

        public static Metric AGGREGATED_SERVLET_REQUEST_TIME = new Metric("Aggregated Servlet Request Time").every(1, MINUTES)
                .type("counter")
                .path("/deployment=*/subsystem=undertow/servlet=*")
                .attribtue("total-request-time");

        public static Metric AGGREGATED_SERVLET_REQUEST_COUNT = new Metric("Aggregated Servlet Request Count").every(1, MINUTES)
                .type("counter")
                .path("/deployment=*/subsystem=undertow/servlet=*")
                .attribtue("request-count");
    }


    public interface Undertow {

        public static Metric ACTIVE_SESSIONS = new Metric("Active Sessions").every(2, MINUTES)
                .path("/subsystem=undertow")
                .attribtue("active-sessions");

        public static Metric SESSIONS_CREATED = new Metric("Sessions Created").every(2, MINUTES)
                .type("counter")
                .path("/subsystem=undertow")
                .attribtue("sessions-created");

        public static Metric EXPIRED_SESSIONS = new Metric("Expired Sessions").every(2, MINUTES)
                .type("counter")
                .path("/subsystem=undertow")
                .attribtue("expired-sessions");

        public static Metric REJECTED_SESSIONS = new Metric("Rejected Sessions").every(2, MINUTES)
                .type("counter")
                .path("/subsystem=undertow")
                .attribtue("rejected-sessions");

        public static Metric MAX_ACTIVE_SESSIONS = new Metric("Max Active Sessions").every(2, MINUTES)
                .type("counter")
                .path("/subsystem=undertow")
                .attribtue("max-active-sessions");
    }

    public interface Servlet {
        public static Metric MAX_REQUEST_TIME = new Metric("Max Request Time").every(5,MINUTES)
                .units("milliseconds")
                .path( "/" )
                .attribtue( "max-request-time" );

        public static Metric MIN_REQUEST_TIME = new Metric("Max Request Time").every(5,MINUTES)
                .units("milliseconds")
                .path( "/" )
                .attribtue( "min-request-time" );

        public static Metric TOTAL_REQUEST_TIME = new Metric( "Total Request Time" ).every(5, MINUTES)
                .type( "counter" )
                .path( "/" )
                .attribtue( "total-request-time");

        public static Metric REQUEST_COUNT = new Metric( "Request Count" ).every( 5, MINUTES )
                .type( "counter" )
                .path( "/" )
                .attribtue( "request-count" );
    }

}
