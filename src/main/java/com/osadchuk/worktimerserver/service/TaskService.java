package com.osadchuk.worktimerserver.service;

import com.osadchuk.worktimerserver.entity.Task;
import com.osadchuk.worktimerserver.model.dto.TaskDTO;
import com.osadchuk.worktimerserver.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
	public Optional<Task> findById(long id) {
		return taskRepository.findById(id);
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

	public Optional<Task> findByUsername(String username) {
		return taskRepository.findByUserUsername(username);
	}

	public List<Task> findAllByUsername(String username) {
		List<Task> tasks = taskRepository.findAllByUserUsername(username);
		tasks.addAll(taskRepository.findAllByUserIsNull());
		return tasks;
	}

	public List<TaskDTO> findAllAsDTOByUsername(String username) {
		return convertIntoDTO(findAllByUsername(username));
	}

	public TaskDTO convertIntoDTO(Task task) {
		return new TaskDTO(
				task.getId(),
				task.getName(),
				task.getDescription(),
				task.getUser() != null ? task.getUser().getId() : 0
		);
	}

	public List<TaskDTO> convertIntoDTO(List<Task> taskList) {
		return taskList.stream()
				.map(this::convertIntoDTO)
				.collect(Collectors.toList());
	}
}
