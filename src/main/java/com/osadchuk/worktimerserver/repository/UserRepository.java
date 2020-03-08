package com.osadchuk.worktimerserver.repository;

import com.osadchuk.worktimerserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * {@link User} JPA repository
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

	List<User> findAllByUsernameContainingIgnoreCase(String username);

	List<User> findAllByFirstNameContainingIgnoreCase(String username);

	List<User> findAllByLastNameContainingIgnoreCase(String username);

	List<User> findAllByPhoneNumberContainingIgnoreCase(String username);
}
