package com.osadchuk.worktimerserver.controller;

import com.osadchuk.worktimerserver.model.dto.UserDTO;
import com.osadchuk.worktimerserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.Optional;

@Component
public class ControllerUtils {
	private final UserService userService;

	@Autowired
	public ControllerUtils(UserService userService) {
		this.userService = userService;
	}

	public void fillModelWithUser(@AuthenticationPrincipal UserDetails userDetails, Model model) {
		Optional<UserDTO> optionalUser = userService.findByUsernameAsDTO(userDetails.getUsername());
		optionalUser.ifPresent(user -> model.addAttribute("user", user));
	}
}
