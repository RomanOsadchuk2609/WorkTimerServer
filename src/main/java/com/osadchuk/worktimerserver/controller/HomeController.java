package com.osadchuk.worktimerserver.controller;

import com.osadchuk.worktimerserver.model.UserTime;
import com.osadchuk.worktimerserver.service.TimeLogService;
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
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Controller for home page
 */
@Controller
public class HomeController {

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	private final ControllerUtils controllerUtils;

	private final TimeLogService timeLogService;

	@Autowired
	public HomeController(ControllerUtils controllerUtils,
	                      TimeLogService timeLogService) {
		this.controllerUtils = controllerUtils;
		this.timeLogService = timeLogService;
	}

	@GetMapping
	public RedirectView homeRedirect(@AuthenticationPrincipal UserDetails userDetails, Model model) {
		return new RedirectView("/home");
	}

	@GetMapping("/home")
	public String home(@AuthenticationPrincipal UserDetails userDetails, Model model) {
		controllerUtils.fillModelWithUser(userDetails, model);
		LocalDateTime endDateTime = LocalDateTime.now();
		LocalDateTime startDateTime = endDateTime.minusDays(1);
		fillModelWithLoggedTime(startDateTime, endDateTime, model);
		return "home";
	}

	@GetMapping("/home/timeByDate")
	public String timeByDate(@AuthenticationPrincipal UserDetails userDetails,
	                         @RequestParam @Valid String startTime,
	                         @RequestParam @Valid String endTime,
	                         Model model) {
		controllerUtils.fillModelWithUser(userDetails, model);
		LocalDateTime startDateTime = LocalDateTime.of(LocalDate.parse(startTime, DATE_TIME_FORMATTER), LocalTime.MIN);
		LocalDateTime endDateTime = LocalDateTime.of(LocalDate.parse(endTime, DATE_TIME_FORMATTER), LocalTime.MIN);
		fillModelWithLoggedTime(startDateTime, endDateTime, model);
		return "home";
	}

	private void fillModelWithLoggedTime(LocalDateTime startDateTime, LocalDateTime endDateTime, Model model) {
		List<UserTime> usersTime = timeLogService.getUsersLoggedTime(startDateTime, endDateTime);
		model.addAttribute("startTime", DATE_TIME_FORMATTER.format(startDateTime));
		model.addAttribute("endTime", DATE_TIME_FORMATTER.format(endDateTime));
		if (!usersTime.isEmpty()) {
			model.addAttribute("usersTime", usersTime);
		} else {
			model.addAttribute("errorMessage", String.format("No logged time from %s to %s",
					DATE_TIME_FORMATTER.format(startDateTime), DATE_TIME_FORMATTER.format(endDateTime)));
		}
	}
}
