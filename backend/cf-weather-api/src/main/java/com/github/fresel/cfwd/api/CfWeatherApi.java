package com.github.fresel.cfwd.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** Main entry point for the Weather API application */
@SpringBootApplication
public class CfWeatherApi {

	public static void main(String[] args) {
		SpringApplication.run(CfWeatherApi.class, args);
	}

}
