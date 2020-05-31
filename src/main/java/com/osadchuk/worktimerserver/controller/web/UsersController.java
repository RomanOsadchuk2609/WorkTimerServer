package com.osadchuk.worktimerserver.controller.web;

import com.osadchuk.worktimerserver.controller.util.ControllerUtil;
import com.osadchuk.worktimerserver.entity.User;
import com.osadchuk.worktimerserver.model.NewUser;
import com.osadchuk.worktimerserver.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

/**
 * Controller for users page
 */
@Controller
@RequestMapping("/users")
@Slf4j
public class UsersController {

	private static final String ALL_USERS = "*";

	private final ControllerUtil controllerUtil;

	private final UserService userService;

	@Autowired
	public UsersController(UserService userService,
	                       ControllerUtil controllerUtil) {
		this.controllerUtil = controllerUtil;
		this.userService = userService;
	}

	@GetMapping
	public String users(@AuthenticationPrincipal UserDetails userDetails, Model model) {
		controllerUtil.fillModelWithUser(userDetails, model);
		model.addAttribute("foundUsers", Collections.emptyList());
		model.addAttribute("newUser", new NewUser());
		return "users";
	}

	@GetMapping("/find")
	public String findUsers(@AuthenticationPrincipal UserDetails userDetails,
	                        @RequestParam String filter,
	                        Model model) {
		controllerUtil.fillModelWithUser(userDetails, model);
		if (Strings.isNotBlank(filter)) {
			List<User> foundUsers;
			if (ALL_USERS.equals(filter)) {
				foundUsers = userService.findAllOrderByUsername();
			} else {
				foundUsers = userService.findAllByContainingFilterIgnoreCase(filter);
			}
			model.addAttribute("foundUsers", userService.convertIntoDTO(foundUsers));
		}
		return "fragments/usersFragments :: foundUsers";
	}

	@PostMapping("/add")
	public String addUser(@AuthenticationPrincipal UserDetails userDetails,
	                      Model model,
	                      @ModelAttribute @Valid NewUser newUser,
	                      BindingResult result) {
		controllerUtil.fillModelWithUser(userDetails, model);
		model.addAttribute("foundUsers", Collections.emptyList());
		model.addAttribute("newUser", newUser);
		if (!result.hasErrors()) {
			newUser = userService.addUser(newUser);
			if (Strings.isNotBlank(newUser.getError())) {
				model.addAttribute("errorMessage", newUser.getError());
			} else {
				model.addAttribute("successMessage",
						"User " + newUser.getUsername() + " was created. Invite token: " + newUser.getToken());
			}
		}
		return "users";
	}
}
