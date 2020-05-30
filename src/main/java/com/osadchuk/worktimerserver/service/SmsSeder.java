/*
 * Copyright (c) 2020. Roman Osadchuk.
 */

package com.osadchuk.worktimerserver.service;

import com.osadchuk.worktimerserver.model.ApplicationSettings;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SmsSeder {

	private final SettingsService settingsService;

	@Autowired
	public SmsSeder(SettingsService settingsService) {
		this.settingsService = settingsService;
	}

	public String send(String phoneNumber, String token) {
		ApplicationSettings applicationSettings = settingsService.getAsApplicationSettingsModel();
		Twilio.init(applicationSettings.getTwilioAccountSID(), applicationSettings.getTwilioAuthToken());
		Message message = Message.creator(
				new PhoneNumber(phoneNumber),
				new PhoneNumber(applicationSettings.getTwilioPhoneNumber()),
				"WorkTimer: " + token)
				.create();
		return message.getSid();
	}
}
