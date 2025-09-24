package com.github.fresel.cfwd.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/** Main entry point for the Weather API application */
@SpringBootApplication
@ConfigurationPropertiesScan(basePackages = "com.github.fresel.cfwd.api.config")
public class CfWeatherApi {

	public static void main(String[] args) {
		SpringApplication.run(CfWeatherApi.class, args);
	}

}
