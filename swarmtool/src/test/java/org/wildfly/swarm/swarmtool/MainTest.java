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
package org.wildfly.swarm.swarmtool;


import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Properties;

import static org.fest.assertions.Assertions.assertThat;

public class MainTest {

    Result runTool(String... args) throws Exception {
        final OutputStream err = new ByteArrayOutputStream();
        final OutputStream out = new ByteArrayOutputStream();
        final PrintStream origErr = System.err;
        final PrintStream origOut = System.out;
        final Result result = new Result();

        try {
            System.setErr(new PrintStream(err));
            System.setOut(new PrintStream(out));
            final String[] fullArgs = Arrays.copyOf(args, args.length + 1);
            fullArgs[fullArgs.length - 1] =  "--output-dir=target/test-output";
            
            result.jarFile = Main.generateSwarmJar(fullArgs);
        } catch (Main.ExitException e) {
            result.exitStatus = e.status;
            result.exitMessage = e.getMessage();
        } finally {
            System.setErr(origErr);
            System.setOut(origOut);
        }

        //TODO: add output to result

        return result;
    }


    @Test
    public void setContextPath() throws Exception {
        final Path war = Paths.get(getClass().getClassLoader().getResource("simple-servlet.war").toURI());
        final Result result = runTool(war.toAbsolutePath().toString(), "--context-path=/path");
        assertThat(result.exitStatus).isEqualTo(0);

        final WebArchive archive = ShrinkWrap.createFromZipFile(WebArchive.class, result.jarFile);
        final Properties props = new Properties();
        try (InputStream in = archive.get("META-INF/wildfly-swarm.properties").getAsset().openStream()) {
            props.load(in);
        }

        assertThat(props.get("wildfly.swarm.context.path")).isEqualTo("/path");
    }

    class Result {
        public int exitStatus = 0;
        public String exitMessage = null;
        public File jarFile = null;
    }
}
