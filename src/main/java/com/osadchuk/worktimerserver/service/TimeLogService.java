package com.osadchuk.worktimerserver.service;

import com.osadchuk.worktimerserver.entity.TimeLog;
import com.osadchuk.worktimerserver.repository.TimeLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for operations with {@link TimeLog} entity
 */
@Service
@Slf4j
public class TimeLogService implements CrudService<TimeLog> {
	private final TimeLogRepository timeLogRepository;

	@Autowired
	public TimeLogService(TimeLogRepository timeLogRepository) {
		this.timeLogRepository = timeLogRepository;
	}

	@Override
	public List<TimeLog> findAll() {
		return timeLogRepository.findAll();
	}

	@Override
	public Optional<TimeLog> findById(long id) {
		return timeLogRepository.findById(id);
	}

	@Override
	public TimeLog save(TimeLog entity) {
		return timeLogRepository.save(entity);
	}

	@Override
	public void delete(TimeLog entity) {
		timeLogRepository.delete(entity);
	}

	@Override
	public void deleteById(long id) {
		timeLogRepository.deleteById(id);
	}
}
