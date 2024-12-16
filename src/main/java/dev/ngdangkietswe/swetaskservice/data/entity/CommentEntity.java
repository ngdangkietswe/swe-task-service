package dev.ngdangkietswe.swetaskservice.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

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
@Table(name = "comment")
public class CommentEntity extends BaseEntity {

    @Column
    private String content;

    @ManyToOne(
            fetch = FetchType.LAZY,
            targetEntity = TaskEntity.class)
    @JoinColumn(
            name = "task_id",
            referencedColumnName = "id")
    private TaskEntity task;

    @ManyToOne(
            fetch = FetchType.LAZY,
            targetEntity = CommentEntity.class)
    @JoinColumn(
            name = "parent_id",
            referencedColumnName = "id")
    private CommentEntity parent;

    @OneToMany(
            mappedBy = "parent",
            fetch = FetchType.EAGER,
            targetEntity = CommentEntity.class)
    private List<CommentEntity> children;
}
