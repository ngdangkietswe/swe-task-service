package dev.ngdangkietswe.swetaskservice.grpc.service;

import dev.ngdangkietswe.sweprotobufshared.common.protobuf.EmptyResp;
import dev.ngdangkietswe.sweprotobufshared.common.protobuf.IdReq;
import dev.ngdangkietswe.sweprotobufshared.common.protobuf.UpsertResp;
import dev.ngdangkietswe.sweprotobufshared.proto.domain.SweGrpcPrincipal;
import dev.ngdangkietswe.sweprotobufshared.task.ListCommentReq;
import dev.ngdangkietswe.sweprotobufshared.task.ListCommentResp;
import dev.ngdangkietswe.sweprotobufshared.task.UpsertCommentReq;

/**
 * @author ngdangkietswe
 * @since 12/14/2024
 */

public interface ICommentGrpcService {

    UpsertResp upsertComment(UpsertCommentReq request, SweGrpcPrincipal principal);

    ListCommentResp listComment(ListCommentReq request, SweGrpcPrincipal principal);

    EmptyResp deleteComment(IdReq request, SweGrpcPrincipal principal);
}
