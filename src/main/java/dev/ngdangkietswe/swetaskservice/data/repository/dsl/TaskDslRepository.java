package dev.ngdangkietswe.swetaskservice.data.repository.dsl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.ngdangkietswe.swejavacommonshared.domain.Page;
import dev.ngdangkietswe.sweprotobufshared.common.protobuf.Pageable;
import dev.ngdangkietswe.sweprotobufshared.task.service.ListTaskReq;
import dev.ngdangkietswe.swetaskservice.data.entity.QCdcAuthUserEntity;
import dev.ngdangkietswe.swetaskservice.data.entity.QTaskEntity;
import dev.ngdangkietswe.swetaskservice.data.projection.TaskProjection;
import dev.ngdangkietswe.swetaskservice.data.utils.PageUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.Map;
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
    private final QCdcAuthUserEntity qCdcAuthUser = QCdcAuthUserEntity.cdcAuthUserEntity;

    @SuppressWarnings("unchecked")
    public Page<TaskProjection> findAllByReq(ListTaskReq request, Pageable pageable) {
        var modifiedBy = QCdcAuthUserEntity.cdcAuthUserEntity;
        var deletedBy = QCdcAuthUserEntity.cdcAuthUserEntity;

        JPAQuery<TaskProjection> query = (JPAQuery<TaskProjection>) factory.from(qTask)
                .innerJoin(qCdcAuthUser).on(qTask.createdBy.eq(qCdcAuthUser.id))
                .innerJoin(modifiedBy).on(qTask.updatedBy.eq(modifiedBy.id))
                .leftJoin(deletedBy).on(qTask.deletedBy.eq(deletedBy.id));

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
                qTask, qCdcAuthUser, modifiedBy, deletedBy);

        return PageUtils.paginate(
                pageable,
                query,
                qTask,
                TASK_ORDER_MAP,
                qTask.updatedAt,
                expr);
    }
}
