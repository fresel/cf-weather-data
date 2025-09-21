package com.github.fresel.cfwd.api.controller;

import com.github.fresel.cfwd.api.controller.dto.WeatherDto;
import com.github.fresel.cfwd.api.controller.dto.WeatherResponse;
import com.github.fresel.cfwd.api.service.Forecast;
import com.github.fresel.cfwd.api.service.WeatherDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller for handling weather-related requests.
 *
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class WeatherController {

  private final WeatherDataService weatherDataService;

  /**
   * Endpoint for retrieving weather data.
   *
   * The endpoint accepts latitude and longitude as query parameters, along with an optional type
   * parameter to specify the type of weather data requested (e.g., current, forecast).
   *
   * @param lat latitude
   * @param lon longitude
   * @param type type of weather data
   * @return weather forecast data as json
   */
  @GetMapping(path = "/weather", produces = "application/json")
  public ResponseEntity<WeatherResponse> getWeather(@RequestParam String lat,
      @RequestParam String lon, @RequestParam(defaultValue = "current") String type) {
    // Call the weather API using the location parameter
    switch (type.toLowerCase()) {
      case "current":
        return ResponseEntity.ok(mapToWeatherResponseFromCurrent());
      case "forecast":
        return ResponseEntity.ok(mapToWeatherResponseFromForecast());
      default:
        return ResponseEntity.badRequest().build();
    }
  }

  private WeatherResponse mapToWeatherResponseFromCurrent() {
    var now = weatherDataService.now("0", "0");
    log.debug("Mapping CurrentWeather to WeatherResponse: {}", now);
    WeatherDto dto = new WeatherDto();
    dto.setTemperature(now.getTemperature());
    dto.setDescription(now.getDescription());
    dto.setWeather(now.getMain());
    WeatherResponse response = new WeatherResponse();
    response.setWeatherData(java.util.List.of(dto));
    return response;
  }

  private WeatherResponse mapToWeatherResponseFromForecast() {
    Forecast forecast = weatherDataService.forecast("0", "0");
    log.debug("Mapping Forecast to WeatherResponse: {}", forecast);
    WeatherDto dto = new WeatherDto();
    forecast.getForecastDays().stream().findFirst().ifPresent(day -> {
      dto.setTemperature(day.getMaxTemperature());
      dto.setDescription(day.getDescription());
      dto.setWeather(day.getMain());
    });
    // Map other fields as needed
    WeatherResponse response = new WeatherResponse();
    response.setWeatherData(java.util.List.of(dto));
    return response;
  }
}
