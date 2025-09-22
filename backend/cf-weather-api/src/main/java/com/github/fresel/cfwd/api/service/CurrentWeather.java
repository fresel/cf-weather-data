package com.github.fresel.cfwd.api.service;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;

/**
 * Represents the current weather conditions for a specific location.
 *
 * It includes details such as timestamp, temperature, main weather condition, and a description.
 */
@Builder
@Value
public class CurrentWeather {
  private LocalDateTime dateTime;
  private String location;
  private double temperature;
  private String main;
  private String description;

}
