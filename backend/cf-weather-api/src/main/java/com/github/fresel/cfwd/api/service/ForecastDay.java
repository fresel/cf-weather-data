package com.github.fresel.cfwd.api.service;

import java.time.LocalDate;
import lombok.Data;

/**
 * Represents the weather forecast for a single day.
 */
@Data
public class ForecastDay {
  private LocalDate date;
  private double minTemperature;
  private double maxTemperature;
  private String main;
  private String description;
}
