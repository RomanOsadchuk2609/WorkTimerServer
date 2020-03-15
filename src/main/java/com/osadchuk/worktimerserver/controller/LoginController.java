package com.osadchuk.worktimerserver.controller;

import com.osadchuk.worktimerserver.entity.User;
import com.osadchuk.worktimerserver.model.dto.UserDTO;
import com.osadchuk.worktimerserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Controller for login page
 */
@Controller
@RequestMapping("/login")
public class LoginController {

	private final UserService userService;

	@Autowired
	public LoginController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public String login() {
		return "login";
	}

	@GetMapping("/token")
	public String loginViaToken(@RequestParam String token, Model model) {
		Optional<User> optionalUser = userService.findByToken(token);
		if (optionalUser.isPresent()) {
			model.addAttribute("userDTO", userService.convertIntoDTO(optionalUser.get()));
		} else {
			model.addAttribute("errorMessage", "Could not find user by invite token: " + token);
		}
		return "tokenLogin";
	}

	@PostMapping("/token")
	public String updatePassword(@ModelAttribute @Valid UserDTO userDTO,
	                             Model model) {
		if (userDTO.getPassword().equalsIgnoreCase(userDTO.getConfirmPassword())) {
			Optional<User> optionalUser = userService.findByUsername(userDTO.getUsername());
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			if (optionalUser.isPresent()) {
				User user = optionalUser.get();
				user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
				user.setToken(null);
				user.setEnabled(true);
				userService.save(user);
				model.addAttribute("successMessage", "Password was updated. Please login into your account");
			} else {
				model.addAttribute("errorMessage", "Could not find user");
			}
		} else {
			model.addAttribute("errorMessage", "Password does not matches");
		}
		return "tokenLogin";
	}
}
