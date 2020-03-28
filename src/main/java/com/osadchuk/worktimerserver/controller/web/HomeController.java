package com.osadchuk.worktimerserver.controller.web;

import com.osadchuk.worktimerserver.controller.util.ControllerUtil;
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
import java.util.List;

import static com.osadchuk.worktimerserver.util.WorkTimerConstants.DATE_FORMATTER;
import static com.osadchuk.worktimerserver.util.WorkTimerConstants.DATE_TIME_FORMATTER;

/**
 * Controller for home page
 */
@Controller
public class HomeController {
	
	private final ControllerUtil controllerUtil;
	
	private final TimeLogService timeLogService;

	@Autowired
	public HomeController(ControllerUtil controllerUtil,
	                      TimeLogService timeLogService) {
		this.controllerUtil = controllerUtil;
		this.timeLogService = timeLogService;
	}

	@GetMapping
	public RedirectView homeRedirect(@AuthenticationPrincipal UserDetails userDetails, Model model) {
		return new RedirectView("/home");
	}

	@GetMapping("/home")
	public String home(@AuthenticationPrincipal UserDetails userDetails, Model model) {
		controllerUtil.fillModelWithUser(userDetails, model);
		LocalDate now = LocalDate.now();
		fillModelWithLoggedTime(now, now, model);
		return "home";
	}

	@GetMapping("/home/timeByDate")
	public String timeByDate(@AuthenticationPrincipal UserDetails userDetails,
	                         @RequestParam @Valid String startTime,
	                         @RequestParam @Valid String endTime,
	                         Model model) {
		controllerUtil.fillModelWithUser(userDetails, model);
		LocalDate startDate = LocalDate.parse(startTime, DATE_FORMATTER);
		LocalDate endDate = LocalDate.parse(endTime, DATE_FORMATTER);
		fillModelWithLoggedTime(startDate, endDate, model);
		return "home";
	}

	private void fillModelWithLoggedTime(LocalDate startDate, LocalDate endDate, Model model) {
		LocalDateTime startDateTime = LocalDateTime.of(startDate, LocalTime.MIN);
		LocalDateTime endDateTime = LocalDateTime.of(endDate, LocalTime.MAX);
		List<UserTime> usersTime = timeLogService.getUsersLoggedTime(startDateTime, endDateTime);
		model.addAttribute("startTime", DATE_FORMATTER.format(startDateTime));
		model.addAttribute("endTime", DATE_FORMATTER.format(endDateTime));
		if (!usersTime.isEmpty()) {
			model.addAttribute("usersTime", usersTime);
		} else {
			model.addAttribute("errorMessage", String.format("No logged time from %s to %s",
					DATE_TIME_FORMATTER.format(startDateTime), DATE_TIME_FORMATTER.format(endDateTime)));
		}
	}
}
