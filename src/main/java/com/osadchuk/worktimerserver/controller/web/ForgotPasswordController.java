/*
 * Copyright (c) 2020. Roman Osadchuk.
 */

package com.osadchuk.worktimerserver.controller.web;

import com.osadchuk.worktimerserver.entity.User;
import com.osadchuk.worktimerserver.service.PasswordRecoveryService;
import com.osadchuk.worktimerserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/forgotPassword")
public class ForgotPasswordController {

	private final PasswordRecoveryService passwordRecoveryService;

	private final UserService userService;

	@Autowired
	public ForgotPasswordController(PasswordRecoveryService passwordRecoveryService,
	                                UserService userService) {
		this.passwordRecoveryService = passwordRecoveryService;
		this.userService = userService;
	}

	@GetMapping
	public String getForgotPasswordPage() {
		return "forgotPassword";
	}

	@PostMapping
	public String getForgotPasswordPage(@RequestParam String username, Model model) {
		Optional<User> optionalUser = userService.findByUsername(username);
		if (optionalUser.isPresent() && optionalUser.get().isEnabled()) {
			boolean result = passwordRecoveryService.recoverPassword(username);
			if (result) {
				model.addAttribute("successMessage", "SMS with invite token was sent to number "
						+ optionalUser.get().getPhoneNumber());
			} else {
				model.addAttribute("errorMessage", "Could not sent SMS with invite token to number "
						+ optionalUser.get().getPhoneNumber());
			}
		} else {
			model.addAttribute("errorMessage", "Could not recover password of user " + username);
		}
		return "forgotPassword";
	}
}
