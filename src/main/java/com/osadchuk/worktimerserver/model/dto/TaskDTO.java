package com.osadchuk.worktimerserver.model.dto;

import com.osadchuk.worktimerserver.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO for {@link Task} entity
 */
@Data
@AllArgsConstructor
public class TaskDTO {
	private long id;

	private String name;

	private String description;

	private long userId;
}
