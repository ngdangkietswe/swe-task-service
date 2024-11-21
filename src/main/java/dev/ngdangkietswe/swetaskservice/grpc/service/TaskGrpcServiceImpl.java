package dev.ngdangkietswe.swetaskservice.grpc.service;

import dev.ngdangkietswe.sweprotobufshared.common.protobuf.IdReq;
import dev.ngdangkietswe.sweprotobufshared.common.protobuf.Pageable;
import dev.ngdangkietswe.sweprotobufshared.common.protobuf.UpsertResp;
import dev.ngdangkietswe.sweprotobufshared.task.protobuf.Status;
import dev.ngdangkietswe.sweprotobufshared.task.protobuf.Task;
import dev.ngdangkietswe.sweprotobufshared.task.service.ListTaskResp;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author ngdangkietswe
 * @since 11/21/2024
 */

@Service
public class TaskGrpcServiceImpl implements ITaskGrpcService {

    @Override
    public UpsertResp upsertTask(Task request) {
        return UpsertResp.newBuilder()
                .setData(UpsertResp.Data.newBuilder()
                        .setId(StringUtils.defaultIfEmpty(request.getId(), UUID.randomUUID().toString()))
                        .build())
                .build();
    }

    @Override
    public ListTaskResp listTask(Pageable request) {
        return ListTaskResp.newBuilder()
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
