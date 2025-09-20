package com.github.fresel.cfwd.api.core.weather.owmap;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import lombok.Builder;
import lombok.NonNull;

/**
 * Client for interacting with the OpenWeatherMap API.
 *
 * This client provides methods to fetch current weather data and weather forecasts based on
 * latitude and longitude.
 *
 * Note: Ensure to handle exceptions appropriately when using this client, as network issues or
 * invalid responses can occur.
 */
@Builder
public class WeatherClient {

  private static final String BASE_URL = "https://api.openweathermap.org/data/2.5";

  @NonNull
  private final String apiKey;

  /**
   * Get current weather data for the specified latitude and longitude.
   *
   * @param lat Latitude of the location.
   * @param lon Longitude of the location.
   * @return WeatherResponse containing current weather data.
   */
  public WeatherResponse getCurrentWeather(String lat, String lon) {
    HttpRequest request = HttpRequest.newBuilder()
        .uri(java.net.URI
            .create(String.format("%s/weather?lat=%s&lon=%s&appid=%s", BASE_URL, lat, lon, apiKey)))
        .GET().build();
    return sendRequest(request, WeatherResponse.class);
  }

  /**
   * Get weather forecast data for the specified latitude and longitude.
   *
   * @param lat Latitude of the location.
   * @param lon Longitude of the location.
   * @return ForecastResponse containing weather forecast data.
   */
  public ForecastResponse getWeatherForecast(String lat, String lon) {
    HttpRequest request = HttpRequest.newBuilder()
        .uri(java.net.URI.create(
            String.format("%s/forecast?lat=%s&lon=%s&appid=%s", BASE_URL, lat, lon, apiKey)))
        .GET().build();
    return sendRequest(request, ForecastResponse.class);
  }

  private static <T> T sendRequest(HttpRequest request, Class<T> responseType) {
    HttpClient client = httpClient();
    HttpResponse<String> response;
    try {
      response = client.send(request, BodyHandlers.ofString());
    } catch (IOException | InterruptedException e) {
      throw new WeatherClientException("Error sending request", e);
    }
    if (response.statusCode() != 200) {
      throw new WeatherClientException("Request failed: " + response.body());
    }
    var mapper = new ObjectMapper();
    try {
      return mapper.readValue(response.body(), responseType);
    } catch (IOException e) {
      throw new WeatherClientException("Error parsing response", e);
    }
  }

  private static HttpClient httpClient() {
    return HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
  }
}
