package per.itachi.java.restful.grpc.infra.svc.grpc;

import io.grpc.Grpc;
import io.grpc.ManagedChannel;
import io.grpc.Metadata;
import io.grpc.netty.shaded.io.grpc.netty.NegotiationType;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import java.util.Locale;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import per.itachi.java.restful.grpc.app.port.SvcPort;
import per.itachi.java.restful.grpc.infra.config.GrpcClientProperties;

@Slf4j
@Component
public class GrpcSvcAdaptor implements SvcPort {

    @Autowired
    private GrpcClientProperties svcGrpcClientProperties;

    @Override
    public void showTradesBatched(Map<String, String> headers, Map<String, String> queryParams) {
        log.info("Initializing svc GRPC client with {}", Grpc.TRANSPORT_ATTR_REMOTE_ADDR);
        // ManagedChannelBuilder
        ManagedChannel channel = createManagedChannel();
        // initialize headers
        Metadata metadata = createMetadata(headers);

//        TradeServceGrpc
//                .newStub(channel)
//                .withInterceptors(new ClientInterceptor[] {MetadataUtils.newAttachHeadersInterceptor(metadata)})
//                .withCompression("gzip");
        log.info("Initialized svc GRPC client with metadata={}", metadata);
    }

    private ManagedChannel createManagedChannel() {
        // ManagedChannelBuilder
        return NettyChannelBuilder
                .forAddress(svcGrpcClientProperties.getHost(), svcGrpcClientProperties.getPort())
                .usePlaintext()
                .negotiationType(NegotiationType.PLAINTEXT)
                .maxInboundMessageSize(1024 * 1024)
//                .flowControlWindow(this.params.getFlowControlWindow())
//                .nameResolverFactory() // deprecated
                .defaultLoadBalancingPolicy("pick_first")
                .build();
    }

    private Metadata createMetadata(Map<String, String> headers) {
        Metadata metadata = new Metadata();
        for (Map.Entry<String, String> header : headers.entrySet()) {
            if (svcGrpcClientProperties.getForwardHeaders().contains(header.getKey().toLowerCase(Locale.ROOT))) {
                metadata.put(Metadata.Key.of(header.getKey(), Metadata.ASCII_STRING_MARSHALLER), headers.get(header.getKey()));
            }
        }
        return metadata;
    }
}
