package com.osadchuk.worktimerserver.repository;

import com.osadchuk.worktimerserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * {@link User} JPA repository
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
}
