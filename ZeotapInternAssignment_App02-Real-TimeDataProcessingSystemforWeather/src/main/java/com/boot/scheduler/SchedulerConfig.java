package com.boot.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.boot.service.WeatherService;

@Component
public class SchedulerConfig {

    @Autowired
    private WeatherService weatherService;

    @Scheduled(fixedRate = 300000) // Every 5 minutes
    public void scheduledWeatherFetch() {
        String[] cities = {"Delhi", "Mumbai", "Chennai", "Bangalore", "Kolkata", "Hyderabad"};
        for (String city : cities) {
            weatherService.fetchAndSaveWeatherData(city);
        }
    }
}
