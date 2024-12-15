package dev.ngdangkietswe.swetaskservice.data.repository.dsl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.ngdangkietswe.swejavacommonshared.domain.Page;
import dev.ngdangkietswe.sweprotobufshared.common.protobuf.Pageable;
import dev.ngdangkietswe.sweprotobufshared.task.ListTaskReq;
import dev.ngdangkietswe.swetaskservice.data.entity.QCdcAuthUserEntity;
import dev.ngdangkietswe.swetaskservice.data.entity.QTaskEntity;
import dev.ngdangkietswe.swetaskservice.data.projection.AuditProjection;
import dev.ngdangkietswe.swetaskservice.data.projection.TaskProjection;
import dev.ngdangkietswe.swetaskservice.data.utils.PageUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * @author ngdangkietswe
 * @since 12/14/2024
 */

@Repository
@RequiredArgsConstructor
public class TaskDslRepository {

    public static final Map<String, ComparableExpressionBase<?>> TASK_ORDER_MAP = Map.of(
            "title", QTaskEntity.taskEntity.title,
            "created_at", QTaskEntity.taskEntity.createdAt,
            "updated_at", QTaskEntity.taskEntity.updatedAt);

    private final JPAQueryFactory factory;
    private final QTaskEntity qTask = QTaskEntity.taskEntity;
    private final QCdcAuthUserEntity createdByUser = new QCdcAuthUserEntity("createdByUser");
    private final QCdcAuthUserEntity updatedByUser = new QCdcAuthUserEntity("updatedByUser");
    private final QCdcAuthUserEntity deletedByUser = new QCdcAuthUserEntity("deletedByUser");

    @SuppressWarnings("unchecked")
    public Optional<TaskProjection> findById(UUID id) {
        JPAQuery<TaskProjection> query = (JPAQuery<TaskProjection>) factory.from(qTask)
                .innerJoin(createdByUser).on(qTask.createdBy.eq(createdByUser.id))
                .innerJoin(updatedByUser).on(qTask.updatedBy.eq(updatedByUser.id))
                .leftJoin(deletedByUser).on(qTask.deletedBy.eq(deletedByUser.id))
                .where(qTask.id.eq(id));

        var expr = Projections.constructor(
                TaskProjection.class,
                qTask, Projections.constructor(
                        AuditProjection.class,
                        createdByUser, updatedByUser, deletedByUser
                ));

        return Optional.ofNullable(query.select(expr).fetchFirst());
    }

    @SuppressWarnings("unchecked")
    public Page<TaskProjection> findAllByReq(ListTaskReq request, Pageable pageable) {
        JPAQuery<TaskProjection> query = (JPAQuery<TaskProjection>) factory.from(qTask)
                .innerJoin(createdByUser).on(qTask.createdBy.eq(createdByUser.id))
                .innerJoin(updatedByUser).on(qTask.updatedBy.eq(updatedByUser.id))
                .leftJoin(deletedByUser).on(qTask.deletedBy.eq(deletedByUser.id));

        if (StringUtils.isNotEmpty(request.getCreatedById())) {
            query.where(qTask.createdBy.eq(UUID.fromString(request.getCreatedById())));
        }

        if (StringUtils.isNotEmpty(request.getReporterId())) {
            query.where(qTask.reporter.id.eq(UUID.fromString(request.getReporterId())));
        }

        if (StringUtils.isNotEmpty(request.getAssigneeId())) {
            query.where(qTask.assignee.id.eq(UUID.fromString(request.getAssigneeId())));
        }

        if (request.getStatus().getNumber() > 0) {
            query.where(qTask.status.eq(request.getStatus().getNumber()));
        }

        if (StringUtils.isNotEmpty(request.getSearch())) {
            query.where(qTask.title.containsIgnoreCase(request.getSearch())
                    .or(qTask.description.containsIgnoreCase(request.getSearch())));
        }

        var expr = Projections.constructor(
                TaskProjection.class,
                qTask, Projections.constructor(
                        AuditProjection.class,
                        createdByUser, updatedByUser, deletedByUser
                ));

        return PageUtils.paginate(
                pageable,
                query,
                qTask,
                TASK_ORDER_MAP,
                qTask.updatedAt,
                expr);
    }
}
