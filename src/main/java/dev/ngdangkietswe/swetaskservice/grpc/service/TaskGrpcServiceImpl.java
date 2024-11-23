package dev.ngdangkietswe.swetaskservice.grpc.service;

import dev.ngdangkietswe.sweprotobufshared.common.protobuf.IdReq;
import dev.ngdangkietswe.sweprotobufshared.common.protobuf.Pageable;
import dev.ngdangkietswe.sweprotobufshared.common.protobuf.UpsertResp;
import dev.ngdangkietswe.sweprotobufshared.proto.common.GrpcUtil;
import dev.ngdangkietswe.sweprotobufshared.task.protobuf.Status;
import dev.ngdangkietswe.sweprotobufshared.task.protobuf.Task;
import dev.ngdangkietswe.sweprotobufshared.task.service.ListTaskResp;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author ngdangkietswe
 * @since 11/21/2024
 */

@Service
@Log4j2
public class TaskGrpcServiceImpl implements ITaskGrpcService {

    @Override
    public UpsertResp upsertTask(Task request) {
        return UpsertResp.newBuilder()
                .setSuccess(true)
                .setData(UpsertResp.Data.newBuilder()
                        .setId(StringUtils.defaultIfEmpty(request.getId(), UUID.randomUUID().toString()))
                        .build())
                .build();
    }

    @Override
    public ListTaskResp listTask(Pageable request) {
        var principal = GrpcUtil.getGrpcPrincipal();
        log.info("Method listTask is called by user: {} with id: {}", principal.getUsername(), principal.getUserId());
        return ListTaskResp.newBuilder()
                .setSuccess(true)
                .setData(ListTaskResp.Data.newBuilder()
                        .addAllTasks(List.of(
                                Task.newBuilder()
                                        .setId(UUID.randomUUID().toString())
                                        .setName("Task 1")
                                        .setStatus(Status.STATUS_PENDING)
                                        .build(),
                                Task.newBuilder()
                                        .setId(UUID.randomUUID().toString())
                                        .setName("Task 2")
                                        .setStatus(Status.STATUS_COMPLETED)
                                        .build(),
                                Task.newBuilder()
                                        .setId(UUID.randomUUID().toString())
                                        .setName("Task 3")
                                        .setStatus(Status.STATUS_PROCESSING)
                                        .build()
                        ))
                        .build())
                .build();
    }

    @Override
    public Task getTask(IdReq request) {
        // TODO
        return null;
    }
}
