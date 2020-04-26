package com.osadchuk.worktimerserver.controller.web;

import com.osadchuk.worktimerserver.controller.util.ControllerUtil;
import com.osadchuk.worktimerserver.model.UserTime;
import com.osadchuk.worktimerserver.service.TimeLogService;
import com.osadchuk.worktimerserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.osadchuk.worktimerserver.util.WorkTimerConstants.DATE_FORMATTER;
import static com.osadchuk.worktimerserver.util.WorkTimerConstants.DATE_TIME_FORMATTER;

/**
 * Controller for home page
 */
@Controller
public class HomeController {

	private static final String ALL_USERS = "*";

	private final ControllerUtil controllerUtil;

	private final TimeLogService timeLogService;

	private final UserService userService;

	@Autowired
	public HomeController(ControllerUtil controllerUtil,
	                      TimeLogService timeLogService,
	                      UserService userService) {
		this.controllerUtil = controllerUtil;
		this.timeLogService = timeLogService;
		this.userService = userService;
	}

	@GetMapping
	public RedirectView homeRedirect() {
		return new RedirectView("/home");
	}

	@GetMapping("/home")
	public String home(@AuthenticationPrincipal UserDetails userDetails, Model model) {
		controllerUtil.fillModelWithUser(userDetails, model);
		LocalDate now = LocalDate.now();
		fillModelWithLoggedTime(ALL_USERS, now, now, model);
		return "home";
	}

	@GetMapping("/home/timeByDate")
	public String timeByDate(@AuthenticationPrincipal UserDetails userDetails,
	                         @RequestParam @Valid String username,
	                         @RequestParam @Valid String startTime,
	                         @RequestParam @Valid String endTime,
	                         Model model) {
		controllerUtil.fillModelWithUser(userDetails, model);
		LocalDate startDate = LocalDate.parse(startTime, DATE_FORMATTER);
		LocalDate endDate = LocalDate.parse(endTime, DATE_FORMATTER);
		fillModelWithLoggedTime(username, startDate, endDate, model);
		return "home";
	}

	private void fillModelWithLoggedTime(String username, LocalDate startDate, LocalDate endDate, Model model) {
		LocalDateTime startDateTime = LocalDateTime.of(startDate, LocalTime.MIN);
		LocalDateTime endDateTime = LocalDateTime.of(endDate, LocalTime.MAX);
		List<UserTime> usersTime = timeLogService.getUsersLoggedTime(startDateTime, endDateTime);
		if (!ALL_USERS.equals(username)) {
			usersTime = usersTime.stream()
					.filter(userTime -> username.equals(userTime.getUsername()))
					.collect(Collectors.toList());
		}
		model.addAttribute("startTime", DATE_FORMATTER.format(startDateTime));
		model.addAttribute("endTime", DATE_FORMATTER.format(endDateTime));
		model.addAttribute("username", username);
		model.addAttribute("users", userService.convertIntoDTO(userService.findAll()));
		if (!usersTime.isEmpty()) {
			model.addAttribute("usersTime", usersTime);
		} else {
			model.addAttribute("errorMessage", String.format("No logged time from %s to %s",
					DATE_TIME_FORMATTER.format(startDateTime), DATE_TIME_FORMATTER.format(endDateTime)));
		}
	}
}
