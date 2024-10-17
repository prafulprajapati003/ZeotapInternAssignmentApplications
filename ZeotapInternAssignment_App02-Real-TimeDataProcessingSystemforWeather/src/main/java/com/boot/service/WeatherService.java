package com.boot.service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.boot.dto.WeatherSummary;
import com.boot.model.WeatherData;
import com.boot.repository.WeatherDataRepository;

@Service
public class WeatherService {

    @Autowired
    private WeatherDataRepository weatherDataRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${openweathermap.api.key}")
    private String apiKey;

    private String apiUrl = "https://api.openweathermap.org/data/2.5/weather?city={city}";

    public void fetchAndSaveWeatherData(String city) {
        String url = apiUrl.replace("{city}", city).replace("{apiKey}", apiKey);
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        saveWeatherData(city, response);
    }

    private void saveWeatherData(String city, Map<String, Object> response) {
        Map<String, Object> main = (Map<String, Object>) response.get("main");
        String weatherCondition = ((Map<String, Object>) ((List<?>) response.get("weather")).get(0)).get("main").toString();
        double tempKelvin = Double.parseDouble(main.get("temp").toString());
        double feelsLikeKelvin = Double.parseDouble(main.get("feels_like").toString());

        WeatherData weatherData = new WeatherData();
        weatherData.setCity(city);
        weatherData.setTemperatureCelsius(tempKelvin - 273.15);
        weatherData.setFeelsLikeCelsius(feelsLikeKelvin - 273.15);
        weatherData.setWeatherCondition(weatherCondition);
        weatherData.setDateTime(LocalDateTime.now());

        weatherDataRepository.save(weatherData);
    }

    public WeatherSummary getDailyWeatherSummary(String city) {
        LocalDateTime todayStart = LocalDate.now().atStartOfDay();
        LocalDateTime now = LocalDateTime.now();
        List<WeatherData> dailyData = weatherDataRepository.findByCityAndDateTimeBetween(city, todayStart, now);

        double avgTemp = dailyData.stream().mapToDouble(WeatherData::getTemperatureCelsius).average().orElse(0);
        double maxTemp = dailyData.stream().mapToDouble(WeatherData::getTemperatureCelsius).max().orElse(0);
        double minTemp = dailyData.stream().mapToDouble(WeatherData::getTemperatureCelsius).min().orElse(0);
        String dominantCondition = dailyData.stream()
                .collect(Collectors.groupingBy(WeatherData::getWeatherCondition, Collectors.counting()))
                .entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("Unknown");

        return new WeatherSummary(avgTemp, maxTemp, minTemp, dominantCondition);
    }
}
