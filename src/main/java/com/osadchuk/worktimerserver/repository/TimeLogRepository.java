package com.osadchuk.worktimerserver.repository;

import com.osadchuk.worktimerserver.entity.TimeLog;
import com.osadchuk.worktimerserver.model.UserTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * {@link TimeLog} JPA repository
 */
@Repository
public interface TimeLogRepository extends JpaRepository<TimeLog, Long> {

	@Query("SELECT new com.osadchuk.worktimerserver.model.UserTime(timeLog.user, timeLog.startTime, timeLog.endTime) " +
			"FROM TimeLog AS timeLog WHERE timeLog.startTime >= :startTime AND timeLog.endTime <= :endTime " +
			"ORDER BY timeLog.user.username")
	List<UserTime> findUserTimeBetweenDates(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
}
