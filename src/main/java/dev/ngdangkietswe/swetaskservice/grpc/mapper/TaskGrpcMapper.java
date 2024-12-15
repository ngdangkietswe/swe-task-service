package dev.ngdangkietswe.swetaskservice.grpc.mapper;

import dev.ngdangkietswe.sweprotobufshared.proto.common.GrpcMapper;
import dev.ngdangkietswe.sweprotobufshared.task.protobuf.Status;
import dev.ngdangkietswe.sweprotobufshared.task.protobuf.Task;
import dev.ngdangkietswe.swetaskservice.data.projection.TaskProjection;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @author ngdangkietswe
 * @since 12/14/2024
 */

@Component
@RequiredArgsConstructor
public class TaskGrpcMapper extends GrpcMapper<TaskProjection, Task, Task.Builder> {

    private final UserGrpcMapper userGrpcMapper;
    private final AuditGrpcMapper auditGrpcMapper;

    @Override
    public Task.Builder toGrpcBuilder(TaskProjection from) {
        var task = from.getTask();
        var createdBy = from.getAudit().getCreatedBy();
        var modifiedBy = from.getAudit().getModifiedBy();
        var deletedBy = from.getAudit().getDeletedBy();

        var builder = Task.newBuilder()
                .setId(task.getId().toString())
                .setTitle(task.getTitle())
                .setDescription(StringUtils.defaultString(task.getDescription()))
                .setStatus(Status.forNumber(task.getStatus()))
                .setAudit(auditGrpcMapper.toGrpcMessage(task, createdBy, modifiedBy, deletedBy));

        if (Objects.nonNull(task.getReporter())) {
            builder.setReporter(userGrpcMapper.toGrpcMessage(task.getReporter()));
        }

        if (Objects.nonNull(task.getAssignee())) {
            builder.setAssignee(userGrpcMapper.toGrpcMessage(task.getAssignee()));
        }

        return builder;
    }

    @Override
    public Task toGrpcMessage(TaskProjection from) {
        return toGrpcBuilder(from).build();
    }

    @Override
    public List<Task> toGrpcMessages(List<TaskProjection> from) {
        return super.toGrpcMessages(from);
    }
}
