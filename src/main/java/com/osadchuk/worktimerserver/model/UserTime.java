package com.osadchuk.worktimerserver.model;

import com.osadchuk.worktimerserver.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
public class UserTime {

	private long userId;

	private String username;

	private String fullName;

	private long loggedSeconds;

	public UserTime(User user, LocalDateTime startTime, LocalDateTime endTime) {
		this.userId = user.getId();
		this.username = user.getUsername();
		this.fullName = String.join(" ", user.getFirstName(), user.getLastName());
		this.loggedSeconds = Duration.between(startTime, endTime).getSeconds();
	}

	public String getFormattedLoggedTime() {
		Duration duration = Duration.ofSeconds(loggedSeconds);
		long hours = duration.toHours();
		long minutes = duration.toMinutes();
		long seconds = duration.getSeconds();
		if (hours > 0) {
			minutes -= hours * 60;
			seconds -= minutes * 60;
			return String.format("%sh %sm %ss", hours, minutes, seconds);
		} else if (minutes > 0) {
			seconds -= minutes * 60;
			return String.format("%sm %ss", minutes, seconds);
		} else {
			return seconds + "s";
		}
	}
}
