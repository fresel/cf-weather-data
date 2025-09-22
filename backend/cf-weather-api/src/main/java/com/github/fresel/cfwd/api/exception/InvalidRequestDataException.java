package com.github.fresel.cfwd.api.exception;

/**
 * Exception thrown when the request data is invalid.
 */
public class InvalidRequestDataException extends RuntimeException {
  public InvalidRequestDataException(String message) {
    super(message);
  }

  public InvalidRequestDataException(String message, Throwable cause) {
    super(message, cause);
  }

}
