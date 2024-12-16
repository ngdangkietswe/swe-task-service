package dev.ngdangkietswe.swetaskservice.grpc.service.impl;

import dev.ngdangkietswe.sweprotobufshared.common.protobuf.EmptyResp;
import dev.ngdangkietswe.sweprotobufshared.common.protobuf.IdReq;
import dev.ngdangkietswe.sweprotobufshared.common.protobuf.UpsertResp;
import dev.ngdangkietswe.sweprotobufshared.proto.common.GrpcUtil;
import dev.ngdangkietswe.sweprotobufshared.proto.domain.SweGrpcPrincipal;
import dev.ngdangkietswe.sweprotobufshared.proto.exception.GrpcNotFoundException;
import dev.ngdangkietswe.sweprotobufshared.task.ListCommentReq;
import dev.ngdangkietswe.sweprotobufshared.task.ListCommentResp;
import dev.ngdangkietswe.sweprotobufshared.task.UpsertCommentReq;
import dev.ngdangkietswe.swetaskservice.data.entity.CommentEntity;
import dev.ngdangkietswe.swetaskservice.data.repository.dsl.CommentDslRepository;
import dev.ngdangkietswe.swetaskservice.data.repository.jpa.CommentRepository;
import dev.ngdangkietswe.swetaskservice.data.repository.jpa.TaskRepository;
import dev.ngdangkietswe.swetaskservice.grpc.mapper.CommentGrpcMapper;
import dev.ngdangkietswe.swetaskservice.grpc.service.ICommentGrpcService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author ngdangkietswe
 * @since 12/14/2024
 */

@Service
@RequiredArgsConstructor
public class CommentGrpcServiceImpl implements ICommentGrpcService {

    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;

    private final CommentDslRepository commentDslRepository;

    private final CommentGrpcMapper commentGrpcMapper;

    @Override
    public UpsertResp upsertComment(UpsertCommentReq request, SweGrpcPrincipal principal) {
        CommentEntity comment;
        var userId = principal.getUserId();

        if (StringUtils.isNotEmpty(request.getId())) {
            UUID id = UUID.fromString(request.getId());
            comment = commentRepository.findById(id)
                    .orElseThrow(() -> new GrpcNotFoundException(CommentEntity.class, "id", request.getId()));
            comment.preUpdate(userId);
        } else {
            comment = new CommentEntity();
            comment.prePersist(userId);
        }

        var task = taskRepository.findById(UUID.fromString(request.getTaskId()))
                .orElseThrow(() -> new GrpcNotFoundException(CommentEntity.class, "task_id", request.getTaskId()));
        comment.setTask(task);

        if (StringUtils.isNotEmpty(request.getParentId())) {
            var parent = commentRepository.findById(UUID.fromString(request.getParentId()))
                    .orElseThrow(() -> new GrpcNotFoundException(CommentEntity.class, "parent_id", request.getParentId()));
            comment.setParent(parent);
        }

        comment.setContent(request.getContent());
        commentRepository.save(comment);

        return UpsertResp.newBuilder()
                .setSuccess(true)
                .setData(UpsertResp.Data.newBuilder()
                        .setId(comment.getId().toString())
                        .build())
                .build();
    }

    @Override
    public ListCommentResp listComment(ListCommentReq request, SweGrpcPrincipal principal) {
        var normalize = GrpcUtil.normalize(request.getPageable());
        normalize = normalize.toBuilder().clearUnPaged().setUnPaged(true).build();
        var data = commentDslRepository.findAllByReq(request, normalize);

        return ListCommentResp.newBuilder()
                .setSuccess(true)
                .setData(ListCommentResp.Data.newBuilder()
                        .addAllComments(commentGrpcMapper.toGrpcMessages(data.getItems()))
                        .build())
                .build();
    }

    @Override
    public EmptyResp deleteComment(IdReq request, SweGrpcPrincipal principal) {
        commentRepository.findById(UUID.fromString(request.getId()))
                .ifPresentOrElse(
                        comment -> {
                            comment.preDelete(principal.getUserId());
                            commentRepository.save(comment);
                        },
                        () -> {
                            throw new GrpcNotFoundException(CommentEntity.class, "id", request.getId());
                        }
                );

        return EmptyResp.newBuilder().setSuccess(true).build();
    }
}
