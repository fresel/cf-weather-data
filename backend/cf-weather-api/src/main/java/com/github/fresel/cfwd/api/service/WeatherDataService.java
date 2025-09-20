package com.github.fresel.cfwd.api.service;

public interface WeatherDataService {

  CurrentWeather now(String lat, String lon);

  Forecast forecast(String lat, String lon);

}
