package com.osadchuk.worktimerserver.repository;

import com.osadchuk.worktimerserver.entity.TimeLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * {@link TimeLog} JPA repository
 */
@Repository
public interface TimeLogRepository extends JpaRepository<TimeLog, Long> {
}
