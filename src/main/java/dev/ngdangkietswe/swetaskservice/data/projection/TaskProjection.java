package dev.ngdangkietswe.swetaskservice.data.projection;

import dev.ngdangkietswe.swetaskservice.data.entity.TaskEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ngdangkietswe
 * @since 12/14/2024
 */

@Getter
@Setter
public class TaskProjection extends AuditProjection {

    private TaskEntity task;
}
