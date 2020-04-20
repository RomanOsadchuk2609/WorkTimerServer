package com.osadchuk.worktimerserver.service;

import com.osadchuk.worktimerserver.entity.Screenshot;
import com.osadchuk.worktimerserver.model.dto.ScreenshotDTO;
import com.osadchuk.worktimerserver.repository.ScreenshotRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.osadchuk.worktimerserver.util.WorkTimerConstants.DATE_TIME_FORMATTER;
import static com.osadchuk.worktimerserver.util.WorkTimerConstants.SCREENSHOT_FILE_DATE_TIME_FORMATTER;

/**
 * Service layer for operations with {@link Screenshot} entity
 */
@Service
@Slf4j
public class ScreenshotService implements CrudService<Screenshot>, DataTransferObjectService<Screenshot, ScreenshotDTO> {
	
	private final ScreenshotRepository screenshotRepository;
	
	@Autowired
	public ScreenshotService(ScreenshotRepository screenshotRepository) {
		this.screenshotRepository = screenshotRepository;
	}
	
	@Override
	public List<Screenshot> findAll() {
		return screenshotRepository.findAll();
	}

	@Override
	public Optional<Screenshot> findById(long id) {
		return screenshotRepository.findById(id);
	}

	@Override
	public Screenshot save(Screenshot entity) {
		return screenshotRepository.save(entity);
	}
	
	@Override
	public void delete(Screenshot entity) {
		screenshotRepository.delete(entity);
	}
	
	@Override
	public void deleteById(long id) {
		screenshotRepository.deleteById(id);
	}
	
	@Override
	public ScreenshotDTO convertIntoDTO(Screenshot model) {
		ScreenshotDTO screenshotDTO = new ScreenshotDTO();
		screenshotDTO.setId(model.getId());
		screenshotDTO.setDate(DATE_TIME_FORMATTER.format(model.getDate()));
		screenshotDTO.setTimeLogId(model.getTimeLog().getId());
		return screenshotDTO;
	}
	
	public List<Screenshot> findAllByUsernameAndBetweenDates(String username, LocalDateTime startTime, LocalDateTime endTime) {
		return screenshotRepository.findAllByUsernameAndBetweenDates(username, startTime, endTime);
	}
	
	public String getBase64Screenshot(long id) {
		return findById(id)
				.map(Screenshot::getBase64)
				.orElse(null);
	}
	
	public Resource zipScreenshots(String fileName, String username, LocalDate startDate, LocalDate endDate) throws IOException {
		LocalDateTime startDateTime = LocalDateTime.of(startDate, LocalTime.MIN);
		LocalDateTime endDateTime = LocalDateTime.of(endDate, LocalTime.MAX);
		List<Screenshot> screenshotList = findAllByUsernameAndBetweenDates(username, startDateTime, endDateTime);
		BASE64Decoder decoder = new BASE64Decoder();
		try (final ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(fileName))) {
			for (Screenshot screenshot : screenshotList) {
				if (!StringUtils.isEmpty(screenshot.getBase64())) {
					byte[] imageByte = decoder.decodeBuffer(screenshot.getBase64());
					try (ByteArrayInputStream bis = new ByteArrayInputStream(imageByte)) {
						BufferedImage bufferedImage = ImageIO.read(bis);
						ZipEntry imageZipOutput = new ZipEntry(
								username + "_" + screenshot.getDate().format(SCREENSHOT_FILE_DATE_TIME_FORMATTER) + ".png");
						zipOut.putNextEntry(imageZipOutput);
						ImageIO.write(bufferedImage, "PNG", zipOut);
					}
				}
			}
		}
		try {
			File file = new File(fileName);
			Resource resource = new UrlResource(file.toURI());
			if (resource.exists()) {
				return resource;
			} else {
				throw new FileNotFoundException("File not found " + fileName);
			}
		} catch (MalformedURLException ex) {
			throw new FileNotFoundException("File not found " + fileName);
		}
	}

	
}
