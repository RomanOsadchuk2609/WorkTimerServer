package com.osadchuk.worktimerserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Controller for home page
 */
@Controller
public class HomeController {

	private final ControllerUtils controllerUtils;

	@Autowired
	public HomeController(ControllerUtils controllerUtils) {
		this.controllerUtils = controllerUtils;
	}

	@GetMapping("/home")
	public String home(@AuthenticationPrincipal UserDetails userDetails, Model model) {
		controllerUtils.fillModelWithUser(userDetails, model);
		return "home";
	}

	@GetMapping
	public RedirectView homeRedirect(@AuthenticationPrincipal UserDetails userDetails, Model model) {
		return new RedirectView("/home");
	}
}
