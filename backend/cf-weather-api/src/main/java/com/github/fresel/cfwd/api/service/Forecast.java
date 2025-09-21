package com.github.fresel.cfwd.api.service;

import java.util.List;
import lombok.Builder;
import lombok.Value;

/**
 * Represents a weather forecast for a specific location over multiple days.
 */
@Builder
@Value
public class Forecast {
  private String location;
  private List<ForecastDay> forecastDays;
}
