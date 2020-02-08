package com.osadchuk.worktimerserver.repository;

import com.osadchuk.worktimerserver.entity.Screenshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * {@link Screenshot} JPA repository
 */
@Repository
public interface ScreenshotRepository extends JpaRepository<Screenshot, Long> {
}
