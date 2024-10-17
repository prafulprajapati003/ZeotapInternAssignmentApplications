package com.boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.boot.dto.WeatherSummary;
import com.boot.service.WeatherService;

@Controller
public class WeatherController {

	@Autowired
	private WeatherService weatherService;

	@GetMapping("/")
	public String homePage() {
		return "index";
	}

	@GetMapping("/weather-summary")
	public String getWeatherSummary(@RequestParam String city, Model model) {
		WeatherSummary weatherSummary = weatherService.getDailyWeatherSummary(city);
		model.addAttribute("city", city);
		model.addAttribute("weatherSummary", weatherSummary);
		return "summary";
	}

	@GetMapping("/fetch-weather")
	public String fetchWeatherData(@RequestParam String city, Model model) {
		weatherService.fetchAndSaveWeatherData(city);
		return "redirect:/weather-summary?city=" + city;
	}
}
