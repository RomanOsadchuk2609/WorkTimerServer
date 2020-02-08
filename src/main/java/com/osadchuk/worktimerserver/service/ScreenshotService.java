package com.osadchuk.worktimerserver.service;

import com.osadchuk.worktimerserver.entity.Screenshot;
import com.osadchuk.worktimerserver.repository.ScreenshotRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for operations with {@link Screenshot} entity
 */
@Service
@Slf4j
public class ScreenshotService implements CrudService<Screenshot> {
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
}
