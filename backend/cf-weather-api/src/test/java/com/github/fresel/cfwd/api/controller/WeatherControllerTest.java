package com.github.fresel.cfwd.api.controller;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.github.fresel.cfwd.api.service.CurrentWeather;
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

  private static final String OUT_OF_RANGE_LATITUDE = "100.0";

  private static final String OUT_OF_RANGE_LONGITUDE = "200.0";

  private static final String VALID_LATITUDE = "59.3293";

  private static final String VALID_LONGITUDE = "18.0686";

  private static final String TYPE_CURRENT = "current";

  private static final String TYPE_FORECAST = "forecast";

  private static final String TYPE_INVALID = "invalidType";

  private static final String INVALID_COORD_STRING = "invalid";

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private WeatherDataService weatherDataService;

  @Test
  void givenValidRequest_whenGetCurrentWeather_thenReturns200() throws Exception {
    // Given
    String lat = VALID_LATITUDE;
    String lon = VALID_LONGITUDE;
    String type = TYPE_CURRENT;

    var currentWeatherMock = mock(CurrentWeather.class);
    given(weatherDataService.now(anyDouble(), anyDouble())).willReturn(currentWeatherMock);

    // When / Then
    mockMvc.perform(get("/api/weather").param("lat", lat).param("lon", lon).param("type", type))
        .andExpect(status().isOk()).andExpect(content().contentType("application/json"));
  }

  @Test
  void givenValidRequest_whenGetForecastWeather_thenReturns200() throws Exception {
    // Given
    String lat = VALID_LATITUDE;
    String lon = VALID_LONGITUDE;
    String type = TYPE_FORECAST;

    // When / Then
    mockMvc.perform(get("/api/weather").param("lat", lat).param("lon", lon).param("type", type))
        .andExpect(status().isOk());
  }

  @Test
  void givenInvalidType_whenGetWeather_thenReturns400() throws Exception {
    // Given
    String lat = VALID_LATITUDE;
    String lon = VALID_LONGITUDE;
    String type = TYPE_INVALID;
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
  void givenOutOfRangeLatitude_whenGetWeather_thenReturns400() throws Exception {
    // Given
    String lat = OUT_OF_RANGE_LATITUDE;
    String lon = VALID_LONGITUDE;
    String type = TYPE_CURRENT;
    // When / Then
    mockMvc.perform(get("/api/weather").param("lat", lat).param("lon", lon).param("type", type))
        .andExpect(status().isBadRequest());
  }

  @Test
  void givenOutOfRangeLongitude_whenGetWeather_thenReturns400() throws Exception {
    // Given
    String lat = VALID_LATITUDE;
    String lon = OUT_OF_RANGE_LONGITUDE;
    String type = TYPE_CURRENT;
    // When / Then
    mockMvc.perform(get("/api/weather").param("lat", lat).param("lon", lon).param("type", type))
        .andExpect(status().isBadRequest());
  }
}
