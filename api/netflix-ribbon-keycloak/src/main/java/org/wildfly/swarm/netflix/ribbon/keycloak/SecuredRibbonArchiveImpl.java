package org.wildfly.swarm.netflix.ribbon.keycloak;

import org.jboss.shrinkwrap.impl.base.ArchiveBase;
import org.wildfly.swarm.netflix.ribbon.RibbonArchiveImpl;
import org.wildfly.swarm.netflix.ribbon.SecuredRibbonArchive;

/**
 * @author Bob McWhirter
 */
public class SecuredRibbonArchiveImpl extends RibbonArchiveImpl implements SecuredRibbonArchive {

    /**
     * Constructs a new instance using the underlying specified archive, which is required
     *
     * @param archive
     */
    public SecuredRibbonArchiveImpl(ArchiveBase<?> archive) {
        super(archive);
    }


}
