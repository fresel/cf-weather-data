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
 * This client provides methods to fetch current weather data and weather forecasts based on city
 * names.
 *
 * This client also provides methods to fetch current weather data and weather forecasts based on
 * latitude and longitude.
 *
 * It uses Java's built-in HttpClient for making HTTP requests and Jackson for JSON parsing.
 *
 * An API key is required to use this client, which can be obtained by signing up on the
 * OpenWeatherMap website.
 *
 * Note: Ensure to handle exceptions appropriately when using this client, as network issues or
 * invalid responses can occur.
 */
@Builder
public class WeatherClient {

  public static final String WEATHER_BASE_URL = "https://api.openweathermap.org/data/2.5";

  public static final String REVERSE_GEO_BASE_URL = "http://api.openweathermap.org/geo/1.0/reverse";

  @NonNull
  private final String apiKey;

  /**
   * Get current weather data for the specified city.
   *
   * @param city Name of the city.
   * @return WeatherResponse containing current weather data.
   */
  public WeatherResponse getCurrentWeather(String city) {
    HttpRequest request = HttpRequest.newBuilder()
        .uri(java.net.URI
            .create(String.format("%s/weather?q=%s&appid=%s", WEATHER_BASE_URL, city, apiKey)))
        .GET().build();
    return sendRequest(request, WeatherResponse.class);
  }

  /**
   * Get current weather data for the specified latitude and longitude.
   *
   * @param lat Latitude of the location.
   * @param lon Longitude of the location.
   * @return WeatherResponse containing current weather data.
   */
  public WeatherResponse getCurrentWeather(String lat, String lon) {
    HttpRequest request = HttpRequest.newBuilder()
        .uri(java.net.URI.create(
            String.format("%s/weather?lat=%s&lon=%s&appid=%s", WEATHER_BASE_URL, lat, lon, apiKey)))
        .GET().build();
    return sendRequest(request, WeatherResponse.class);
  }

  /**
   * Get weather forecast data for the specified city.
   *
   * @param city Name of the city.
   * @return ForecastResponse containing weather forecast data.
   */
  public ForecastResponse getWeatherForecast(String city) {
    HttpRequest request = HttpRequest.newBuilder()
        .uri(java.net.URI
            .create(String.format("%s/forecast?q=%s&appid=%s", WEATHER_BASE_URL, city, apiKey)))
        .GET().build();
    return sendRequest(request, ForecastResponse.class);
  }

  /**
   * Get weather forecast data for the specified latitude and longitude.
   *
   * @param lat Latitude of the location.
   * @param lon Longitude of the location.
   * @return ForecastResponse containing weather forecast data.
   */
  public ForecastResponse getWeatherForecast(String lat, String lon) {
    HttpRequest request =
        HttpRequest
            .newBuilder().uri(java.net.URI.create(String
                .format("%s/forecast?lat=%s&lon=%s&appid=%s", WEATHER_BASE_URL, lat, lon, apiKey)))
            .GET().build();
    return sendRequest(request, ForecastResponse.class);
  }

  /**
   * Get city name for the specified latitude and longitude using reverse geocoding.
   *
   * @param lat Latitude of the location.
   * @param lon Longitude of the location.
   * @return GeoCodingResponse containing city name and other details.
   */
  public GeoCodingResponse getReversedGeoCoding(double lat, double lon) {
    HttpRequest request = HttpRequest.newBuilder().uri(java.net.URI.create(
        String.format("%s?lat=%s&lon=%s&limit=1&appid=%s", REVERSE_GEO_BASE_URL, lat, lon, apiKey)))
        .GET().build();
    GeoCodingResponse[] responseArray = sendRequest(request, GeoCodingResponse[].class);
    return responseArray[0];
  }

  // Helper method to send HTTP requests and parse responses
  public static <T> T sendRequest(HttpRequest request, Class<T> responseType) {
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
