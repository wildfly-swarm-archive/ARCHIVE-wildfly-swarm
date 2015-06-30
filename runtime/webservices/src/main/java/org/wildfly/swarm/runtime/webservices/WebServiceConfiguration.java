package org.wildfly.swarm.runtime.webservices;

import org.jboss.dmr.ModelNode;
import org.wildfly.swarm.runtime.container.AbstractServerConfiguration;
import org.wildfly.swarm.webservices.WebServicesFraction;

import java.util.List;

/**
 * @author John D. Ament
 */
public class WebServiceConfiguration extends AbstractServerConfiguration<WebServicesFraction> {
    public WebServiceConfiguration() {
        super(WebServicesFraction.class);
    }

    @Override
    public WebServicesFraction defaultFraction() {
        return new WebServicesFraction();
    }

    @Override
    public List<ModelNode> getList(WebServicesFraction fraction) {
        return null;
    }
}
