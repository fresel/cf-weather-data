package com.github.fresel.cfwd.api.service;

import java.util.List;
import lombok.Data;

/**
 * Represents a weather forecast for a specific location over multiple days.
 */
@Data
public class Forecast {
  private String location;
  private List<ForecastDay> forecastDays;
}
