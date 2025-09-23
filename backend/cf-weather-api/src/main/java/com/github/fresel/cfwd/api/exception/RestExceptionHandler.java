package com.github.fresel.cfwd.api.exception;

import com.github.fresel.cfwd.api.core.validation.CoordinateValidationException;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * Global exception handler for REST controllers.
 *
 * It captures specific exceptions and returns appropriate HTTP responses with error details.
 *
 * The {@link ResponseEntityExceptionHandler} is extended to provide default handling for Spring MVC
 * exceptions. Custom exception handlers can be added using the {@link ExceptionHandler} annotation.
 */
@RestControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(RuntimeException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<WeatherErrorResponse> handleAllUncaughtException(RuntimeException ex) {
    log.error("Unknown error occurred: ", ex);
    return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
  }

  @ExceptionHandler(value = {InvalidRequestDataException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<WeatherErrorResponse> handleInvalidRequestDataException(
      InvalidRequestDataException ex) {
    log.error("Invalid request data: ", ex);
    return createErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
  }

  @ExceptionHandler(CoordinateValidationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<WeatherErrorResponse> handleCoordinateValidationException(
      CoordinateValidationException ex) {
    log.error("Coordinate validation error: ", ex);
    return createErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
  }

  private static ResponseEntity<WeatherErrorResponse> createErrorResponse(
      @NonNull HttpStatus status, String message) {
    WeatherErrorResponse errorResponse = WeatherErrorResponse.builder()
        .timestamp(LocalDateTime.now()).error("Weather API Error").message(message).build();
    return ResponseEntity.status(status).body(errorResponse);
  }
}
