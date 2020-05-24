/*
 * Copyright (c) 2020. Roman Osadchuk.
 */

package com.osadchuk.worktimerserver.service.scheduled;

import com.osadchuk.worktimerserver.service.ScreenshotService;
import com.osadchuk.worktimerserver.service.SettingsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Slf4j
@Service
public class ScreenshotScheduledCleanerService {

	private static final long SCREENSHOTS_REVIEW_INTERVAL = 24 * 60 * 60 * 1000;//1 day

	private final SettingsService settingsService;

	private final ScreenshotService screenshotService;

	@Autowired
	public ScreenshotScheduledCleanerService(SettingsService settingsService,
	                                         ScreenshotService screenshotService) {
		this.settingsService = settingsService;
		this.screenshotService = screenshotService;
	}

	@Async
	@Scheduled(fixedRate = SCREENSHOTS_REVIEW_INTERVAL)
	public void removeOldScreenshots() {
		int maxStorageDays = settingsService.getAsApplicationSettingsModel().getScreenshotStorageTime();
		log.debug("Started deleting screenshots created more then {} days ago.", maxStorageDays);
		screenshotService.findAll()
				.stream()
				.filter(screenshot -> Duration.between(screenshot. getDate(), LocalDateTime.now()).toDays() >= maxStorageDays)
				.forEach(screenshotService::delete);
		log.debug("Finished deleting screenshots created more then {} days ago.", maxStorageDays);
	}

}
