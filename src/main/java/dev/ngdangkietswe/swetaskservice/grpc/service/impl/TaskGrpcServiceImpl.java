package dev.ngdangkietswe.swetaskservice.grpc.service.impl;

import dev.ngdangkietswe.sweprotobufshared.common.protobuf.EmptyResp;
import dev.ngdangkietswe.sweprotobufshared.common.protobuf.IdReq;
import dev.ngdangkietswe.sweprotobufshared.common.protobuf.UpsertResp;
import dev.ngdangkietswe.sweprotobufshared.proto.common.GrpcUtil;
import dev.ngdangkietswe.sweprotobufshared.proto.domain.SweGrpcPrincipal;
import dev.ngdangkietswe.sweprotobufshared.proto.exception.GrpcNotFoundException;
import dev.ngdangkietswe.sweprotobufshared.task.service.GetTaskResp;
import dev.ngdangkietswe.sweprotobufshared.task.service.ListTaskReq;
import dev.ngdangkietswe.sweprotobufshared.task.service.ListTaskResp;
import dev.ngdangkietswe.sweprotobufshared.task.service.UpsertTaskReq;
import dev.ngdangkietswe.swetaskservice.data.entity.CdcAuthUserEntity;
import dev.ngdangkietswe.swetaskservice.data.entity.TaskEntity;
import dev.ngdangkietswe.swetaskservice.data.repository.dsl.TaskDslRepository;
import dev.ngdangkietswe.swetaskservice.data.repository.jpa.CdcAuthUserRepository;
import dev.ngdangkietswe.swetaskservice.data.repository.jpa.TaskRepository;
import dev.ngdangkietswe.swetaskservice.grpc.mapper.TaskGrpcMapper;
import dev.ngdangkietswe.swetaskservice.grpc.service.ITaskGrpcService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author ngdangkietswe
 * @since 11/21/2024
 */

@Service
@RequiredArgsConstructor
@Log4j2
public class TaskGrpcServiceImpl implements ITaskGrpcService {

    private final TaskRepository taskRepository;
    private final CdcAuthUserRepository cdcAuthUserRepository;

    private final TaskDslRepository taskDslRepository;

    private final TaskGrpcMapper taskGrpcMapper;

    @Override
    public UpsertResp upsertTask(UpsertTaskReq request, SweGrpcPrincipal principal) {
        var resp = UpsertResp.newBuilder();

        var userId = principal.getUserId();
        TaskEntity task;

        if (StringUtils.isNotEmpty(request.getId())) {
            UUID id = UUID.fromString(request.getId());
            task = taskRepository.findById(id)
                    .orElseThrow(() -> new GrpcNotFoundException(TaskEntity.class, "id", request.getId()));
            task.preUpdate(userId);
        } else {
            task = new TaskEntity();
            task.prePersist(userId);
        }

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus().getNumber());

        if (StringUtils.isNotEmpty(request.getReporterId())) {
            var reporter = cdcAuthUserRepository.findById(UUID.fromString(request.getReporterId()))
                    .orElseThrow(() -> new GrpcNotFoundException(CdcAuthUserEntity.class, "id", request.getReporterId()));
            task.setReporter(reporter);
        }

        if (StringUtils.isNotEmpty(request.getAssigneeId())) {
            var assignee = cdcAuthUserRepository.findById(UUID.fromString(request.getAssigneeId()))
                    .orElseThrow(() -> new GrpcNotFoundException(CdcAuthUserEntity.class, "id", request.getAssigneeId()));
            task.setAssignee(assignee);
        }

        taskRepository.save(task);

        return resp
                .setSuccess(true)
                .setData(UpsertResp.Data.newBuilder()
                        .setId(task.getId().toString())
                        .build())
                .build();
    }

    @Override
    public ListTaskResp listTask(ListTaskReq request, SweGrpcPrincipal principal) {
        var normalize = GrpcUtil.normalize(request.getPageable());
        var data = taskDslRepository.findAllByReq(request, normalize);
        return ListTaskResp.newBuilder()
                .setSuccess(true)
                .setData(ListTaskResp.Data.newBuilder()
                        .addAllTasks(taskGrpcMapper.toGrpcMessages(data.getItems()))
                        .setPageMetaData(GrpcUtil.asPageMetaData(normalize, data.getTotalItems()))
                        .build())
                .build();
    }

    @Override
    public GetTaskResp getTask(IdReq request, SweGrpcPrincipal principal) {
        return GetTaskResp.newBuilder().build();
    }

    @Override
    public EmptyResp deleteTask(IdReq request, SweGrpcPrincipal principal) {
        return null;
    }

    @Override
    public EmptyResp markTaskAsInProgress(IdReq request, SweGrpcPrincipal principal) {
        return null;
    }

    @Override
    public EmptyResp markTaskAsInReview(IdReq request, SweGrpcPrincipal principal) {
        return null;
    }

    @Override
    public EmptyResp markTaskAsDone(IdReq request, SweGrpcPrincipal principal) {
        return null;
    }

    @Override
    public EmptyResp markTaskAsCanceled(IdReq request, SweGrpcPrincipal principal) {
        return null;
    }
}
