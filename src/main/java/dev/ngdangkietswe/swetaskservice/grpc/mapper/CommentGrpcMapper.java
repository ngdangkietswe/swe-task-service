package dev.ngdangkietswe.swetaskservice.grpc.mapper;

import dev.ngdangkietswe.swejavacommonshared.utils.CommonUtil;
import dev.ngdangkietswe.sweprotobufshared.proto.common.GrpcMapper;
import dev.ngdangkietswe.sweprotobufshared.task.protobuf.Comment;
import dev.ngdangkietswe.swetaskservice.data.projection.CommentProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;

/**
 * @author ngdangkietswe
 * @since 12/15/2024
 */

@Component
@RequiredArgsConstructor
public class CommentGrpcMapper extends GrpcMapper<CommentProjection, Comment, Comment.Builder> {

    private final AuditGrpcMapper auditGrpcMapper;

    @Override
    public Comment.Builder toGrpcBuilder(CommentProjection from) {
        var comment = from.getComment();
        var createdBy = from.getAudit().getCreatedBy();
        var modifiedBy = from.getAudit().getModifiedBy();
        var deletedBy = from.getAudit().getDeletedBy();

        return Comment.newBuilder()
                .setId(comment.getId().toString())
                .setContent(comment.getContent())
                .setTask(Comment.Task.newBuilder()
                        .setId(comment.getTask().getId().toString())
                        .setTitle(comment.getTask().getTitle())
                        .build())
                .setAudit(auditGrpcMapper.toGrpcMessage(comment, createdBy, modifiedBy, deletedBy));
    }

    public Comment toGrpcMessage(CommentProjection from, Map<UUID, CommentProjection> commentProjectionMap) {
        var builder = toGrpcBuilder(from);
        builder.addAllChildren(
                CommonUtil.asList(
                        from.getComment().getChildren(),
                        child -> toGrpcMessage(commentProjectionMap.get(child.getId()), commentProjectionMap)
                )
        );
        return builder.build();
    }

    @Override
    public Comment toGrpcMessage(CommentProjection from) {
        return toGrpcBuilder(from).build();
    }

    @Override
    public List<Comment> toGrpcMessages(List<CommentProjection> from) {
        var commentProjectionMap = CommonUtil.asMono(
                from,
                f -> f.getComment().getId(),
                Function.identity()
        );

        return CommonUtil.asList(
                from.stream().filter(f -> Objects.isNull(f.getComment().getParent())).toList(),
                f -> toGrpcMessage(f, commentProjectionMap)
        );
    }
}
