package per.itachi.java.restful.grpc.app.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import per.itachi.java.restful.grpc.app.service.UserService;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Override
    public void getUser(String userId) {
        log.info("Got an user with id={}", userId);
    }
}
