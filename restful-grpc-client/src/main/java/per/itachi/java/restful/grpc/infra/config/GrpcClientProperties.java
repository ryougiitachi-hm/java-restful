package per.itachi.java.restful.grpc.infra.config;

import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GrpcClientProperties {

    private String host;

    private int port;

    private List<String> forwardHeaders = Collections.emptyList();
}
