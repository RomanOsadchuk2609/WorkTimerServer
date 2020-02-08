package com.osadchuk.worktimerserver.controller;

import com.osadchuk.worktimerserver.model.SimpleTask;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/simple_tasks/by_username")
public class TaskController {

	@PostMapping
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
}
