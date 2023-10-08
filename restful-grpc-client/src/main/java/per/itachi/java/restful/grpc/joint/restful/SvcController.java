package per.itachi.java.restful.grpc.joint.restful;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import per.itachi.java.restful.grpc.app.service.SvcService;

@RestController
@RequestMapping("/v1/svc")
public class SvcController {

    @Autowired
    private SvcService svcService;

    @GetMapping("/show-trades-batched")
    public void invokeShowTradesBatched(@RequestHeader Map<String, String> headers,
                                        @RequestParam Map<String, String> queryParams) {
        svcService.showTradesBatched(headers, queryParams);
    }

}
