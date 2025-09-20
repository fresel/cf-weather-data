package com.github.fresel.cfwd.api.controller;

import com.github.fresel.cfwd.api.controller.dto.WeatherResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling weather-related requests.
 *
 */
@RestController
public class WeatherController {

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
    return ResponseEntity.ok(new WeatherResponse());
  }

}
