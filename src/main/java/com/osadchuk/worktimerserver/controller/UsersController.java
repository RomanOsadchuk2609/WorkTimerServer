package com.osadchuk.worktimerserver.controller;

import com.osadchuk.worktimerserver.entity.User;
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
import org.thymeleaf.util.StringUtils;

import java.util.Collections;
import java.util.List;

/**
 * Controller for users page
 */
@Controller
@RequestMapping("/users")
@Slf4j
public class UsersController {

	private final ControllerUtils controllerUtils;

	private final UserService userService;

	@Autowired
	public UsersController(UserService userService,
	                       ControllerUtils controllerUtils) {
		this.controllerUtils = controllerUtils;
		this.userService = userService;
	}

	@GetMapping
	public String users(@AuthenticationPrincipal UserDetails userDetails, Model model) {
		controllerUtils.fillModelWithUser(userDetails, model);
		model.addAttribute("foundUsers", Collections.emptyList());
		return "users";
	}

	@GetMapping("/find")
	public String findUsers(@AuthenticationPrincipal UserDetails userDetails,
	                        @RequestParam String filter,
	                        Model model) {
		log.debug("username - {}", filter);
		controllerUtils.fillModelWithUser(userDetails, model);
		if (!StringUtils.isEmptyOrWhitespace(filter)) {
			List<User> foundUsers = userService.findAllByContainingFilterIgnoreCase(filter);
			model.addAttribute("foundUsers", userService.convertIntoDTO(foundUsers));
		}
		return "fragments/usersFragments :: foundUsers";
	}
}
