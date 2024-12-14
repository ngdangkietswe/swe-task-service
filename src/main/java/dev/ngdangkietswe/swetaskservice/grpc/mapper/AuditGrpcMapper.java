package dev.ngdangkietswe.swetaskservice.grpc.mapper;

import dev.ngdangkietswe.sweprotobufshared.common.protobuf.Audit;
import dev.ngdangkietswe.swetaskservice.data.entity.BaseEntity;
import dev.ngdangkietswe.swetaskservice.data.entity.CdcAuthUserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author ngdangkietswe
 * @since 12/15/2024
 */

@Component
@RequiredArgsConstructor
public class AuditGrpcMapper {

    private final UserGrpcMapper userGrpcMapper;

    public <E extends BaseEntity> Audit.Builder toGrpcBuilder(E entity,
                                                              CdcAuthUserEntity createdBy,
                                                              CdcAuthUserEntity updatedBy,
                                                              CdcAuthUserEntity deletedBy) {
        var builder = Audit.newBuilder()
                .setCreatedAt(entity.getCreatedAt().toString())
                .setCreatedBy(userGrpcMapper.toGrpcBuilder(createdBy))
                .setUpdatedAt(entity.getUpdatedAt().toString())
                .setUpdatedBy(userGrpcMapper.toGrpcBuilder(updatedBy));

        if (deletedBy != null) {
            builder.setIsDeleted(true).setDeletedBy(userGrpcMapper.toGrpcBuilder(deletedBy));
        }

        return builder;
    }

    public <E extends BaseEntity> Audit toGrpcMessage(E entity,
                                                      CdcAuthUserEntity createdBy,
                                                      CdcAuthUserEntity updatedBy,
                                                      CdcAuthUserEntity deletedBy) {
        return toGrpcBuilder(entity, createdBy, updatedBy, deletedBy).build();
    }
}
