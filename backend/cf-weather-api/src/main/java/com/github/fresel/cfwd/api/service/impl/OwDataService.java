package com.github.fresel.cfwd.api.service.impl;

import com.github.fresel.cfwd.api.core.weather.owmap.ForecastResponse;
import com.github.fresel.cfwd.api.core.weather.owmap.GeoCodingResponse;
import com.github.fresel.cfwd.api.core.weather.owmap.WeatherClient;
import com.github.fresel.cfwd.api.core.weather.owmap.WeatherResponse;
import com.github.fresel.cfwd.api.exception.WeatherDataServiceException;
import com.github.fresel.cfwd.api.service.CurrentWeather;
import com.github.fresel.cfwd.api.service.Forecast;
import com.github.fresel.cfwd.api.service.ForecastDay;
import com.github.fresel.cfwd.api.service.WeatherDataService;

import java.time.LocalDateTime;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of WeatherDataService that fetches weather data from
 * OpenWeatherMap.
 */
@RequiredArgsConstructor
@Slf4j
public class OwDataService implements WeatherDataService {

  private final WeatherClient weatherClient;

  @Override
  public CurrentWeather now(Double lat, Double lon) {
    log.debug("OwDataService.now called with lat={}, lon={}", lat, lon);
    String locationName = resolveLocationName(lat, lon);
    log.debug("Resolved location name: {}", locationName);
    WeatherResponse response = weatherClient.getCurrentWeather(locationName);
    return mapToCurrentWeather(response, locationName);
  }

  @Override
  public Forecast forecast(Double lat, Double lon) {
    log.debug("OwDataService.forecast called with lat={}, lon={}", lat, lon);
    String locationName = resolveLocationName(lat, lon);
    log.debug("Resolved location name: {}", locationName);
    ForecastResponse response = weatherClient.getWeatherForecast(locationName);
    return mapToForecast(response, locationName);
  }

  // Resolve a human-readable location name from latitude and longitude.
  private String resolveLocationName(Double lat, Double lon) {
    GeoCodingResponse response = weatherClient.getReversedGeoCoding(lat, lon);
    return response.getName();
  }

  // Map WeatherResponse to CurrentWeather
  private static CurrentWeather mapToCurrentWeather(WeatherResponse response, String locationName) {
    if (response.getWeather() == null || response.getWeather().isEmpty()) {
      throw new WeatherDataServiceException(
          "Weather information is not available for location: " + locationName);
    }
    var weather = response.getWeather().get(0);
    return CurrentWeather.builder()
        .dateTime(convertUnixToLocalDateTime(response.getDt()))
        .location(locationName)
        .temperature(response.getMain().getTemp())
        .main(weather.getMain())
        .description(weather.getDescription()).build();
  }

  // Map ForecastResponse to Forecast
  private static Forecast mapToForecast(ForecastResponse response, String locationName) {
    if (response.getList() == null || response.getList().isEmpty()) {
      throw new WeatherDataServiceException(
          "Weather forecast information is not available for location: " + locationName);
    }
    List<ForecastDay> forecastDays = response.getList().stream().map(day -> {
      var hasWeather = day.getWeather() != null && !day.getWeather().isEmpty();
      var weather = hasWeather ? day.getWeather().get(0) : null;

      return ForecastDay.builder()
          .dateTime(convertUnixToLocalDateTime(day.getDt()))
          .minTemperature(day.getMain().getTempMin())
          .maxTemperature(day.getMain().getTempMax())
          .main(weather != null ? weather.getMain() : null)
          .description(weather != null ? weather.getDescription() : null)
          .build();
    }).toList();
    return Forecast.builder().location(locationName).forecastDays(forecastDays).build();
  }

  private static LocalDateTime convertUnixToLocalDateTime(long unixTimestamp) {
    return LocalDateTime.ofEpochSecond(unixTimestamp, 0, java.time.ZoneOffset.UTC);
  }

}
