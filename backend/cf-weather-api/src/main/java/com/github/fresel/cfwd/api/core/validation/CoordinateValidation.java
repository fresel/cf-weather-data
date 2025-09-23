package com.github.fresel.cfwd.api.core.validation;

import lombok.extern.slf4j.Slf4j;

/**
 * Utility class for validating geographic coordinates.
 *
 * This class provides methods to validate latitude and longitude values to ensure they fall within
 * acceptable ranges.
 *
 * Latitude must be between -90 and 90 degrees, and longitude must be between -180 and 180 degrees.
 */
@Slf4j
public class CoordinateValidation {

  public static final double MIN_LATITUDE = -90.0;

  public static final double MAX_LATITUDE = 90.0;

  public static final double MIN_LONGITUDE = -180.0;

  public static final double MAX_LONGITUDE = 180.0;

  /**
   * Validates the given latitude.
   *
   * @param latitude the latitude to validate
   * @throws CoordinateValidationException if the latitude is out of range
   */
  public static void validateLatitude(double latitude) {
    if (latitude < MIN_LATITUDE || latitude > MAX_LATITUDE) {
      log.error("Invalid latitude: {}", latitude);
      throw new CoordinateValidationException("Invalid latitude: " + latitude);
    }
  }

  /**
   * Validates the given longitude.
   *
   * @param longitude the longitude to validate
   * @throws CoordinateValidationException if the longitude is out of range
   */
  public static void validateLongitude(double longitude) {
    if (longitude < MIN_LONGITUDE || longitude > MAX_LONGITUDE) {
      log.error("Invalid longitude: {}", longitude);
      throw new CoordinateValidationException("Invalid longitude: " + longitude);
    }
  }

  /**
   * Validates the given latitude and longitude.
   *
   * @param latitude the latitude to validate
   * @param longitude the longitude to validate
   * @throws CoordinateValidationException if the coordinates are out of range
   */
  public static void validateCoordinates(double latitude, double longitude) {
    validateLatitude(latitude);
    validateLongitude(longitude);
  }

  private CoordinateValidation() {
    // Private constructor to prevent instantiation
  }
}
