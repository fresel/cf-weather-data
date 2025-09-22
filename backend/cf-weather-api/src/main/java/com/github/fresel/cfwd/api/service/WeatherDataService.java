package com.github.fresel.cfwd.api.service;

/**
 * Service interface for fetching weather data.
 */
public interface WeatherDataService {

  /**
   * Fetches the current weather for the specified latitude and longitude.
   *
   * @param lat Latitude of the location.
   * @param lon Longitude of the location.
   * @return CurrentWeather object containing current weather details.
   */
  CurrentWeather now(String lat, String lon);

  /**
   * Fetches the weather forecast for the specified latitude and longitude.
   *
   * @param lat Latitude of the location.
   * @param lon Longitude of the location.
   * @return Forecast object containing weather forecast details.
   */
  Forecast forecast(String lat, String lon);

}
