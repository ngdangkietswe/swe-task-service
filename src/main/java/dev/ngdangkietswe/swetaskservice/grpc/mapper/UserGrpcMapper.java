package dev.ngdangkietswe.swetaskservice.grpc.mapper;

import dev.ngdangkietswe.sweprotobufshared.common.protobuf.User;
import dev.ngdangkietswe.sweprotobufshared.proto.common.GrpcMapper;
import dev.ngdangkietswe.swetaskservice.data.entity.CdcAuthUserEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ngdangkietswe
 * @since 12/15/2024
 */

@Component
public class UserGrpcMapper extends GrpcMapper<CdcAuthUserEntity, User, User.Builder> {

    @Override
    public User.Builder toGrpcBuilder(CdcAuthUserEntity from) {
        return User.newBuilder()
                .setId(from.getId().toString())
                .setEmail(from.getEmail());
    }

    @Override
    public User toGrpcMessage(CdcAuthUserEntity from) {
        return toGrpcBuilder(from).build();
    }

    @Override
    public List<User> toGrpcMessages(List<CdcAuthUserEntity> from) {
        return super.toGrpcMessages(from);
    }
}
