package com.osadchuk.worktimerserver.repository;

import com.osadchuk.worktimerserver.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * {@link Task} JPA repository
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
