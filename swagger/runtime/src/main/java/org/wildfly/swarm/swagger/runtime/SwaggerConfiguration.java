package org.wildfly.swarm.swagger.runtime;

import org.jboss.dmr.ModelNode;
import org.wildfly.swarm.container.runtime.AbstractServerConfiguration;
import org.wildfly.swarm.swagger.SwaggerFraction;

import java.util.Collections;
import java.util.List;

/**
 * @author Lance Ball
 */
public class SwaggerConfiguration extends AbstractServerConfiguration<SwaggerFraction> {
    public SwaggerConfiguration() {
        super(SwaggerFraction.class);
    }

    @Override
    public SwaggerFraction defaultFraction() {
        return new SwaggerFraction();
    }

    @Override
    public List<ModelNode> getList(SwaggerFraction fraction) throws Exception {
        return Collections.emptyList();
    }
}
