package per.itachi.java.restful.grpc.app.service.impl;

import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import per.itachi.java.restful.grpc.app.port.SvcPort;
import per.itachi.java.restful.grpc.app.service.SvcService;

@Service
public class SvcServiceImpl implements SvcService {

    @Resource
    private SvcPort svcPort;

    @Override
    public void showTradesBatched(Map<String, String> headers, Map<String, String> queryParams) {
        svcPort.showTradesBatched(headers, queryParams);
    }
}
