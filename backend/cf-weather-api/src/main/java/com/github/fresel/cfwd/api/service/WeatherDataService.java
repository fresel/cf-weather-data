package com.github.fresel.cfwd.api.service;

public interface WeatherDataService {

  void getCurrentWeather(String lat, String lon);

  void getWeatherForecast(String lat, String lon, int days);

}
