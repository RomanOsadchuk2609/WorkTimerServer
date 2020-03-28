package com.osadchuk.worktimerserver.repository;

import com.osadchuk.worktimerserver.entity.Screenshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * {@link Screenshot} JPA repository
 */
@Repository
public interface ScreenshotRepository extends JpaRepository<Screenshot, Long> {
	
	@Query("SELECT screenshot FROM Screenshot AS screenshot WHERE screenshot.timeLog.user.username = :username " +
			"AND screenshot.date >= :startTime AND screenshot.date <= :endTime ORDER BY screenshot.date")
	List<Screenshot> findAllByUsernameAndBetweenDates(@Param("username") String username,
	                                                  @Param("startTime") LocalDateTime startTime,
	                                                  @Param("endTime") LocalDateTime endTime);
}
