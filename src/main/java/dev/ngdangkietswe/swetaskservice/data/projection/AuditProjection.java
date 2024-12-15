package dev.ngdangkietswe.swetaskservice.data.projection;

import dev.ngdangkietswe.swetaskservice.data.entity.CdcAuthUserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author ngdangkietswe
 * @since 12/15/2024
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuditProjection {

    private CdcAuthUserEntity createdBy;
    private CdcAuthUserEntity modifiedBy;
    private CdcAuthUserEntity deletedBy;
}
