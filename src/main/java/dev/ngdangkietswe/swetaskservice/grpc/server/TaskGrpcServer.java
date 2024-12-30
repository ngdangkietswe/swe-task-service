package dev.ngdangkietswe.swetaskservice.grpc.server;

import dev.ngdangkietswe.swejavacommonshared.aspects.authorization.SecuredAction;
import dev.ngdangkietswe.swejavacommonshared.aspects.authorization.SecuredAuth;
import dev.ngdangkietswe.swejavacommonshared.aspects.authorization.SecuredResource;
import dev.ngdangkietswe.sweprotobufshared.common.protobuf.EmptyResp;
import dev.ngdangkietswe.sweprotobufshared.common.protobuf.IdReq;
import dev.ngdangkietswe.sweprotobufshared.common.protobuf.UpsertResp;
import dev.ngdangkietswe.sweprotobufshared.proto.common.GrpcUtil;
import dev.ngdangkietswe.sweprotobufshared.proto.common.IGrpcServer;
import dev.ngdangkietswe.sweprotobufshared.proto.security.SweGrpcServerInterceptor;
import dev.ngdangkietswe.sweprotobufshared.task.GetTaskResp;
import dev.ngdangkietswe.sweprotobufshared.task.ListTaskReq;
import dev.ngdangkietswe.sweprotobufshared.task.ListTaskResp;
import dev.ngdangkietswe.sweprotobufshared.task.TaskServiceGrpc;
import dev.ngdangkietswe.sweprotobufshared.task.UpsertTaskReq;
import dev.ngdangkietswe.swetaskservice.grpc.service.ITaskGrpcService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.lognet.springboot.grpc.GRpcService;

/**
 * @author ngdangkietswe
 * @since 11/21/2024
 */

@GRpcService(interceptors = SweGrpcServerInterceptor.class)
@RequiredArgsConstructor
public class TaskGrpcServer extends TaskServiceGrpc.TaskServiceImplBase {

    private final ITaskGrpcService taskGrpcService;

    @Override
    @SecuredAuth(action = SecuredAction.UPSERT, resource = SecuredResource.TASK, requestId = "id")
    public void upsertTask(UpsertTaskReq request, StreamObserver<UpsertResp> responseObserver) {
        IGrpcServer.execute(
                request,
                responseObserver,
                taskGrpcService::upsertTask,
                exception -> UpsertResp.newBuilder()
                        .setError(GrpcUtil.asError(exception))
                        .build()
        );
    }

    @Override
    @SecuredAuth(action = SecuredAction.READ, resource = SecuredResource.TASK)
    public void listTask(ListTaskReq request, StreamObserver<ListTaskResp> responseObserver) {
        IGrpcServer.execute(
                request,
                responseObserver,
                taskGrpcService::listTask,
                exception -> ListTaskResp.newBuilder()
                        .setError(GrpcUtil.asError(exception))
                        .build()
        );
    }

    @Override
    @SecuredAuth(action = SecuredAction.READ, resource = SecuredResource.TASK)
    public void getTask(IdReq request, StreamObserver<GetTaskResp> responseObserver) {
        IGrpcServer.execute(
                request,
                responseObserver,
                taskGrpcService::getTask,
                exception -> GetTaskResp.newBuilder()
                        .setError(GrpcUtil.asError(exception))
                        .build()
        );
    }

    @Override
    @SecuredAuth(action = SecuredAction.DELETE, resource = SecuredResource.TASK, requestId = "id")
    public void deleteTask(IdReq request, StreamObserver<EmptyResp> responseObserver) {
        IGrpcServer.execute(
                request,
                responseObserver,
                taskGrpcService::deleteTask,
                exception -> EmptyResp.newBuilder()
                        .setError(GrpcUtil.asError(exception))
                        .build()
        );
    }

    @Override
    @SecuredAuth(action = SecuredAction.UPDATE, resource = SecuredResource.TASK)
    public void markTaskAsInProgress(IdReq request, StreamObserver<EmptyResp> responseObserver) {
        IGrpcServer.execute(
                request,
                responseObserver,
                taskGrpcService::markTaskAsInProgress,
                exception -> EmptyResp.newBuilder()
                        .setError(GrpcUtil.asError(exception))
                        .build()
        );
    }

    @Override
    @SecuredAuth(action = SecuredAction.UPDATE, resource = SecuredResource.TASK)
    public void markTaskAsInReview(IdReq request, StreamObserver<EmptyResp> responseObserver) {
        IGrpcServer.execute(
                request,
                responseObserver,
                taskGrpcService::markTaskAsInReview,
                exception -> EmptyResp.newBuilder()
                        .setError(GrpcUtil.asError(exception))
                        .build()
        );
    }

    @Override
    @SecuredAuth(action = SecuredAction.UPDATE, resource = SecuredResource.TASK)
    public void markTaskAsDone(IdReq request, StreamObserver<EmptyResp> responseObserver) {
        IGrpcServer.execute(
                request,
                responseObserver,
                taskGrpcService::markTaskAsDone,
                exception -> EmptyResp.newBuilder()
                        .setError(GrpcUtil.asError(exception))
                        .build()
        );
    }

    @Override
    @SecuredAuth(action = SecuredAction.UPDATE, resource = SecuredResource.TASK)
    public void markTaskAsCanceled(IdReq request, StreamObserver<EmptyResp> responseObserver) {
        IGrpcServer.execute(
                request,
                responseObserver,
                taskGrpcService::markTaskAsCanceled,
                exception -> EmptyResp.newBuilder()
                        .setError(GrpcUtil.asError(exception))
                        .build()
        );
    }
}
