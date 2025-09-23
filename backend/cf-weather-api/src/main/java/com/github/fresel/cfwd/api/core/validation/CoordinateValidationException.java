package com.github.fresel.cfwd.api.core.validation;

/** Exception thrown when coordinate validation fails. */
public class CoordinateValidationException extends RuntimeException {

  public CoordinateValidationException(String message) {
    super(message);
  }

  public CoordinateValidationException(String message, Throwable cause) {
    super(message, cause);
  }

}
