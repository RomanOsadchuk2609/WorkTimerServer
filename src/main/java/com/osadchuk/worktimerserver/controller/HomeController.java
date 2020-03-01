package com.osadchuk.worktimerserver.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for home page
 */
@Controller
@RequestMapping("/home")
public class HomeController {
	@GetMapping
	public String home(@AuthenticationPrincipal UserDetails userDetails, Model model) {
		model.addAttribute("user", userDetails.getUsername());
		return "home";
	}
}
