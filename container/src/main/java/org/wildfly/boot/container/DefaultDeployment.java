package org.wildfly.boot.container;

import java.io.File;

/**
 * @author Bob McWhirter
 */
public class DefaultDeployment implements Deployment {

    public DefaultDeployment() {


    }

    @Override
    public File getContent() {
        return Content.CONTENT;
    }
}
