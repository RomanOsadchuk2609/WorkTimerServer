package com.osadchuk.worktimerserver.repository;

import com.osadchuk.worktimerserver.entity.Settings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * {@link Settings} JPA repository
 */
@Repository
public interface SettingsRepository extends JpaRepository<Settings, Long> {
	Optional<Settings> findByName(String name);
}
