package com.boot.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.boot.model.WeatherData;

@Repository
public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {
	List<WeatherData> findByCityAndDateTimeBetween(String city, LocalDateTime start, LocalDateTime end);
}
