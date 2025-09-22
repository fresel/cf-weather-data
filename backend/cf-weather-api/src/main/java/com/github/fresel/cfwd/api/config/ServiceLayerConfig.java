package com.github.fresel.cfwd.api.config;

import com.github.fresel.cfwd.api.core.weather.owmap.WeatherClient;
import com.github.fresel.cfwd.api.service.WeatherDataService;
import com.github.fresel.cfwd.api.service.impl.OwDataService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for the service layer.
 *
 * This class can be used to define beans and other configurations related to the service layer.
 *
 */
@Configuration
public class ServiceLayerConfig {

  @Bean
  WeatherClient weatherClient(ApiProperties apiProperties) {
    return WeatherClient.builder().apiKey(apiProperties.getApiKey()).build();
  }

  @Bean
  WeatherDataService weatherDataService(WeatherClient weatherClient) {
    return new OwDataService(weatherClient);
  }
}
