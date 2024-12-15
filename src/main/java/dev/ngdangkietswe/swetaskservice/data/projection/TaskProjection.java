package dev.ngdangkietswe.swetaskservice.data.projection;

import dev.ngdangkietswe.swetaskservice.data.entity.TaskEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author ngdangkietswe
 * @since 12/14/2024
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskProjection {

    private TaskEntity task;
    private AuditProjection audit;
}
