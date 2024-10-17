package com.boot.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(length = 25)
	private String city;
	private double temperatureCelsius;
	private double feelsLikeCelsius;
	@Column(length = 25)
	private String weatherCondition;
	private LocalDateTime dateTime;

}
