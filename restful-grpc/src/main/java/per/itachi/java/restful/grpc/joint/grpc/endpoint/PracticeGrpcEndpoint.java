package per.itachi.java.restful.grpc.joint.grpc.endpoint;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import per.itachi.java.restful.grpc.joint.grpc.PracticeGrpcServiceGrpc;
import per.itachi.java.restful.grpc.joint.grpc.PracticeRequest;
import per.itachi.java.restful.grpc.joint.grpc.PracticeResponse;

@Slf4j
@Component
public class PracticeGrpcEndpoint extends PracticeGrpcServiceGrpc.PracticeGrpcServiceImplBase {

    @Override
    public void exerciseRequestResponse(PracticeRequest request, StreamObserver<PracticeResponse> responseObserver) {
    }

    @Override
    public StreamObserver<PracticeRequest> exerciseStreamRequestResponse(StreamObserver<PracticeResponse> responseObserver) {
        return super.exerciseStreamRequestResponse(responseObserver);
    }

    @Override
    public void exerciseRequestStreamResponse(PracticeRequest request, StreamObserver<PracticeResponse> responseObserver) {
    }

    @Override
    public StreamObserver<PracticeRequest> exerciseStreamRequestStreamResponse(StreamObserver<PracticeResponse> responseObserver) {
        return super.exerciseStreamRequestStreamResponse(responseObserver);
    }
}
