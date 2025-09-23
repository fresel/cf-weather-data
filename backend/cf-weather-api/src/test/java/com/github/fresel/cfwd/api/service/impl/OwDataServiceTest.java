package com.github.fresel.cfwd.api.service.impl;

import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import com.github.fresel.cfwd.api.core.weather.owmap.ForecastResponse;
import com.github.fresel.cfwd.api.core.weather.owmap.GeoCodingResponse;
import com.github.fresel.cfwd.api.core.weather.owmap.WeatherClient;
import com.github.fresel.cfwd.api.core.weather.owmap.WeatherResponse;
import com.github.fresel.cfwd.api.service.CurrentWeather;
import com.github.fresel.cfwd.api.service.Forecast;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Test class for {@link OwDataService}.
 */
public class OwDataServiceTest {

  private static final String TEST_CITY = "Test City";

  @Mock
  private WeatherClient weatherClientMock;

  private OwDataService owDataService;

  private AutoCloseable autoCloseable;

  @BeforeEach
  void setUp() {
    // Initialize mocks
    autoCloseable = MockitoAnnotations.openMocks(this);

    // Create instance of OwDataService with mocked WeatherClient
    owDataService = new OwDataService(weatherClientMock);
  }

  @AfterEach
  void tearDown() throws Exception {
    // Release mocks
    if (autoCloseable == null) {
      autoCloseable.close();
    }
  }

  @Test
  void givenValidLocation_whenNow_thenReturnsCurrentWeather() {
    // given
    GeoCodingResponse mockGeoCoding = mock(GeoCodingResponse.class);
    given(mockGeoCoding.getName()).willReturn(TEST_CITY);

    WeatherResponse.Weather mockWeather = mock(WeatherResponse.Weather.class);

    WeatherResponse mockWeatherResponse = mock(WeatherResponse.class);
    given(mockWeatherResponse.getWeather()).willReturn(java.util.List.of(mockWeather));
    given(mockWeatherResponse.getMain()).willReturn(mock(WeatherResponse.Main.class));

    given(weatherClientMock.getReversedGeoCoding(1.0, 2.0)).willReturn(mockGeoCoding);
    given(weatherClientMock.getCurrentWeather(TEST_CITY)).willReturn(mockWeatherResponse);

    // when
    CurrentWeather currentWeather = owDataService.now(1.0, 2.0);

    // then
    then(currentWeather).isNotNull();
    then(currentWeather.getLocation()).isEqualTo(TEST_CITY);
  }

  @Test
  void givenValidLocation_whenForecast_thenReturnsForecast() {
    // given
    GeoCodingResponse mockGeoCoding = mock(GeoCodingResponse.class);
    given(mockGeoCoding.getName()).willReturn(TEST_CITY);
    given(weatherClientMock.getReversedGeoCoding(1.0, 2.0)).willReturn(mockGeoCoding);

    WeatherResponse.Weather mockWeather = mock(WeatherResponse.Weather.class);

    WeatherResponse.Main mockMain = mock(WeatherResponse.Main.class);

    WeatherResponse mockWeatherResponse = mock(WeatherResponse.class);
    given(mockWeatherResponse.getWeather()).willReturn(java.util.List.of(mockWeather));
    given(mockWeatherResponse.getMain()).willReturn(mockMain);

    ForecastResponse mockForecastResponse = mock(ForecastResponse.class);
    given(mockForecastResponse.getList()).willReturn(java.util.List.of(mockWeatherResponse));

    given(weatherClientMock.getWeatherForecast(TEST_CITY)).willReturn(mockForecastResponse);

    // when
    Forecast forecast = owDataService.forecast(1.0, 2.0);

    // then
    then(forecast).isNotNull();
    then(forecast.getLocation()).isEqualTo(TEST_CITY);
  }

  @Test
  void givenNoWeatherData_whenNow_thenThrowsException() {
    // given
    GeoCodingResponse mockGeoCoding = mock(GeoCodingResponse.class);
    given(mockGeoCoding.getName()).willReturn(TEST_CITY);
    given(weatherClientMock.getReversedGeoCoding(1.0, 2.0)).willReturn(mockGeoCoding);
    WeatherResponse mockWeatherResponse = mock(WeatherResponse.class);
    given(mockWeatherResponse.getWeather()).willReturn(java.util.Collections.emptyList());
    given(weatherClientMock.getCurrentWeather(TEST_CITY)).willReturn(mockWeatherResponse);
    // when / then
    Throwable thrown = catchThrowable(() -> owDataService.now(1.0, 2.0));
    then(thrown)
        .isInstanceOf(com.github.fresel.cfwd.api.exception.WeatherDataServiceException.class);
  }

  @Test
  void givenNoForecastData_whenForecast_thenThrowsException() {
    // given
    GeoCodingResponse mockGeoCoding = mock(GeoCodingResponse.class);
    given(mockGeoCoding.getName()).willReturn(TEST_CITY);
    given(weatherClientMock.getReversedGeoCoding(1.0, 2.0)).willReturn(mockGeoCoding);
    ForecastResponse mockForecastResponse = mock(ForecastResponse.class);
    given(mockForecastResponse.getList()).willReturn(java.util.Collections.emptyList());
    given(weatherClientMock.getWeatherForecast(TEST_CITY)).willReturn(mockForecastResponse);
    // when / then
    Throwable thrown = catchThrowable(() -> owDataService.forecast(1.0, 2.0));
    then(thrown)
        .isInstanceOf(com.github.fresel.cfwd.api.exception.WeatherDataServiceException.class);
  }
}
