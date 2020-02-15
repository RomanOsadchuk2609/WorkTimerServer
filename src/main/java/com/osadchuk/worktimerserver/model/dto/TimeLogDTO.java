package com.osadchuk.worktimerserver.model.dto;

import com.osadchuk.worktimerserver.entity.TimeLog;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO for {@link TimeLog} entity
 */
@Data
public class TimeLogDTO {
	private long id;

	private LocalDateTime startTime;

	private LocalDateTime endTime;

	private long userId;

	private long taskId;

}
