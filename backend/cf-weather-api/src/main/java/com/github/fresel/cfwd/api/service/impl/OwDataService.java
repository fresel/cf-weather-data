package com.github.fresel.cfwd.api.service.impl;

import com.github.fresel.cfwd.api.core.weather.owmap.ForecastResponse;
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
 * Implementation of WeatherDataService that fetches weather data from OpenWeatherMap.
 */
@RequiredArgsConstructor
@Slf4j
public class OwDataService implements WeatherDataService {

  private final WeatherClient weatherClient;

  @Override
  public CurrentWeather now(String lat, String lon) {
    log.info("OwDatatService.now called with lat={}, lon={}", lat, lon);
    String locationName = getLocationName(lat, lon);
    log.debug("Resolved location name: {}", locationName);
    WeatherResponse response = weatherClient.getCurrentWeather(locationName);
    response.getWeather().stream().findFirst().ifPresent(weather -> {
      log.debug("Current weather: main={}, description={}", weather.getMain(),
          weather.getDescription());
    });

    return mapToCurrentWeather(response, locationName);
  }

  @Override
  public Forecast forecast(String lat, String lon) {
    log.info("OwDatatService.forecast called with lat={}, lon={}", lat, lon);
    String locationName = getLocationName(lat, lon);
    log.debug("Resolved location name: {}", locationName);
    ForecastResponse response = weatherClient.getWeatherForecast(locationName);
    return mapToForecast(response, locationName);
  }

  private String getLocationName(String lat, String lon) {
    // TODO: Fix new errror type
    try {
      return weatherClient.getReversedGeoCoding(Double.parseDouble(lat), Double.parseDouble(lon))
          .getName();
    } catch (NumberFormatException e) {
      log.warn("Invalid latitude or longitude format: lat={}, lon={}", lat, lon, e);
      return null;
    } catch (Exception e) {
      log.error("Error during reverse geocoding for lat={}, lon={}", lat, lon, e);
      return null;
    }
  }

  private static CurrentWeather mapToCurrentWeather(WeatherResponse response, String locationName) {
    // if weather list is empty, throw exception or handle accordingly
    if (response.getWeather().isEmpty()) {
      throw new IllegalArgumentException("Weather information is not available");
    }
    return CurrentWeather.builder().dateTime(convertUnixToLocalDateTime(response.getDt()))
        .location(locationName).temperature(response.getMain().getTemp())
        .main(response.getWeather().isEmpty() ? null : response.getWeather().get(0).getMain())
        .description(
            response.getWeather().isEmpty() ? null : response.getWeather().get(0).getDescription())
        .build();
  }

  private static Forecast mapToForecast(ForecastResponse response, String locationName) {
    // if weather list is empty, throw exception or handle accordingly
    if (response.getList().isEmpty()) {
      throw new WeatherDataServiceException("Weather information is not available");
    }
    // map response days to ForecastDays
    List<ForecastDay> forecastDays = response.getList().stream()
        .map(day -> ForecastDay.builder().dateTime(convertUnixToLocalDateTime(day.getDt()))
            .minTemperature(day.getMain().getTempMin()).maxTemperature(day.getMain().getTempMax())
            .main(day.getWeather().isEmpty() ? null : day.getWeather().get(0).getMain())
            .description(
                day.getWeather().isEmpty() ? null : day.getWeather().get(0).getDescription())
            .build())
        .toList();
    return Forecast.builder().location(locationName).forecastDays(forecastDays).build();
  }

  private static LocalDateTime convertUnixToLocalDateTime(long unixTimestamp) {
    return LocalDateTime.ofEpochSecond(unixTimestamp, 0, java.time.ZoneOffset.UTC);
  }
}
