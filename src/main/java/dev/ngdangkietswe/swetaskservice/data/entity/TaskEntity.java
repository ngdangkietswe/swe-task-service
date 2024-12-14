package dev.ngdangkietswe.swetaskservice.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @author ngdangkietswe
 * @since 12/14/2024
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "task")
public class TaskEntity extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    @Column
    private Integer status;

    @ManyToOne(targetEntity = CdcAuthUserEntity.class)
    @JoinColumn(
            name = "reporter_id",
            referencedColumnName = "id")
    private CdcAuthUserEntity reporter;

    @ManyToOne(targetEntity = CdcAuthUserEntity.class)
    @JoinColumn(
            name = "assignee_id",
            referencedColumnName = "id")
    private CdcAuthUserEntity assignee;

}
