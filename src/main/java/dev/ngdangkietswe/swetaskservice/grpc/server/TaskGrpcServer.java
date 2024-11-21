package dev.ngdangkietswe.swetaskservice.grpc.server;

import com.google.rpc.Code;
import dev.ngdangkietswe.sweprotobufshared.common.protobuf.Error;
import dev.ngdangkietswe.sweprotobufshared.common.protobuf.Pageable;
import dev.ngdangkietswe.sweprotobufshared.common.protobuf.UpsertResp;
import dev.ngdangkietswe.sweprotobufshared.proto.common.IGrpcServer;
import dev.ngdangkietswe.sweprotobufshared.task.protobuf.Task;
import dev.ngdangkietswe.sweprotobufshared.task.service.ListTaskResp;
import dev.ngdangkietswe.sweprotobufshared.task.service.TaskServiceGrpc;
import dev.ngdangkietswe.swetaskservice.grpc.service.ITaskGrpcService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.lognet.springboot.grpc.GRpcService;

/**
 * @author ngdangkietswe
 * @since 11/21/2024
 */

@GRpcService
@RequiredArgsConstructor
public class TaskGrpcServer extends TaskServiceGrpc.TaskServiceImplBase {

    private final ITaskGrpcService taskGrpcService;

    @Override
    public void upsertTask(Task request, StreamObserver<UpsertResp> responseObserver) {
        IGrpcServer.execute(
                request,
                responseObserver,
                taskGrpcService::upsertTask,
                exception -> UpsertResp.newBuilder()
                        .setError(Error.newBuilder()
                                .setCode(Code.UNKNOWN_VALUE)
                                .setStatus(Code.UNKNOWN.name())
                                .setMessage(exception.getMessage())
                                .build())
                        .build()
        );
    }

    @Override
    public void listTask(Pageable request, StreamObserver<ListTaskResp> responseObserver) {
        IGrpcServer.execute(
                request,
                responseObserver,
                taskGrpcService::listTask,
                exception -> ListTaskResp.newBuilder()
                        .setError(Error.newBuilder()
                                .setCode(Code.UNKNOWN_VALUE)
                                .setStatus(Code.UNKNOWN.name())
                                .setMessage(exception.getMessage())
                                .build())
                        .build()
        );
    }
}
