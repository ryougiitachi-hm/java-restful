package per.itachi.java.restful.grpc.infra.svc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import per.itachi.java.restful.grpc.infra.config.GrpcClientProperties;

@Configuration
public class SvcClientConfig {

    @Bean
    @ConfigurationProperties("infra.grpc.svc")
    public GrpcClientProperties svcGrpcClientProperties() {
        return new GrpcClientProperties();
    }
}
