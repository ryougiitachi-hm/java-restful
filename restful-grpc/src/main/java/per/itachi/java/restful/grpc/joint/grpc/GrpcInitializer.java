package per.itachi.java.restful.grpc.joint.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GrpcInitializer {

    @Value("${joint.grpc.port}")
    private int port;

    private Server server;

    @PostConstruct
    public void start() {
        server = ServerBuilder
                .forPort(port)
                .build();
        try {
            server.start();
        }
        catch (IOException e) {
            log.error("Error occurred. ", e);
//            throw;
        }
    }

    @PreDestroy
    public void shutdown() {
        if (server == null) {
            log.warn("The grpc server didn't start up actually. ");
            return;
        }
        server.shutdown();
    }
}