/*
 * Copyright (c) 2020. Roman Osadchuk.
 */

package com.osadchuk.worktimerserver.controller.rest;

import com.osadchuk.worktimerserver.service.ScreenshotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDate;

import static com.osadchuk.worktimerserver.util.WorkTimerConstants.DATE_FORMATTER;

@RestController
@RequestMapping("/screenshot")
@Slf4j
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
	
	@GetMapping("/download")
	public ResponseEntity<Resource> downloadFile(HttpServletRequest request,
	                                             @RequestParam String username,
	                                             @RequestParam String startTime,
	                                             @RequestParam String endTime) throws IOException {
		// Load file as Resource
		String filename = username + "_screenshots_" + startTime + "_" + endTime + ".zip";
		LocalDate startDate = LocalDate.parse(startTime, DATE_FORMATTER);
		LocalDate endDate = LocalDate.parse(endTime, DATE_FORMATTER);
		
		Resource resource = screenshotService.zipScreenshots(filename, username, startDate, endDate);
		
		// Try to determine file's content type
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			log.info("Could not determine file type.");
		}
		
		// Fallback to the default content type if type could not be determined
		if (contentType == null) {
			contentType = "application/octet-stream";
		}
		screenshotService.deleteScreenshotsArchive(filename, 5);
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
}
