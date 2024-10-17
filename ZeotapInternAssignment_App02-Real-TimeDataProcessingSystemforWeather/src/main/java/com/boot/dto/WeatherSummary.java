package com.boot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherSummary {

	private double averageTemp;
	private double maxTemp;
	private double minTemp;
	private String dominantWeatherCondition;

}
