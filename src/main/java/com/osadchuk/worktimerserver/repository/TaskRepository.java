package com.osadchuk.worktimerserver.repository;

import com.osadchuk.worktimerserver.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * {@link Task} JPA repository
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
	Optional<Task> findByUserUsername(String username);

	List<Task> findAllByUserUsername(String username);

	List<Task> findAllByUserIsNull();
}
