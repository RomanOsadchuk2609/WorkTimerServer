/*
 * Copyright (c) 2020. Roman Osadchuk.
 */

package com.osadchuk.worktimerserver.service;

import com.osadchuk.worktimerserver.model.UserTime;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TableRowAlign;
import org.apache.poi.xwpf.usermodel.TableWidthType;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
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
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TimeReportService {

	private final TimeLogService timeLogService;

	@Autowired
	public TimeReportService(TimeLogService timeLogService) {
		this.timeLogService = timeLogService;
	}

	public Resource createGlobalReport(LocalDate startDate, LocalDate endDate) throws FileNotFoundException {
		LocalDateTime startDateTime = LocalDateTime.of(startDate, LocalTime.MIN);
		LocalDateTime endDateTime = LocalDateTime.of(endDate, LocalTime.MAX);
		List<UserTime> usersTime = timeLogService.getUsersLoggedTime(startDateTime, endDateTime);
		usersTime.sort(Comparator.comparing(UserTime::getFullName));

		//Blank Document
		XWPFDocument document = new XWPFDocument();

		//Create a blank spreadsheet
		XWPFParagraph paragraph = document.createParagraph();
		paragraph.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun run = paragraph.createRun();
		run.setBold(true);
		run.setFontSize(14);
		run.setText("Звіт");
		run.addBreak();
		run.setBold(false);
		run.setText("з " + startDate + " по " + endDate);
		run.addBreak();

		//create table
		XWPFTable table = document.createTable();
		table.setTableAlignment(TableRowAlign.LEFT);
		table.setWidthType(TableWidthType.PCT);
		table.setWidth("100%");
		XWPFTableRow tableHeader = table.getRow(0);
		tableHeader.getCell(0).setText("№");
		tableHeader.getCell(0).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
		tableHeader.addNewTableCell().setText("ПІБ");
		tableHeader.getCell(0).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
		tableHeader.addNewTableCell().setText("Ім'я користувача");
		tableHeader.getCell(0).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
		tableHeader.addNewTableCell().setText("Час");
		tableHeader.getCell(0).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);

		AtomicInteger number = new AtomicInteger(1);
		for (UserTime userTime : usersTime) {
			XWPFTableRow tableRow = table.createRow();
			tableRow.getCell(0).setText(String.valueOf(number.getAndIncrement()));
			tableRow.getCell(1).setText(userTime.getFullName());
			tableRow.getCell(2).setText(userTime.getUsername());
			tableRow.getCell(3).setText(userTime.getFormattedLoggedTime());
		}

		String fileName = "globalReport_" + startDate + "_" + endDate + ".docx";
		try (FileOutputStream out = new FileOutputStream(new File(fileName))) {
			document.write(out);
			File file = new File(fileName);
			Resource resource = null;
			resource = new UrlResource(file.toURI());
			if (resource.exists()) {
				return resource;
			} else {
				throw new FileNotFoundException("File not found " + fileName);
			}
		} catch (IOException ex) {
			throw new FileNotFoundException("File not found " + fileName);
		}

		/*try {
			File file2 = new File(fileName);
			Resource resource = null;
			resource = new UrlResource(file2.toURI());
			if (resource.exists()) {
				return resource;
			} else {
				throw new FileNotFoundException("File not found " + fileName);
			}
		} catch (MalformedURLException e) {
			throw new FileNotFoundException("File not found " + fileName);
		}*/
	}
}
