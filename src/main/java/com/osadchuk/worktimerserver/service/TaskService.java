package com.osadchuk.worktimerserver.service;

import com.osadchuk.worktimerserver.entity.Task;
import com.osadchuk.worktimerserver.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for operations with {@link Task} entity
 */
@Service
@Slf4j
public class TaskService implements CrudService<Task> {
	private final TaskRepository taskRepository;

	@Autowired
	public TaskService(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}

	@Override
	public List<Task> findAll() {
		return taskRepository.findAll();
	}

	@Override
	public Task findById(long id) {
		return taskRepository.findById(id).orElse(null);
	}

	@Override
	public Task save(Task entity) {
		return taskRepository.save(entity);
	}

	@Override
	public void delete(Task entity) {
		taskRepository.delete(entity);
	}

	@Override
	public void deleteById(long id) {
		taskRepository.deleteById(id);
	}
}
