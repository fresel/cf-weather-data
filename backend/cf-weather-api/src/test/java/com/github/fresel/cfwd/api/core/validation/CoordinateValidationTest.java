package com.github.fresel.cfwd.api.core.validation;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import org.junit.jupiter.api.Test;

/** Test class for {@link CoordinateValidation}. */
public class CoordinateValidationTest {

  private static final double VALID_LATITUDE = 45.0;

  private static final double VALID_LONGITUDE = 90.0;

  private static final double INVALID_LATITUDE = 100.0;

  private static final double INVALID_LONGITUDE = 200.0;

  @Test
  void givenValidCoordinates_whenValidateCoordinates_thenNoExceptionThrown() {
    // given
    double latitude = VALID_LATITUDE;
    double longitude = VALID_LONGITUDE;

    // when
    Throwable thrown =
        catchThrowable(() -> CoordinateValidation.validateCoordinates(latitude, longitude));

    // then
    then(thrown).isNull();
  }

  @Test
  void givenInvalidLatitude_whenValidateCoordinates_thenThrowsException() {
    // given
    double invalidLatitude = INVALID_LATITUDE;
    double longitude = VALID_LONGITUDE;

    // when
    Throwable thrown =
        catchThrowable(() -> CoordinateValidation.validateCoordinates(invalidLatitude, longitude));

    // then
    then(thrown).isInstanceOf(CoordinateValidationException.class)
        .hasMessageContaining("Invalid latitude");
  }

  @Test
  void givenInvalidLongitude_whenValidateCoordinates_thenThrowsException() {
    // given
    double latitude = VALID_LATITUDE;
    double invalidLongitude = INVALID_LONGITUDE;

    // when
    Throwable thrown =
        catchThrowable(() -> CoordinateValidation.validateCoordinates(latitude, invalidLongitude));

    // then
    then(thrown).isInstanceOf(CoordinateValidationException.class)
        .hasMessageContaining("Invalid longitude");
  }

}
