package per.itachi.java.restful.grpc;

import java.util.concurrent.CountDownLatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class GrpcApp {

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) {
        SpringApplication.run(GrpcApp.class, args);
        initializeShutdownHook();
    }

    private static void initializeShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(
                () -> {
                    countDownLatch.countDown();
                    log.info("Received signal of shutting down from shutdown hook. ");
                }
        ));

        try {
            countDownLatch.await();
            log.info("Received signal of shutting down. ");
        }
        catch (InterruptedException e) {
            log.error("Error occurred when awaiting signal of shutting down. ");
        }
    }
}
