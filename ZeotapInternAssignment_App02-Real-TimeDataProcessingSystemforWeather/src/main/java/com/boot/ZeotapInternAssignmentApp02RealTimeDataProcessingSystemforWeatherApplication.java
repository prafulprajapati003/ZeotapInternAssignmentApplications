package com.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableScheduling
public class ZeotapInternAssignmentApp02RealTimeDataProcessingSystemforWeatherApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZeotapInternAssignmentApp02RealTimeDataProcessingSystemforWeatherApplication.class, args);
	}
	 @Bean
	    public RestTemplate restTemplate() {
	        return new RestTemplate();
	    }

}
