package com.github.fresel.cfwd.api.core.weather.owmap;

import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import java.net.http.HttpRequest;
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
  void givenValidParams_whenGetCurrentWeatherCity_thenReturnsWeatherResponse() {
    // given
    WeatherClient client = WeatherClient.builder().apiKey(getApiKey()).build();
    String city = "London"; // Example city
    // when
    WeatherResponse response = client.getCurrentWeather(city);
    // then
    then(response).isNotNull();
    then(response.getCoord()).isNotNull();
    then(response.getCoord().getLat()).isNotNull();
    then(response.getCoord().getLon()).isNotNull();

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
  void givenValidParams_whenGetCurrentWeatherLatLon_thenReturnsWeatherResponse() {
    // given
    WeatherClient client = WeatherClient.builder().apiKey(getApiKey()).build();
    Double lat = 37.7749; // Example latitude
    Double lon = -122.4194; // Example longitude
    // when
    WeatherResponse response = client.getCurrentWeather(lat, lon);
    // then
    then(response).isNotNull();

    then(response.getCoord()).isNotNull();
    then(response.getCoord().getLat()).isEqualTo(lat);
    then(response.getCoord().getLon()).isEqualTo(lon);

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
  void givenValidParams_whenGetCurrentWeatherForecastCity_thenReturnsForecastResponse() {
    // given
    WeatherClient client = WeatherClient.builder().apiKey(getApiKey()).build();
    String city = "London"; // Example city
    // when
    ForecastResponse response = client.getWeatherForecast(city);
    // then
    then(response).isNotNull();
    then(response.getCnt()).isGreaterThan(0);
    then(response.getList()).isNotEmpty();
    then(response.getList().get(0).getMain()).isNotNull();
    then(response.getList().get(0).getWeather()).isNotEmpty();
  }

  @Test
  void givenValidParams_whenGetWeatherForecastLatLon_thenReturnsWeatherResponse() {
    // given
    WeatherClient client = WeatherClient.builder().apiKey(getApiKey()).build();
    Double lat = 37.7749; // Example latitude
    Double lon = -122.4194; // Example longitude
    // when
    ForecastResponse response = client.getWeatherForecast(lat, lon);
    // then
    then(response).isNotNull();
    then(response.getCnt()).isGreaterThan(0);
    then(response.getList()).isNotEmpty();
    then(response.getList().get(0).getMain()).isNotNull();
    then(response.getList().get(0).getWeather()).isNotEmpty();
  }

  //
  // Test the reverse geocoding methods
  //

  @Test
  void givenValidParams_whenGetCityNameLatLon_thenReturnsCityName() {
    // given
    WeatherClient client = WeatherClient.builder().apiKey(getApiKey()).build();
    double lat = 51.5085; // London latitude
    double lon = -0.1257; // London longitude
    // when
    GeoCodingResponse response = client.getReversedGeoCoding(lat, lon);
    // then
    then(response).isNotNull();
    then(response.getName()).isEqualTo("London");
  }

  //
  // Test the sendRequest method
  //

  @Test
  void givenValidRequest_whenSendRequest_thenReturnsResponse() {
    // given
    HttpRequest request = HttpRequest.newBuilder()
        .uri(java.net.URI.create(
            "%s/weather?q=London&appid=%s".formatted(WeatherClient.WEATHER_BASE_URL, getApiKey())))
        .GET().build();
    // when
    WeatherResponse response = WeatherClient.sendRequest(request, WeatherResponse.class);
    // then
    then(response).isNotNull();
  }

  @Test
  void givenInvalidRequest_whenSendRequest_thenThrowsException() {
    // given
    HttpRequest request = HttpRequest.newBuilder().uri(java.net.URI.create(
        "%s/weather?q=InvalidCity&appid=%s".formatted(WeatherClient.WEATHER_BASE_URL, getApiKey())))
        .GET().build();
    // when
    Throwable thrown =
        catchThrowable(() -> WeatherClient.sendRequest(request, WeatherResponse.class));
    // then
    then(thrown).isInstanceOf(WeatherClientException.class);
    then(thrown).hasMessageContaining("Request failed");
  }

  @Test
  void givenInvalidResponseType_whenSendRequest_thenThrowsException() {
    // given
    HttpRequest request = HttpRequest.newBuilder()
        .uri(java.net.URI.create(
            "%s/weather?q=London&appid=%s".formatted(WeatherClient.WEATHER_BASE_URL, getApiKey())))
        .GET().build();
    // when
    Throwable thrown = catchThrowable(() -> WeatherClient.sendRequest(request, String.class));
    // then
    then(thrown).isInstanceOf(WeatherClientException.class);
    then(thrown).hasMessageContaining("Error parsing response");
  }

  @Test
  void givenClientThrowsException_whenSendRequest_thenThrowsWeatherClientException() {
    // given
    HttpRequest request =
        HttpRequest.newBuilder().uri(java.net.URI.create("http://invalid-url")).GET().build();
    // when
    Throwable thrown =
        catchThrowable(() -> WeatherClient.sendRequest(request, WeatherResponse.class));
    // then
    then(thrown).isInstanceOf(WeatherClientException.class);
    then(thrown).hasMessageContaining("Error sending request");
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
