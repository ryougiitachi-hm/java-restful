package per.itachi.java.restful.grpc.joint.grpc.endpoint;

import com.google.protobuf.Any;
import com.google.protobuf.StringValue;
import com.google.rpc.Status;
import io.grpc.protobuf.StatusProto;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Component;
import per.itachi.java.restful.grpc.joint.grpc.ExceptionGrpcServiceGrpc;
import per.itachi.java.restful.grpc.joint.grpc.ExceptionRequest;
import per.itachi.java.restful.grpc.joint.grpc.ExceptionResponse;

@Slf4j
@Component
public class ExceptionGrpcEndpoint extends ExceptionGrpcServiceGrpc.ExceptionGrpcServiceImplBase {

    @Override
    public void testException(ExceptionRequest request, StreamObserver<ExceptionResponse> responseObserver) {
        try {
            throw new RuntimeException("Let's take a look at this exception. ");
        }
        catch (Exception e) {
            log.error("Error", e);
            StringValue stackTraceHolder = StringValue.newBuilder()
                    .setValue("stack trace -> " + ExceptionUtils.getStackTrace(e))
                    .build();
            Status status = Status.newBuilder()
                    .setCode(io.grpc.Status.INTERNAL.getCode().value())
                    .setMessage(String.format("Error occurred while processing request. Cause [%s]", ExceptionUtils.getRootCauseMessage(e)))
                    .addDetails(Any.pack(stackTraceHolder))
                    .build();
            responseObserver.onError(StatusProto.toStatusException(status));
        }
    }
}
