/*
 * Copyright (c) 2020. Roman Osadchuk.
 */

package com.osadchuk.worktimerserver.service;

import com.osadchuk.worktimerserver.entity.User;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PasswordRecoveryService {

	private final UserService userService;

	private final SmsSeder smsSeder;

	@Autowired
	public PasswordRecoveryService(SmsSeder smsSeder, UserService userService) {
		this.smsSeder = smsSeder;
		this.userService = userService;
	}

	public boolean recoverPassword(String username) {
		Optional<User> optionalUser = userService.disableUser(username);
		if (optionalUser.isPresent()) {
			String messageSID = smsSeder.send(optionalUser.get().getPhoneNumber(), optionalUser.get().getToken());
			return Strings.isNotBlank(messageSID);
		} else {
			return false;
		}
	}
}
