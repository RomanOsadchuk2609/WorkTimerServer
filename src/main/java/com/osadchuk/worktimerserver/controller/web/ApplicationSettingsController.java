/*
 * Copyright (c) 2020. Roman Osadchuk.
 */

package com.osadchuk.worktimerserver.controller.web;

import com.osadchuk.worktimerserver.model.ApplicationSettings;
import com.osadchuk.worktimerserver.service.SettingsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/settings")
public class ApplicationSettingsController {

	private final SettingsService settingsService;

	public ApplicationSettingsController(SettingsService settingsService) {
		this.settingsService = settingsService;
	}

	@GetMapping
	public String getSettings(Model model) {
		model.addAttribute("settings", settingsService.getAsApplicationSettingsModel());
		return "settings";
	}

	@PostMapping
	public String saveSettings(@ModelAttribute("settings") @Valid ApplicationSettings settings, Model model) {
		ApplicationSettings newApplicationSettings = settingsService.save(settings);
		model.addAttribute("settings", newApplicationSettings);
		return "settings";
	}
}
