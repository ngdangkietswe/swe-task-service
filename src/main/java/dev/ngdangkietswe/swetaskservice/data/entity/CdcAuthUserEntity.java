package dev.ngdangkietswe.swetaskservice.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author ngdangkietswe
 * @since 12/14/2024
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity(name = "cdc_auth_users")
public class CdcAuthUserEntity implements Serializable {

    @Id
    @Column(nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;
}
