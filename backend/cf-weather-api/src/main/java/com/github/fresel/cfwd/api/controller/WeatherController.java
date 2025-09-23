package com.github.fresel.cfwd.api.controller;

import com.github.fresel.cfwd.api.exception.InvalidRequestDataException;
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
  public ResponseEntity<?> getWeather(@RequestParam String lat, @RequestParam String lon,
      @RequestParam(defaultValue = "current") String type) {
    validateCoordinates(lat, lon);
    switch (type.toLowerCase()) {
      case "current":
        return ResponseEntity.ok(weatherDataService.now(lat, lon));
      case "forecast":
        return ResponseEntity.ok(weatherDataService.forecast(lat, lon));
      default:
        throw new InvalidRequestDataException(
            "'%s' is not a valid type. Use 'current' or 'forecast'.".formatted(type));
    }
  }

  private void validateCoordinates(String lat, String lon) {
    try {
      double latitude = Double.parseDouble(lat);
      double longitude = Double.parseDouble(lon);
      if (latitude < -90 || latitude > 90 || longitude < -180 || longitude > 180) {
        throw new InvalidRequestDataException(
            "Latitude must be between -90 and 90 and longitude between -180 and 180.");
      }
    } catch (NumberFormatException e) {
      throw new InvalidRequestDataException("Latitude and longitude must be valid numbers.", e);
    }
  }

}
