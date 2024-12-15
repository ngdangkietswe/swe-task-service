package dev.ngdangkietswe.swetaskservice.grpc.service.impl;

import dev.ngdangkietswe.sweprotobufshared.common.protobuf.EmptyResp;
import dev.ngdangkietswe.sweprotobufshared.common.protobuf.IdReq;
import dev.ngdangkietswe.sweprotobufshared.common.protobuf.UpsertResp;
import dev.ngdangkietswe.sweprotobufshared.proto.domain.SweGrpcPrincipal;
import dev.ngdangkietswe.sweprotobufshared.task.ListCommentReq;
import dev.ngdangkietswe.sweprotobufshared.task.ListCommentResp;
import dev.ngdangkietswe.sweprotobufshared.task.UpsertCommentReq;
import dev.ngdangkietswe.swetaskservice.grpc.service.ICommentGrpcService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author ngdangkietswe
 * @since 12/14/2024
 */

@Service
@RequiredArgsConstructor
public class CommentGrpcServiceImpl implements ICommentGrpcService {

    @Override
    public UpsertResp upsertComment(UpsertCommentReq request, SweGrpcPrincipal principal) {
        return null;
    }

    @Override
    public ListCommentResp listComment(ListCommentReq request, SweGrpcPrincipal principal) {
        return null;
    }

    @Override
    public EmptyResp deleteComment(IdReq request, SweGrpcPrincipal principal) {
        return null;
    }
}
