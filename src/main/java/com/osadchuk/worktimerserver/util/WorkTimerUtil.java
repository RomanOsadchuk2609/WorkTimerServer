package com.osadchuk.worktimerserver.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Class with general utility methods
 */
public final class WorkTimerUtil {
	private WorkTimerUtil() {
	}

	public static LocalDateTime localDateTimeFromMillis(long millis) {
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault());
	}
}
