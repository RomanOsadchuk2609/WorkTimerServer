package com.osadchuk.worktimerserver.model.dto;

import com.osadchuk.worktimerserver.entity.Screenshot;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for {@link Screenshot} entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ScreenshotDTO {
	
	private long id;
	
	private long timeLogId;
	
	private String date;
}
