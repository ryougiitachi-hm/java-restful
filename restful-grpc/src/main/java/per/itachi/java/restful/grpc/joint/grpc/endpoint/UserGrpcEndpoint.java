package per.itachi.java.restful.grpc.joint.grpc.endpoint;

import io.grpc.stub.StreamObserver;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import per.itachi.java.restful.grpc.app.service.UserService;
import per.itachi.java.restful.grpc.joint.grpc.UserGrpcServiceGrpc;
import per.itachi.java.restful.grpc.joint.grpc.UserRequest;
import per.itachi.java.restful.grpc.joint.grpc.UserResponse;

@Component
public class UserGrpcEndpoint extends UserGrpcServiceGrpc.UserGrpcServiceImplBase {

    @Autowired
    private UserService userService;

    @Override
    public void getUser(UserRequest request, StreamObserver<UserResponse> responseObserver) {
        userService.getUser(request.getUserId());
        UserResponse userResponse = UserResponse.newBuilder().setUserId(UUID.randomUUID().toString()).build();
        responseObserver.onNext(userResponse);
        // if no invocation for onNext, stderr will output as follows:
        // Cancelling the stream with status Status{code=INTERNAL, description=Completed without a response, cause=null}
        responseObserver.onCompleted();
    }
}
