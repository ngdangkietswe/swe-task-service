package dev.ngdangkietswe.swetaskservice.grpc.service;

import dev.ngdangkietswe.sweprotobufshared.common.protobuf.IdReq;
import dev.ngdangkietswe.sweprotobufshared.common.protobuf.Pageable;
import dev.ngdangkietswe.sweprotobufshared.common.protobuf.UpsertResp;
import dev.ngdangkietswe.sweprotobufshared.task.protobuf.Task;
import dev.ngdangkietswe.sweprotobufshared.task.service.ListTaskResp;

/**
 * @author ngdangkietswe
 * @since 11/21/2024
 */

public interface ITaskGrpcService {

    UpsertResp upsertTask(Task request);

    ListTaskResp listTask(Pageable request);

    Task getTask(IdReq request);
}
