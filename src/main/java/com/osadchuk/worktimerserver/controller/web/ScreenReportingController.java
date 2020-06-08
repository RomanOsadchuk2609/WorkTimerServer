package com.osadchuk.worktimerserver.controller.web;

import com.osadchuk.worktimerserver.controller.util.ControllerUtil;
import com.osadchuk.worktimerserver.model.dto.ScreenshotDTO;
import com.osadchuk.worktimerserver.service.ScreenshotService;
import com.osadchuk.worktimerserver.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
@RequestMapping("/screenReporting")
@Slf4j
public class ScreenReportingController {
	
	private final ControllerUtil controllerUtil;
	
	private final UserService userService;
	
	private final ScreenshotService screenshotService;
	
	@Autowired
	public ScreenReportingController(ControllerUtil controllerUtil,
	                                 UserService userService,
	                                 ScreenshotService screenshotService) {
		this.controllerUtil = controllerUtil;
		this.userService = userService;
		this.screenshotService = screenshotService;
	}
	
	@GetMapping
	public String screenReporting(@AuthenticationPrincipal UserDetails userDetails, Model model) {
		controllerUtil.fillModelWithUser(userDetails, model);
		LocalDate now = LocalDate.now();
		fillModel(userDetails.getUsername(), now, now, model);
		return "screenReporting";
	}
	
	@GetMapping("/byUserAndDate")
	public String timeByDate(@AuthenticationPrincipal UserDetails userDetails,
	                         @RequestParam @Valid String username,
	                         @RequestParam @Valid String startTime,
	                         @RequestParam @Valid String endTime,
	                         Model model) {
		controllerUtil.fillModelWithUser(userDetails, model);
		LocalDate startDate = LocalDate.parse(startTime, DATE_FORMATTER);
		LocalDate endDate = LocalDate.parse(endTime, DATE_FORMATTER);
		fillModel(username, startDate, endDate, model);
		return "screenReporting";
	}
	
	private void fillModel(String username, LocalDate startDate, LocalDate endDate, Model model) {
		LocalDateTime startDateTime = LocalDateTime.of(startDate, LocalTime.MIN);
		LocalDateTime endDateTime = LocalDateTime.of(endDate, LocalTime.MAX);
		List<ScreenshotDTO> screenshotDTOList = screenshotService.convertIntoDTO(
				screenshotService.findAllByUsernameAndBetweenDates(username, startDateTime, endDateTime));
		model.addAttribute("users", userService.convertIntoDTO(userService.findAll()));
		model.addAttribute("username", username);
		model.addAttribute("startTime", startDate);
		model.addAttribute("endTime", endDate);
		if (!screenshotDTOList.isEmpty()) {
			model.addAttribute("screenshotDTOList", screenshotDTOList);
		} else {
			model.addAttribute("errorMessage", String.format("No screenshots for %s from %s to %s",
					username, DATE_TIME_FORMATTER.format(startDateTime), DATE_TIME_FORMATTER.format(endDateTime)));
		}
	}
}
