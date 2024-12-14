package dev.ngdangkietswe.swetaskservice.grpc.service;

import dev.ngdangkietswe.sweprotobufshared.common.protobuf.EmptyResp;
import dev.ngdangkietswe.sweprotobufshared.common.protobuf.IdReq;
import dev.ngdangkietswe.sweprotobufshared.common.protobuf.UpsertResp;
import dev.ngdangkietswe.sweprotobufshared.proto.domain.SweGrpcPrincipal;
import dev.ngdangkietswe.sweprotobufshared.task.service.GetTaskResp;
import dev.ngdangkietswe.sweprotobufshared.task.service.ListTaskReq;
import dev.ngdangkietswe.sweprotobufshared.task.service.ListTaskResp;
import dev.ngdangkietswe.sweprotobufshared.task.service.UpsertTaskReq;

/**
 * @author ngdangkietswe
 * @since 11/21/2024
 */

public interface ITaskGrpcService {

    UpsertResp upsertTask(UpsertTaskReq request, SweGrpcPrincipal principal);

    ListTaskResp listTask(ListTaskReq request, SweGrpcPrincipal principal);

    GetTaskResp getTask(IdReq request, SweGrpcPrincipal principal);

    EmptyResp deleteTask(IdReq request, SweGrpcPrincipal principal);

    EmptyResp markTaskAsInProgress(IdReq request, SweGrpcPrincipal principal);

    EmptyResp markTaskAsInReview(IdReq request, SweGrpcPrincipal principal);

    EmptyResp markTaskAsDone(IdReq request, SweGrpcPrincipal principal);

    EmptyResp markTaskAsCanceled(IdReq request, SweGrpcPrincipal principal);
}
