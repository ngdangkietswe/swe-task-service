package dev.ngdangkietswe.swetaskservice.kafka.cdc.payload;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author ngdangkietswe
 * @since 12/14/2024
 */

@Getter
@Setter
public abstract class CdcBasePayload<E extends Serializable> implements Serializable {

    private UUID id;

    public abstract E toEntity();
}
