package com.osadchuk.worktimerserver.service;

import com.osadchuk.worktimerserver.entity.Screenshot;
import com.osadchuk.worktimerserver.model.dto.ScreenshotDTO;
import com.osadchuk.worktimerserver.repository.ScreenshotRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.osadchuk.worktimerserver.util.WorkTimerConstants.DATE_TIME_FORMATTER;

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
}
