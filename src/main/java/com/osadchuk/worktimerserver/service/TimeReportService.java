/*
 * Copyright (c) 2020. Roman Osadchuk.
 */

package com.osadchuk.worktimerserver.service;

import com.osadchuk.worktimerserver.entity.User;
import com.osadchuk.worktimerserver.model.UserTime;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TableRowAlign;
import org.apache.poi.xwpf.usermodel.TableWidthType;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class TimeReportService {

	private final TimeLogService timeLogService;

	private final UserService userService;

	@Autowired
	public TimeReportService(TimeLogService timeLogService,
	                         UserService userService) {
		this.timeLogService = timeLogService;
		this.userService = userService;
	}

	public Resource createGlobalReport(LocalDate startDate, LocalDate endDate) throws FileNotFoundException {
		LocalDateTime startDateTime = LocalDateTime.of(startDate, LocalTime.MIN);
		LocalDateTime endDateTime = LocalDateTime.of(endDate, LocalTime.MAX);
		List<UserTime> usersTime = timeLogService.getUsersLoggedTime(startDateTime, endDateTime);
		usersTime.sort(Comparator.comparing(UserTime::getFullName));

		//Blank Document
		XWPFDocument document = new XWPFDocument();
		createHeader(document, String.format(("з %s по %s"), startDate, endDate));
		XWPFTable table = createTable(document, "№", "Прізвище та ім'я", "Ім'я користувача", "Відзвітований час");

		AtomicInteger number = new AtomicInteger(1);
		for (UserTime userTime : usersTime) {
			XWPFTableRow tableRow = table.createRow();
			tableRow.getCell(0).setText(String.valueOf(number.getAndIncrement()));
			tableRow.getCell(1).setText(userTime.getFullName());
			tableRow.getCell(2).setText(userTime.getUsername());
			tableRow.getCell(3).setText(userTime.getFormattedLoggedTime());
		}

		String fileName = "globalReport_" + startDate + "_" + endDate + ".docx";
		return saveReportFile(document, fileName);
	}

	public Resource createDetailReport(String username, LocalDate startDate, LocalDate endDate) throws FileNotFoundException {
		Optional<User> optionalUser = userService.findByUsername(username);
		if (optionalUser.isPresent()) {
			XWPFDocument document = new XWPFDocument();
			createHeader(document,
					String.format("Роботи користувача %s %s (%s)",
							optionalUser.get().getFirstName(),
							optionalUser.get().getLastName(),
							optionalUser.get().getUsername()),
					String.format(("з %s по %s"), startDate, endDate));

			XWPFTable table = createTable(document, "№", "Дата", "Відзвітований час");
			AtomicInteger number = new AtomicInteger(1);
			UserTime totalUserTime = new UserTime();
			while (!startDate.equals(endDate.plusDays(1))) {
				LocalDateTime startDateTime = LocalDateTime.of(startDate, LocalTime.MIN);
				LocalDateTime endDateTime = LocalDateTime.of(startDate, LocalTime.MAX);
				XWPFTableRow tableRow = table.createRow();
				tableRow.getCell(0).setText(String.valueOf(number.getAndIncrement()));
				tableRow.getCell(1).setText(startDate.toString());
				UserTime userTime = timeLogService.getUserLoggedTime(username, startDateTime, endDateTime);
				if (userTime != null) {
					tableRow.getCell(2).setText(userTime.getFormattedLoggedTime());
					totalUserTime.setLoggedSeconds(totalUserTime.getLoggedSeconds() + userTime.getLoggedSeconds());
				}/* else {
					tableRow.getCell(2).setText("-");
				}*/
				startDate = startDate.plusDays(1);
			}
			XWPFTableRow totalInfoRow = table.createRow();
			totalInfoRow.getCell(0).setText(String.valueOf(number.getAndIncrement()));
			totalInfoRow.getCell(1).setText("Всього");
			totalInfoRow.getCell(2).setText(totalUserTime.getFormattedLoggedTime());

			String fileName = "detailReport_" + username + "_" + startDate + "_" + endDate + ".docx";
			return saveReportFile(document, fileName);
		} else {
			log.error("User {} not found.", username);
			return null;
		}
	}

	private XWPFParagraph createHeader(XWPFDocument document, String... headerLines) {
		XWPFParagraph paragraph = document.createParagraph();
		paragraph.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun run = paragraph.createRun();
		run.setBold(true);
		run.setFontSize(14);
		run.setText("Звіт");
		run.addBreak();
		run.setBold(false);
		Arrays.stream(headerLines).forEach(line -> {
			run.setText(line);
			run.addBreak();
		});
		return paragraph;
	}

	private XWPFTable createTable(XWPFDocument document, String... columns) {
		XWPFTable table = document.createTable();
		table.setTableAlignment(TableRowAlign.LEFT);
		table.setWidthType(TableWidthType.PCT);
		table.setWidth("100%");
		XWPFTableRow tableHeader = table.getRow(0);
		AtomicInteger columnIndex = new AtomicInteger(0);
		Arrays.stream(columns).forEach(column -> {
			if (columnIndex.get() == 0) {
				tableHeader.getCell(columnIndex.getAndIncrement()).setText(column);
			} else {
				tableHeader.addNewTableCell().setText(column);
			}
		});
		return table;
	}

	private Resource saveReportFile(XWPFDocument document, String fileName) throws FileNotFoundException {
		try (FileOutputStream out = new FileOutputStream(new File(fileName))) {
			document.write(out);
			File file = new File(fileName);
			Resource resource = new UrlResource(file.toURI());
			if (resource.exists()) {
				return resource;
			} else {
				throw new FileNotFoundException("File not found " + fileName);
			}
		} catch (IOException ex) {
			throw new FileNotFoundException("File not found " + fileName);
		}
	}
}
