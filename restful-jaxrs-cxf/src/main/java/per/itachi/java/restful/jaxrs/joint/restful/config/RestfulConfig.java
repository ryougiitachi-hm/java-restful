package per.itachi.java.restful.jaxrs.joint.restful.config;

import java.util.Collections;
import javax.annotation.PostConstruct;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import per.itachi.java.restful.jaxrs.joint.restful.jaxrs.CxfUserResource;

/**
 * Referred to https://blog.csdn.net/weixin_42304484/article/details/127821403
 * */
@Configuration
@ImportResource("classpath:/META-INF/cxf/cxf.xml")
public class RestfulConfig {

    @Autowired
    private SpringBus springBus;

    @Autowired
    private CxfUserResource userResource;

    @PostConstruct
    public void init() {
        JAXRSServerFactoryBean jaxrsServerFactoryBean = new JAXRSServerFactoryBean();
        jaxrsServerFactoryBean.setServiceBeans(Collections.singletonList(userResource));
        jaxrsServerFactoryBean.setAddress("http://127.0.0.1:8081/cxf"); // TODO: configurable
//        jaxrsServerFactoryBean.setInInterceptors(); // can be configured as log
//        jaxrsServerFactoryBean.setInFaultInterceptors();
//        jaxrsServerFactoryBean.setOutInterceptors();// can be configured as log
//        jaxrsServerFactoryBean.setOutFaultInterceptors();
        jaxrsServerFactoryBean.create();
    }
}
