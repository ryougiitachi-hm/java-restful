package per.itachi.java.restful.jaxrs.joint.restful.config;

import org.apache.cxf.bus.spring.BusExtensionPostProcessor;
import org.apache.cxf.bus.spring.BusWiringBeanFactoryPostProcessor;
import org.apache.cxf.bus.spring.SpringBus;
import org.springframework.context.annotation.Bean;

/**
 * The content of cxf.xml in cxf-core.jar. validated not yet.
 * */
public class CxfCoreConfig {

    @Bean(name = "cxf", destroyMethod = "shutdown")
    public SpringBus springBus() {
        return new SpringBus(true);
    }

    @Bean("org.apache.cxf.bus.spring.BusWiringBeanFactoryPostProcessor")
    public BusWiringBeanFactoryPostProcessor initBusWiringBeanFactoryPostProcessor() {
        return new BusWiringBeanFactoryPostProcessor();
    }

//    @Bean("org.apache.cxf.bus.spring.Jsr250BeanPostProcessor")
//    public Jsr250BeanPostProcessor initJsr250BeanPostProcessor() {
//        return new Jsr250BeanPostProcessor();
//    }

    @Bean("org.apache.cxf.bus.spring.BusExtensionPostProcessor")
    public BusExtensionPostProcessor initBusExtensionPostProcessor() {
        return new BusExtensionPostProcessor();
    }
}