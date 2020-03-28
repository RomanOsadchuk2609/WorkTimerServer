/*
 * Copyright (c) 2020. Roman Osadchuk.
 */

package com.osadchuk.worktimerserver.controller.rest;

import com.osadchuk.worktimerserver.service.ScreenshotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/screenshot")
public class ScreenShotRestController {
	
	private final ScreenshotService screenshotService;
	
	@Autowired
	public ScreenShotRestController(ScreenshotService screenshotService) {
		this.screenshotService = screenshotService;
	}
	
	@GetMapping("/byId")
	public String getScreenshotById(@RequestParam long id) {
		return screenshotService.getBase64Screenshot(id);
	}
}
