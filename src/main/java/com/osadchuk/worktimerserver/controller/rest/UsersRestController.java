/*
 * Copyright (c) 2020. Roman Osadchuk.
 */

package com.osadchuk.worktimerserver.controller.rest;

import com.osadchuk.worktimerserver.entity.User;
import com.osadchuk.worktimerserver.service.SmsSeder;
import com.osadchuk.worktimerserver.service.UserService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UsersRestController {

	private final UserService userService;

	private final SmsSeder smsSeder;

	@Autowired
	public UsersRestController(UserService userService, SmsSeder smsSeder) {
		this.userService = userService;
		this.smsSeder = smsSeder;
	}

	@PostMapping("/sendSMS")
	public String sendSMS(@RequestParam String username) {
		String result = "Could not sent SMS. User not found.";
		Optional<User> optionalUser = userService.findByUsername(username);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			if (Strings.isNotBlank(user.getToken())) {
				String messageSID = smsSeder.send(user.getPhoneNumber(), user.getToken());
				if (Strings.isNotBlank(messageSID)) {
					result = "SMS with token was sent to user " + username;
				} else {
					result = "Could not sent SMS. Consider phoneNumber is valid.";
				}
			} else {
				result = "Could not sent SMS. Token is not present.";
			}
		}
		return result;
	}

	@PutMapping("/grantAdmin")
	public String grantAdminAuthorities(@RequestParam String username) {
		userService.grantAdminAuthorities(username);
		return "ADMIN authorities was granted.";
	}

	@PutMapping("/removeAdmin")
	public String removeAdminAuthorities(@RequestParam String username) {
		userService.removeAdminAuthorities(username);
		return "ADMIN authorities was removed.";
	}

	@PutMapping("/disable")
	public String disableUser(@RequestParam String username) {
		userService.disableUserWithoutToken(username);
		return "User " + username + " was disabled.";
	}

	@PutMapping("/enable")
	public String enableUser(@RequestParam String username) {
		userService.enableUser(username);
		return "User " + username + " was enabled.";
	}

	@DeleteMapping("/remove")
	public String removeUser(@RequestParam String username) {
		userService.deleteByUsername(username);
		return "User " + username + " was removed.";
	}
}
