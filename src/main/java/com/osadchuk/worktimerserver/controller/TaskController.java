package com.osadchuk.worktimerserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
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
    public List<Object> tasks(@RequestParam String username) {
        return Collections.emptyList();
    }
}
