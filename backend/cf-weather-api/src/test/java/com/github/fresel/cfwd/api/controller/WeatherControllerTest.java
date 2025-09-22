package com.github.fresel.cfwd.api.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.github.fresel.cfwd.api.service.CurrentWeather;
import com.github.fresel.cfwd.api.service.Forecast;
import com.github.fresel.cfwd.api.service.WeatherDataService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(WeatherController.class)
@ActiveProfiles("test")
public class WeatherControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private WeatherDataService weatherDataService;

  @Test
  void givenValidRequest_whenGetCurrentWeather_thenReturns200() throws Exception {
    // Given
    String lat = "59.3293";
    String lon = "18.0686";
    String type = "current";

    var currentWeatherMock = mock(CurrentWeather.class);
    given(weatherDataService.now(lat, lon)).willReturn(currentWeatherMock);

    // When / Then
    mockMvc.perform(get("/api/weather").param("lat", lat).param("lon", lon).param("type", type))
        .andExpect(status().isOk()).andExpect(content().contentType("application/json"));
  }

  @Test
  void givenValidRequest_whenGetForecastWeather_thenReturns200() throws Exception {
    // Given
    String lat = "59.3293";
    String lon = "18.0686";
    String type = "forecast";

    var forecastMock = mock(Forecast.class);
    given(weatherDataService.forecast(lat, lon)).willReturn(forecastMock);

    // When / Then
    mockMvc.perform(get("/api/weather").param("lat", lat).param("lon", lon).param("type", type))
        .andExpect(status().isOk()).andExpect(content().contentType("application/json"));
  }

  @Test
  void givenInvalidType_whenGetWeather_thenReturns400() throws Exception {
    // Given
    String lat = "59.3293";
    String lon = "18.0686";
    String type = "invalidType";

    // When / Then
    mockMvc.perform(get("/api/weather").param("lat", lat).param("lon", lon).param("type", type))
        .andExpect(status().isBadRequest());
  }

  @Test
  void givenMissingParams_whenGetWeather_thenReturns400() throws Exception {
    // When / Then
    mockMvc.perform(get("/api/weather")).andExpect(status().isBadRequest());
  }

  @Test
  void givenInvalidLatLon_whenGetWeather_thenReturns500() throws Exception {
    // Given
    String lat = "invalidLat";
    String lon = "invalidLon";
    String type = "current";
    given(weatherDataService.now(lat, lon))
        .willThrow(new IllegalArgumentException("Invalid lat/lon"));
    // When / Then
    mockMvc.perform(get("/api/weather").param("lat", lat).param("lon", lon).param("type", type))
        .andExpect(status().isInternalServerError());
  }
}
