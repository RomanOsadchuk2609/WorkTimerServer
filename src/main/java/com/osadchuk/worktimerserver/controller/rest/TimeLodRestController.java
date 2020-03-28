package com.osadchuk.worktimerserver.controller.rest;

import com.osadchuk.worktimerserver.entity.Screenshot;
import com.osadchuk.worktimerserver.entity.Task;
import com.osadchuk.worktimerserver.entity.TimeLog;
import com.osadchuk.worktimerserver.entity.User;
import com.osadchuk.worktimerserver.model.dto.TimeLogDTO;
import com.osadchuk.worktimerserver.service.ScreenshotService;
import com.osadchuk.worktimerserver.service.TaskService;
import com.osadchuk.worktimerserver.service.TimeLogService;
import com.osadchuk.worktimerserver.service.UserService;
import com.osadchuk.worktimerserver.util.WorkTimerConstants;
import com.osadchuk.worktimerserver.util.WorkTimerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Decoder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.Optional;

@RestController
@RequestMapping("/time_log")
@Slf4j
public class TimeLodRestController {
	private final UserService userService;
	private final TaskService taskService;
	private final TimeLogService timeLogService;
	private final ScreenshotService screenshotService;

	public TimeLodRestController(UserService userService,
	                             TaskService taskService,
	                             TimeLogService timeLogService,
	                             ScreenshotService screenshotService) {
		this.userService = userService;
		this.taskService = taskService;
		this.timeLogService = timeLogService;
		this.screenshotService = screenshotService;
	}

	@PostMapping("/login")
	public String login(@RequestParam(value = "authToken") String authTokenBase64) {
		log.debug("/time_log/login");
		try {
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] authTokenBytes = decoder.decodeBuffer(authTokenBase64);
			ByteArrayInputStream bis = new ByteArrayInputStream(authTokenBytes);
			ObjectInput in = new ObjectInputStream(bis);
			Authentication authToken = (UsernamePasswordAuthenticationToken) in.readObject();
			SecurityContextHolder.getContext().setAuthentication(authToken);
			return "Login successfully!";
		} catch (AuthenticationException | IOException | ClassNotFoundException e) {
			log.error("Login failure, please try again.", e);
			return "Login failure, please try again.";
		}
	}

	@GetMapping("/get_user_icon")
	public String userIcon() {
		log.debug("/time_log/get_user_icon");
		return WorkTimerConstants.DEFAULT_USER_ICON_BASE64;
	}


	@PostMapping("/create")
	@ResponseBody
	public TimeLogDTO createNewTimer(@AuthenticationPrincipal UserDetails userDetails,
	                                 @RequestParam(value = "user_id") long userId,
	                                 @RequestParam(value = "task_id") long taskId,
	                                 @RequestParam(value = "start_time") long startTime) {
		log.debug("/time_log/create");
		Optional<User> user = userId != 0
				? userService.findById(userId)
				: userService.findByUsername(userDetails.getUsername());
		if (user.isPresent()) {
			TimeLog timeLog = new TimeLog();
			timeLog.setUser(user.get());
			Optional<Task> task = taskService.findById(taskId);
			task.ifPresent(timeLog::setTask);
			timeLog.setStartTime(WorkTimerUtil.localDateTimeFromMillis(startTime));
			timeLog = timeLogService.save(timeLog);
			return timeLogService.convertIntoDTO(timeLog);
		} else {
			return null;
		}
	}

	@PostMapping("/stop")
	@ResponseBody
	public TimeLogDTO stopWork(@AuthenticationPrincipal UserDetails userDetails,
	                           @RequestParam(value = "time_log_id") long timeLogId,
	                           @RequestParam(value = "end_time") long endTime) {
		log.debug("/time_log/stop");
		Optional<TimeLog> optionalTimeLog = timeLogService.findById(timeLogId);
		if (optionalTimeLog.isPresent()) {
			TimeLog timeLog = optionalTimeLog.get();
			timeLog.setEndTime(WorkTimerUtil.localDateTimeFromMillis(endTime));
			timeLog = timeLogService.save(timeLog);
			return timeLogService.convertIntoDTO(timeLog);
		} else {
			return null;
		}
	}

	@PostMapping("/save_screenshot")
	@ResponseBody
	public Screenshot saveScreenShot(@AuthenticationPrincipal UserDetails userDetails,
	                                 @RequestParam(value = "time_log_id") long timeLogId,
	                                 @RequestParam(value = "screenshot") String base64ScreenShot,
	                                 @RequestParam(value = "date") long date) {
		log.debug("/time_log/save_screenshot");
		Optional<TimeLog> optionalTimeLog = timeLogService.findById(timeLogId);
		if (optionalTimeLog.isPresent()) {
			TimeLog timeLog = optionalTimeLog.get();
			timeLog.setEndTime(WorkTimerUtil.localDateTimeFromMillis(date));
			timeLog = timeLogService.save(timeLog);
			Screenshot screenshot = new Screenshot();
			screenshot.setBase64(base64ScreenShot);
			screenshot.setTimeLog(timeLog);
			screenshot.setDate(WorkTimerUtil.localDateTimeFromMillis(date));
			return screenshotService.save(screenshot);
		} else {
			return null;
		}
	}
}
