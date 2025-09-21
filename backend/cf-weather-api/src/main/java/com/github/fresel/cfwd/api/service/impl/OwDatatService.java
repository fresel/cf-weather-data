package com.github.fresel.cfwd.api.service.impl;

import com.github.fresel.cfwd.api.core.weather.owmap.ForecastResponse;
import com.github.fresel.cfwd.api.core.weather.owmap.WeatherClient;
import com.github.fresel.cfwd.api.core.weather.owmap.WeatherResponse;
import com.github.fresel.cfwd.api.service.CurrentWeather;
import com.github.fresel.cfwd.api.service.Forecast;
import com.github.fresel.cfwd.api.service.ForecastDay;
import com.github.fresel.cfwd.api.service.WeatherDataService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class OwDatatService implements WeatherDataService {

  private final WeatherClient weatherClient;

  @Override
  public CurrentWeather now(String lat, String lon) {
    log.info("OwDatatService.now called with lat={}, lon={}", lat, lon);
    WeatherResponse response = weatherClient.getCurrentWeather(lat, lon);
    response.getWeather().stream().findFirst().ifPresent(weather -> {
      log.debug("Current weather: main={}, description={}", weather.getMain(),
          weather.getDescription());
    });

    return mapToCurrentWeather(response);
  }

  @Override
  public Forecast forecast(String lat, String lon) {
    log.info("OwDatatService.forecast called with lat={}, lon={}", lat, lon);
    ForecastResponse response = weatherClient.getWeatherForecast(lat, lon);
    return mapToForecast(response);
  }

  private static CurrentWeather mapToCurrentWeather(WeatherResponse response) {
    // if weather list is empty, throw exception or handle accordingly
    if (response.getWeather().isEmpty()) {
      throw new IllegalArgumentException("Weather information is not available");
    }
    return CurrentWeather.builder().timestamp(null) // TODO: map timestamp from response
        .location(null) // TODO: map location name if available
        .temperature(response.getMain().getTemp())
        .main(response.getWeather().isEmpty() ? null : response.getWeather().get(0).getMain())
        .description(
            response.getWeather().isEmpty() ? null : response.getWeather().get(0).getDescription())
        .build();
  }

  private static Forecast mapToForecast(ForecastResponse response) {
    // if weather list is empty, throw exception or handle accordingly
    if (response.getDays().isEmpty()) {
      throw new IllegalArgumentException("Weather information is not available");
    }
    // map response days to ForecastDays
    List<ForecastDay> forecastDays = response.getDays().stream()
        .map(day -> ForecastDay.builder().date(null) // TODO: map date from day
            .minTemperature(day.getMain().getTempMin()).maxTemperature(day.getMain().getTempMax())
            .main(day.getWeather().isEmpty() ? null : day.getWeather().get(0).getMain())
            .description(
                day.getWeather().isEmpty() ? null : day.getWeather().get(0).getDescription())
            .build())
        .toList();
    return Forecast.builder().forecastDays(forecastDays).build();
  }
}
