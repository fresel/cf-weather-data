package com.github.fresel.cfwd.api.core.weather.owmap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class WeatherResponse {

  private Coord coord;

  private List<Weather> weather;

  private Main main;

  private long dt;

  @JsonIgnoreProperties(ignoreUnknown = true)
  @Data
  public static class Coord {
    private double lon;
    private double lat;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  @Data
  public static class Weather {
    private String main;
    private String description;
    private String icon;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  @Data
  public static class Main {
    private double temp;
    private double feelsLike;
    @JsonProperty("temp_min")
    private double tempMin;
    @JsonProperty("temp_max")
    private double tempMax;
    private int pressure;
    private int humidity;
  }

}
