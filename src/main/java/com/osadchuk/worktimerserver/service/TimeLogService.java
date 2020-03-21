package com.osadchuk.worktimerserver.service;

import com.osadchuk.worktimerserver.entity.TimeLog;
import com.osadchuk.worktimerserver.model.UserTime;
import com.osadchuk.worktimerserver.model.dto.TimeLogDTO;
import com.osadchuk.worktimerserver.repository.TimeLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

	public List<UserTime> getUsersLoggedTime(LocalDateTime startTime, LocalDateTime endTime) {
		List<UserTime> usersTime = timeLogRepository.findUserTimeBetweenDates(startTime, endTime);
		List<UserTime> transformedUserTime = new ArrayList<>();
		for (UserTime userTime : usersTime) {
			Optional<UserTime> optionalUser = transformedUserTime.stream()
					.filter(ut -> ut.getUserId() == userTime.getUserId())
					.findFirst();
			if (optionalUser.isPresent()) {
				optionalUser.get().setLoggedSeconds(optionalUser.get().getLoggedSeconds() + userTime.getLoggedSeconds());
			} else {
				transformedUserTime.add(userTime);
			}
		}
		return transformedUserTime;
	}

	public TimeLogDTO convertIntoDTO(TimeLog timeLog) {
		TimeLogDTO timeLogDTO = new TimeLogDTO();
		timeLogDTO.setId(timeLog.getId());
		timeLogDTO.setStartTime(timeLog.getStartTime());
		timeLogDTO.setEndTime(timeLog.getEndTime());
		long userId = timeLog.getUser() != null
				? timeLog.getUser().getId()
				: 0;
		timeLogDTO.setUserId(userId);
		long taskId = timeLog.getTask() != null
				? timeLog.getTask().getId()
				: 0;
		timeLogDTO.setTaskId(taskId);
		return timeLogDTO
				;
	}
}
