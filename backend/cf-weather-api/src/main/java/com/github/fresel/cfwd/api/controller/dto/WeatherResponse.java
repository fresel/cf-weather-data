package com.github.fresel.cfwd.api.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.Data;

/**
 * Weather response DTO.
 *
 * This hold a list of weather data for a number of days.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class WeatherResponse {

  private List<WeatherDto> weatherData;
}
