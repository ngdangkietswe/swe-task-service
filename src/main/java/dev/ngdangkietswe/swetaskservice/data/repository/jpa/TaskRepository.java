package dev.ngdangkietswe.swetaskservice.data.repository.jpa;

import dev.ngdangkietswe.swetaskservice.data.entity.TaskEntity;
import org.springframework.stereotype.Repository;

/**
 * @author ngdangkietswe
 * @since 12/14/2024
 */

@Repository
public interface TaskRepository extends IBaseRepository<TaskEntity> {
}
