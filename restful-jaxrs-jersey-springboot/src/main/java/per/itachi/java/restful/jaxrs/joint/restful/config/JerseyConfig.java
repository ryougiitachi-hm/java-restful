package per.itachi.java.restful.jaxrs.joint.restful.config;

import javax.annotation.PostConstruct;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import per.itachi.java.restful.jaxrs.joint.restful.jaxrs.JerseyUserResource;

/**
 * Referred to https://blog.csdn.net/allway2/article/details/123716091
 * */
@Configuration
public class JerseyConfig extends ResourceConfig {

    @Autowired
    private JerseyUserResource userResource;

    @PostConstruct
    public void init() {
        register(userResource);
    }
}
