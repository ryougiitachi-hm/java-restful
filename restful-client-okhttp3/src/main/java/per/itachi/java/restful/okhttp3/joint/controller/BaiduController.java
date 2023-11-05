package per.itachi.java.restful.okhttp3.joint.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import per.itachi.java.restful.okhttp3.app.port.BaiduPort;

@RestController
@RequestMapping("/v1/baidu")
public class BaiduController {

    @Autowired
    private BaiduPort baiduPort;

    @GetMapping("/search")
    public void search() {
        baiduPort.search();
    }
}
