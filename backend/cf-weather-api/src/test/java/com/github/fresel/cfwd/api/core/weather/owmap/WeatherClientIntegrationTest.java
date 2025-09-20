package com.github.fresel.cfwd.api.core.weather.owmap;

import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Integration tests for {@link WeatherClient}.
 *
 * These tests require a valid API key to be set in the environment variable
 * `WEATHER_API_KEY_JUNIT`.
 */
public class WeatherClientIntegrationTest {

  @BeforeEach
  void setup() {
    // Initialize any required resources or mock objects here
  }

  @Test
  void givenValidApiKey_whenBuild_thenSuccess() {
    // given
    String apiKey = "test-api-key";

    // when
    WeatherClient client = WeatherClient.builder().apiKey(apiKey).build();

    // then
    then(client).isNotNull();
    then(client).hasFieldOrPropertyWithValue("apiKey", apiKey);
  }

  @Test
  void givenNoApiKey_whenBuild_thenThrowsException() {
    // when
    Throwable thrown = catchThrowable(() -> WeatherClient.builder().build());
    // then
    then(thrown).isInstanceOf(NullPointerException.class);
    then(thrown).hasMessageContaining("apiKey");
  }

  @Test
  void givenValidParams_whenGetCurrentWeather_thenReturnsWeatherResponse() {
    // given
    WeatherClient client = WeatherClient.builder().apiKey(getApiKey()).build();
    String lat = "37.7749"; // Example latitude
    String lon = "-122.4194"; // Example longitude
    // when
    WeatherResponse response = client.getCurrentWeather(lat, lon);
    // then
    then(response).isNotNull();

    then(response.getCoord()).isNotNull();
    then(response.getCoord().getLat()).isEqualTo(Double.parseDouble(lat));
    then(response.getCoord().getLon()).isEqualTo(Double.parseDouble(lon));

    then(response.getWeather()).isNotEmpty();
    then(response.getWeather().get(0).getMain()).isNotBlank();
    then(response.getWeather().get(0).getDescription()).isNotBlank();
    then(response.getWeather().get(0).getIcon()).isNotBlank();

    then(response.getMain()).isNotNull();
    then(response.getMain().getTemp()).isNotNull();
    then(response.getMain().getFeelsLike()).isNotNull();
    then(response.getMain().getTempMin()).isNotNull();
    then(response.getMain().getTempMax()).isNotNull();
    then(response.getMain().getPressure()).isGreaterThan(0);
    then(response.getMain().getHumidity()).isGreaterThan(0);
  }

  @Test
  void givenValidParams_whenGetWeatherForecast_thenReturnsWeatherResponse() {
    // given
    WeatherClient client = WeatherClient.builder().apiKey(getApiKey()).build();
    String lat = "37.7749"; // Example latitude
    String lon = "-122.4194"; // Example longitude
    // when
    ForecastResponse response = client.getWeatherForecast(lat, lon);
    // then
    then(response).isNotNull();
    then(response.getCnt()).isGreaterThan(0);
    then(response.getList()).isNotEmpty();
    then(response.getList().get(0).getMain()).isNotNull();
    then(response.getList().get(0).getWeather()).isNotEmpty();
  }

  @Test
  void givenInvalidApiKey_whenGetCurrentWeather_thenThrowsException() {
    // given
    WeatherClient client = WeatherClient.builder().apiKey("invalid-api-key").build();
    String lat = "37.7749"; // Example latitude
    String lon = "-122.4194"; // Example longitude
    // when
    Throwable thrown = catchThrowable(() -> client.getCurrentWeather(lat, lon));
    // then
    then(thrown).isInstanceOf(WeatherClientException.class);
    then(thrown).hasMessageContaining("Request failed");
  }

  @Test
  void givenInvalidApiKey_whenGetWeatherForecast_thenThrowsException() {
    // given
    WeatherClient client = WeatherClient.builder().apiKey("invalid-api-key").build();
    String lat = "37.7749"; // Example latitude
    String lon = "-122.4194"; // Example longitude
    // when
    Throwable thrown = catchThrowable(() -> client.getWeatherForecast(lat, lon));
    // then
    then(thrown).isInstanceOf(WeatherClientException.class);
    then(thrown).hasMessageContaining("Request failed");
  }

  @Test
  void givenInvalidParams_whenGetCurrentWeather_thenThrowsException() {
    // given
    WeatherClient client = WeatherClient.builder().apiKey(getApiKey()).build();
    String lat = "invalid-lat"; // Invalid latitude
    String lon = "invalid-lon"; // Invalid longitude
    // when
    Throwable thrown = catchThrowable(() -> client.getCurrentWeather(lat, lon));
    // then
    then(thrown).isInstanceOf(WeatherClientException.class);
    then(thrown).hasMessageContaining("Request failed");
  }

  @Test
  void givenInvalidParams_whenGetWeatherForecast_thenThrowsException() {
    // given
    WeatherClient client = WeatherClient.builder().apiKey(getApiKey()).build();
    String lat = "invalid-lat"; // Invalid latitude
    String lon = "invalid-lon"; // Invalid longitude
    // when
    Throwable thrown = catchThrowable(() -> client.getWeatherForecast(lat, lon));
    // then
    then(thrown).isInstanceOf(WeatherClientException.class);
    then(thrown).hasMessageContaining("Request failed");
  }

  @Disabled
  @Test
  void givenNetworkIssue_whenGetCurrentWeather_thenThrowsException() {
    // This test would require a way to simulate network issues, which is not implemented here.
    // In a real-world scenario, you might use a mocking library to simulate this.
  }

  @Disabled
  @Test
  void givenNetworkIssue_whenGetWeatherForecast_thenThrowsException() {
    // This test would require a way to simulate network issues, which is not implemented here.
    // In a real-world scenario, you might use a mocking library to simulate this.
  }

  @Disabled
  @Test
  void givenMalformedResponse_whenGetCurrentWeather_thenThrowsException() {
    // This test would require a way to simulate a malformed response, which is not implemented
    // here.
    // In a real-world scenario, you might use a mocking library to simulate this.
  }

  @Disabled
  @Test
  void givenMalformedResponse_whenGetWeatherForecast_thenThrowsException() {
    // This test would require a way to simulate a malformed response, which is not implemented
    // here.
    // In a real-world scenario, you might use a mocking library to simulate this.
  }

  private static String getApiKey() {
    return System.getenv("WEATHER_API_KEY_JUNIT");
  }
}
