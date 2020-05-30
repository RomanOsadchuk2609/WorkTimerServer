/*
 * Copyright (c) 2020. Roman Osadchuk.
 */

package com.osadchuk.worktimerserver.model;

import lombok.Data;

/**
 * Model class for application settings
 */
@Data
public class ApplicationSettings {
	private int screenshotStorageTime;

	private String twilioAccountSID;

	private String twilioAuthToken;

	private String twilioPhoneNumber;
}
