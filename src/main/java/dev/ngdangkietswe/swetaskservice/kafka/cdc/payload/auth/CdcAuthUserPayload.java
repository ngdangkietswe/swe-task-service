package dev.ngdangkietswe.swetaskservice.kafka.cdc.payload.auth;

import dev.ngdangkietswe.swetaskservice.data.entity.CdcAuthUserEntity;
import dev.ngdangkietswe.swetaskservice.kafka.cdc.payload.CdcBasePayload;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ngdangkietswe
 * @since 12/14/2024
 */

@Getter
@Setter
public class CdcAuthUserPayload extends CdcBasePayload<CdcAuthUserEntity> {

    private String username;
    private String email;

    @Override
    public CdcAuthUserEntity toEntity() {
        return CdcAuthUserEntity.builder()
                .id(this.getId())
                .username(this.username)
                .email(this.email)
                .build();
    }
}
