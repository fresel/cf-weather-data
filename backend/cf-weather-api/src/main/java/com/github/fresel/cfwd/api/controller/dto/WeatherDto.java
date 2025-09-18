package com.github.fresel.cfwd.api.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Data Transfer Object for weather data.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class WeatherDto {
  private String location;
  private String longitude;
  private String latitude;
  private String weather;
  private String description;
  private String temperature;
}
