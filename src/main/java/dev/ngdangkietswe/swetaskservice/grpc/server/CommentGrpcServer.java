package dev.ngdangkietswe.swetaskservice.grpc.server;

import dev.ngdangkietswe.sweprotobufshared.comment.service.CommentServiceGrpc;
import dev.ngdangkietswe.sweprotobufshared.comment.service.ListCommentReq;
import dev.ngdangkietswe.sweprotobufshared.comment.service.ListCommentResp;
import dev.ngdangkietswe.sweprotobufshared.comment.service.UpsertCommentReq;
import dev.ngdangkietswe.sweprotobufshared.common.protobuf.EmptyResp;
import dev.ngdangkietswe.sweprotobufshared.common.protobuf.IdReq;
import dev.ngdangkietswe.sweprotobufshared.common.protobuf.UpsertResp;
import dev.ngdangkietswe.sweprotobufshared.proto.common.GrpcUtil;
import dev.ngdangkietswe.sweprotobufshared.proto.common.IGrpcServer;
import dev.ngdangkietswe.sweprotobufshared.proto.security.SweGrpcServerInterceptor;
import dev.ngdangkietswe.swetaskservice.grpc.service.ICommentGrpcService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.lognet.springboot.grpc.GRpcService;

/**
 * @author ngdangkietswe
 * @since 12/14/2024
 */

@GRpcService(interceptors = SweGrpcServerInterceptor.class)
@RequiredArgsConstructor
public class CommentGrpcServer extends CommentServiceGrpc.CommentServiceImplBase {

    private final ICommentGrpcService commentGrpcService;

    @Override
    public void upsertComment(UpsertCommentReq request, StreamObserver<UpsertResp> responseObserver) {
        IGrpcServer.execute(
                request,
                responseObserver,
                commentGrpcService::upsertComment,
                exception -> UpsertResp.newBuilder()
                        .setError(GrpcUtil.asError(exception))
                        .build()
        );
    }

    @Override
    public void listComment(ListCommentReq request, StreamObserver<ListCommentResp> responseObserver) {
        IGrpcServer.execute(
                request,
                responseObserver,
                commentGrpcService::listComment,
                exception -> ListCommentResp.newBuilder()
                        .setError(GrpcUtil.asError(exception))
                        .build()
        );
    }

    @Override
    public void deleteComment(IdReq request, StreamObserver<EmptyResp> responseObserver) {
        IGrpcServer.execute(
                request,
                responseObserver,
                commentGrpcService::deleteComment,
                exception -> EmptyResp.newBuilder()
                        .setError(GrpcUtil.asError(exception))
                        .build()
        );
    }
}
