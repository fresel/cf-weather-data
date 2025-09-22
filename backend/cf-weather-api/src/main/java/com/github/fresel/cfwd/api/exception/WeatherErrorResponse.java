package com.github.fresel.cfwd.api.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;

/**
 * Represents an error response for weather-related API requests.
 */
@Builder
@Value
public class WeatherErrorResponse {

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
  private LocalDateTime timestamp;

  String error;

  String message;
}
