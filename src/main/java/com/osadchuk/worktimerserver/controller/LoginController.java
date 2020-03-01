package com.osadchuk.worktimerserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for login page
 */
@Controller
@RequestMapping("/login")
public class LoginController {

	@GetMapping
	public String login() {
		return "login";
	}
}
