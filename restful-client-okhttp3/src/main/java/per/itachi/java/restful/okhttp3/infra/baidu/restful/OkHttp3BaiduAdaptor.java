package per.itachi.java.restful.okhttp3.infra.baidu.restful;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;
import per.itachi.java.restful.okhttp3.app.port.BaiduPort;

@Slf4j
@Component
public class OkHttp3BaiduAdaptor implements BaiduPort {

    private static final String BASE_URL_BAIDU = "https://www.baidu.com";

    @Override
    public void search() {
        Request request = new Request.Builder()
                .url(BASE_URL_BAIDU + "/sa")
                .get()
                .build();
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        try(Response response = okHttpClient.newCall(request).execute()) {
            log.info("response={}", response);
        }
        catch (IOException e) {
            log.error("Error occurred. ",e );
        }
    }
}
