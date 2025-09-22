package com.github.fresel.cfwd.api.service;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;

/**
 * Represents the weather forecast for a single day.
 */
@Builder
@Value
public class ForecastDay {
  private LocalDateTime dateTime;
  private double minTemperature;
  private double maxTemperature;
  private String main;
  private String description;
}
