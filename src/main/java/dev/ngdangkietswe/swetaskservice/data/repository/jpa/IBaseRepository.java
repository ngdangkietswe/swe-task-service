package dev.ngdangkietswe.swetaskservice.data.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author ngdangkietswe
 * @since 12/14/2024
 */

@NoRepositoryBean
public interface IBaseRepository<E extends Serializable> extends JpaRepository<E, UUID> {
}
