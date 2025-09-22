package com.github.fresel.cfwd.api.exception;

/** Exception thrown when there is an error in the weather service layer. */
public class WeatherDataServiceException extends RuntimeException {

  public WeatherDataServiceException(String message) {
    super(message);
  }

  public WeatherDataServiceException(String message, Throwable cause) {
    super(message, cause);
  }

}
