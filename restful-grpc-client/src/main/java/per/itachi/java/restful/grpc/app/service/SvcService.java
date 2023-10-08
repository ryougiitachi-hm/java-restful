package per.itachi.java.restful.grpc.app.service;

import java.util.Map;

public interface SvcService {

    void showTradesBatched(Map<String, String> headers, Map<String, String> queryParams);
}
