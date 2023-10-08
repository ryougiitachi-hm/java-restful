package per.itachi.java.restful.grpc.app.port;

import java.util.Map;

public interface SvcPort {

    void showTradesBatched(Map<String, String> headers, Map<String, String> queryParams);
}
