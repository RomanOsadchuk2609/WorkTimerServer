/*
 * Copyright (c) 2020. Roman Osadchuk.
 */

package com.osadchuk.worktimerserver.service;

import com.osadchuk.worktimerserver.entity.Settings;
import com.osadchuk.worktimerserver.model.ApplicationSettings;
import com.osadchuk.worktimerserver.repository.SettingsRepository;
import com.osadchuk.worktimerserver.util.WorkTimerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SettingsService implements CrudService<Settings> {

	private final SettingsRepository settingsRepository;

	@Autowired
	public SettingsService(SettingsRepository settingsRepository) {
		this.settingsRepository = settingsRepository;
	}

	@Override
	public List<Settings> findAll() {
		return settingsRepository.findAll();
	}

	@Override
	public Optional<Settings> findById(long id) {
		return settingsRepository.findById(id);
	}

	@Override
	public Settings save(Settings entity) {
		return settingsRepository.save(entity);
	}

	@Override
	public void delete(Settings entity) {
		settingsRepository.delete(entity);
	}

	@Override
	public void deleteById(long id) {
		settingsRepository.deleteById(id);
	}

	public String findValueByName(String name) {
		Optional<Settings> setting = settingsRepository.findByName(name);
		if (setting.isPresent()) {
			return setting.get().getValue();
		} else {
			return "";
		}
	}

	public Settings saveValueByName(String name, String value) {
		Optional<Settings> optionalSettings = settingsRepository.findByName(name);
		Settings settings;
		if (optionalSettings.isPresent()) {
			settings = optionalSettings.get();
		} else {
			settings = new Settings();
			settings.setName(name);
		}
		settings.setValue(value);
		return settingsRepository.save(settings);
	}

	public ApplicationSettings getAsApplicationSettingsModel() {
		ApplicationSettings applicationSettings = new ApplicationSettings();
		applicationSettings.setScreenshotStorageTime(Integer.parseInt(findValueByName(WorkTimerConstants.ApplicationSettings.SCREENSHOT_STORAGE_TIME)));
		applicationSettings.setTwilioAccountSID(findValueByName(WorkTimerConstants.ApplicationSettings.TWILIO_ACCOUNT_SID));
		applicationSettings.setTwilioAuthToken(findValueByName(WorkTimerConstants.ApplicationSettings.TWILIO_AUTH_TOKEN));
		applicationSettings.setTwilioPhoneNumber(findValueByName(WorkTimerConstants.ApplicationSettings.TWILIO_PHONE_NUMBER));
		return applicationSettings;
	}

	public ApplicationSettings save(ApplicationSettings applicationSettings) {
		saveValueByName(WorkTimerConstants.ApplicationSettings.SCREENSHOT_STORAGE_TIME, String.valueOf(applicationSettings.getScreenshotStorageTime()));
		saveValueByName(WorkTimerConstants.ApplicationSettings.TWILIO_ACCOUNT_SID, applicationSettings.getTwilioAccountSID());
		saveValueByName(WorkTimerConstants.ApplicationSettings.TWILIO_AUTH_TOKEN, applicationSettings.getTwilioAuthToken());
		saveValueByName(WorkTimerConstants.ApplicationSettings.TWILIO_PHONE_NUMBER, applicationSettings.getTwilioPhoneNumber());
		return getAsApplicationSettingsModel();
	}
}
