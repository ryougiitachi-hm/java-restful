package per.itachi.java.restful.grpc.joint.grpc;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerInterceptor;
import io.grpc.ServerInterceptors;
import io.grpc.ServerServiceDefinition;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;
import java.io.IOException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GrpcInitializer {

    @Value("${joint.grpc.port}")
    private int port;

    @Autowired
    private List<ServerInterceptor> serverInterceptors;

    @Autowired
    private List<BindableService> bindableServices;

    private Server server;

    @PostConstruct
    public void start() {
        // ServerBuilder<?> serverBuilder
        ServerBuilder<NettyServerBuilder> serverBuilder = NettyServerBuilder
                .forPort(port);
        log.info("Initializing grpc endpoints: ");
        for (BindableService item : bindableServices) {
            ServerServiceDefinition serverServiceDefinition = ServerInterceptors
                    .interceptForward(item, serverInterceptors.toArray(new ServerInterceptor[0]));
            serverBuilder.addService(serverServiceDefinition);
            log.info("Added grpc endpoint, item={}", item);
        }
        server = serverBuilder.build();
//        ServerServiceDefinition
        try {
            server.start();
            log.warn("The grpc server started up, port={}. ", port);
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
        log.warn("The grpc server shut down. ");
    }
}
