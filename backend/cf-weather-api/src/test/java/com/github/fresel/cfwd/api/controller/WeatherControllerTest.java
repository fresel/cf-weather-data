package com.github.fresel.cfwd.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(WeatherController.class)
@ActiveProfiles("test")
public class WeatherControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void givenValidRequest_whenGetCurrentWeather_thenReturns200() throws Exception {
    // Given
    String lat = "59.3293";
    String lon = "18.0686";
    String type = "current";

    // When / Then
    mockMvc.perform(get("/weather").param("lat", lat).param("lon", lon).param("type", type))
        .andExpect(status().isOk()).andExpect(content().contentType("application/json"));
  }

  @Test
  void givenValidRequest_whenGetForecastWeather_thenReturns200() throws Exception {
    // Given
    String lat = "59.3293";
    String lon = "18.0686";
    String type = "forecast";

    // When / Then
    mockMvc.perform(get("/weather").param("lat", lat).param("lon", lon).param("type", type))
        .andExpect(status().isOk()).andExpect(content().contentType("application/json"));
  }

}
