package dev.ngdangkietswe.swetaskservice.data.repository.dsl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.ngdangkietswe.swejavacommonshared.domain.Page;
import dev.ngdangkietswe.sweprotobufshared.common.protobuf.Pageable;
import dev.ngdangkietswe.sweprotobufshared.task.ListCommentReq;
import dev.ngdangkietswe.swetaskservice.data.entity.QCdcAuthUserEntity;
import dev.ngdangkietswe.swetaskservice.data.entity.QCommentEntity;
import dev.ngdangkietswe.swetaskservice.data.entity.QTaskEntity;
import dev.ngdangkietswe.swetaskservice.data.projection.AuditProjection;
import dev.ngdangkietswe.swetaskservice.data.projection.CommentProjection;
import dev.ngdangkietswe.swetaskservice.data.utils.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;

/**
 * @author ngdangkietswe
 * @since 12/15/2024
 */

@Repository
@RequiredArgsConstructor
public class CommentDslRepository {

    public static final Map<String, ComparableExpressionBase<?>> COMMENT_ORDER_MAP = Map.of(
            "created_at", QTaskEntity.taskEntity.createdAt,
            "updated_at", QTaskEntity.taskEntity.updatedAt);

    private final JPAQueryFactory factory;
    private final QTaskEntity qTask = QTaskEntity.taskEntity;
    private final QCommentEntity qComment = QCommentEntity.commentEntity;
    private final QCdcAuthUserEntity createdByUser = new QCdcAuthUserEntity("createdByUser");
    private final QCdcAuthUserEntity updatedByUser = new QCdcAuthUserEntity("updatedByUser");
    private final QCdcAuthUserEntity deletedByUser = new QCdcAuthUserEntity("deletedByUser");

    @SuppressWarnings("unchecked")
    public Page<CommentProjection> findAllByReq(ListCommentReq request, Pageable pageable) {
        JPAQuery<CommentProjection> query = (JPAQuery<CommentProjection>) factory.from(qComment)
                .innerJoin(createdByUser).on(qComment.createdBy.eq(createdByUser.id))
                .innerJoin(updatedByUser).on(qComment.updatedBy.eq(updatedByUser.id))
                .leftJoin(deletedByUser).on(qComment.deletedBy.eq(deletedByUser.id))
                .innerJoin(qComment.task, qTask).fetchJoin()
                .where(qTask.id.eq(UUID.fromString(request.getTaskId())));

        var expr = Projections.constructor(
                CommentProjection.class,
                qComment, Projections.constructor(
                        AuditProjection.class,
                        createdByUser, updatedByUser, deletedByUser
                ));

        return PageUtils.paginate(
                pageable,
                query,
                qComment,
                COMMENT_ORDER_MAP,
                qComment.updatedAt,
                expr);
    }
}
