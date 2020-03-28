package com.osadchuk.worktimerserver.controller.rest;

import com.osadchuk.worktimerserver.model.SimpleTask;
import com.osadchuk.worktimerserver.model.dto.TaskDTO;
import com.osadchuk.worktimerserver.service.TaskService;
import com.osadchuk.worktimerserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/simple_tasks")
public class TaskRestController {
	private final TaskService taskService;

	private final UserService userService;

	@Autowired
	public TaskRestController(TaskService taskService, UserService userService) {
		this.taskService = taskService;
		this.userService = userService;
	}

	@PostMapping("/by_username")
	public List<SimpleTask> tasks(@RequestParam String username) {
		return Collections.singletonList(new SimpleTask(
				1,
				"project1",
				1,
				"task1",
				false,
				0,
				1,
				"admin"
		));
	}

	@GetMapping("/by_username")
	public List<TaskDTO> getTasks(@RequestParam String username) {
		return taskService.findAllAsDTOByUsername(username);
	}
}
