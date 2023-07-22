package per.itachi.java.restful.ftp.jsch.infra.sftp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SftpConfig {

    @Bean
    @ConfigurationProperties("infra.sftp.remote-host-paths")
    public SftpProperties sftpProperties() {
        return new SftpProperties();
    }
}
