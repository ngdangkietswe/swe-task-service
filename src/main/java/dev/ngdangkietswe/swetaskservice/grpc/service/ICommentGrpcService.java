package dev.ngdangkietswe.swetaskservice.grpc.service;

import dev.ngdangkietswe.sweprotobufshared.comment.service.ListCommentReq;
import dev.ngdangkietswe.sweprotobufshared.comment.service.ListCommentResp;
import dev.ngdangkietswe.sweprotobufshared.comment.service.UpsertCommentReq;
import dev.ngdangkietswe.sweprotobufshared.common.protobuf.EmptyResp;
import dev.ngdangkietswe.sweprotobufshared.common.protobuf.IdReq;
import dev.ngdangkietswe.sweprotobufshared.common.protobuf.UpsertResp;
import dev.ngdangkietswe.sweprotobufshared.proto.domain.SweGrpcPrincipal;

/**
 * @author ngdangkietswe
 * @since 12/14/2024
 */

public interface ICommentGrpcService {

    UpsertResp upsertComment(UpsertCommentReq request, SweGrpcPrincipal principal);

    ListCommentResp listComment(ListCommentReq request, SweGrpcPrincipal principal);

    EmptyResp deleteComment(IdReq request, SweGrpcPrincipal principal);
}
