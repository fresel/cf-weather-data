package com.github.fresel.cfwd.api.core.weather.owmap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ForecastResponse {

  private int cnt;

  private List<WeatherResponse> list;
}
