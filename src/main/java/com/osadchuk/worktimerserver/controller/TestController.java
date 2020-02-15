package com.osadchuk.worktimerserver.controller;

import com.osadchuk.worktimerserver.service.ScreenshotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
	private final ScreenshotService screenshotService;

	@Autowired
	public TestController(ScreenshotService screenshotService) {
		this.screenshotService = screenshotService;
	}

	@GetMapping
	public String test() {
		return "It works";
	}

	@GetMapping("/security")
	public String test2() {
		return "Security works!";
	}

	@GetMapping("/screenshot")
	public String screenshot() {
		return screenshotService.findAll().get(0).getBase64();
	}
}
