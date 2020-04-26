/*
 * Copyright (c) 2020. Roman Osadchuk.
 */

package com.osadchuk.worktimerserver.controller.rest;

import com.osadchuk.worktimerserver.service.TimeReportService;
import com.osadchuk.worktimerserver.util.FileSystemUtil;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

import static com.osadchuk.worktimerserver.util.WorkTimerConstants.DATE_FORMATTER;

@RestController
@RequestMapping("/timeReport/")
@Slf4j
public class TimeReportRestController {

	private final TimeReportService timeReportService;

	@Autowired
	public TimeReportRestController(TimeReportService timeReportService) {
		this.timeReportService = timeReportService;
	}

	@GetMapping("/global/download")
	public ResponseEntity<Resource> downloadGlobalReport(HttpServletRequest request,
	                                                     @RequestParam String startDate,
	                                                     @RequestParam String endDate) throws FileNotFoundException {
		LocalDate start = LocalDate.parse(startDate, DATE_FORMATTER);
		LocalDate end = LocalDate.parse(endDate, DATE_FORMATTER);
		Resource resource = timeReportService.createGlobalReport(start, end);
		return downloadResource(request, resource);
	}

	@GetMapping("/detail/download")
	public ResponseEntity<Resource> downloadGlobalReport(HttpServletRequest request,
	                                                     @RequestParam String username,
	                                                     @RequestParam String startDate,
	                                                     @RequestParam String endDate) throws FileNotFoundException {
		LocalDate start = LocalDate.parse(startDate, DATE_FORMATTER);
		LocalDate end = LocalDate.parse(endDate, DATE_FORMATTER);
		Resource resource = timeReportService.createDetailReport(username, start, end);
		return downloadResource(request, resource);
	}

	private ResponseEntity<Resource> downloadResource(HttpServletRequest request, Resource resource) {
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
		FileSystemUtil.deleteScreenshotsArchive(resource.getFilename(), 5);
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
}
