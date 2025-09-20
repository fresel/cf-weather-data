package com.github.fresel.cfwd.api.core.weather.owmap;

/**
 * Exception thrown when there is an error in the WeatherClient.
 */
public class WeatherClientException extends RuntimeException {

  public WeatherClientException(String message) {
    super(message);
  }

  public WeatherClientException(String message, Throwable cause) {
    super(message, cause);
  }

}
