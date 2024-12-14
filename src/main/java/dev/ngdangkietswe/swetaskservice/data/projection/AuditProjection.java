package dev.ngdangkietswe.swetaskservice.data.projection;

import dev.ngdangkietswe.swetaskservice.data.entity.CdcAuthUserEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ngdangkietswe
 * @since 12/15/2024
 */

@Getter
@Setter
public class AuditProjection {

    private CdcAuthUserEntity createdBy;
    private CdcAuthUserEntity modifiedBy;
    private CdcAuthUserEntity deletedBy;
}
