package com.github.fresel.cfwd.api.core.weather.owmap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Response model for geocoding API responses from OpenWeatherMap.
 *
 * This class is currently a placeholder and can be expanded to include fields such as latitude,
 * longitude, name, country, state, etc., based on the actual response structure from the
 * OpenWeatherMap geocoding API.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class GeoCodingResponse {

  private String name;
  private String country;
  private String state;
  private double lat;
  private double lon;
}
