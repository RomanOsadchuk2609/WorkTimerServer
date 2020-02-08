package com.osadchuk.worktimerserver.repository;

import com.osadchuk.worktimerserver.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * {@link Role} JPA repository
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
