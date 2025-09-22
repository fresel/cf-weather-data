package com.github.fresel.cfwd.api.service.impl;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import com.github.fresel.cfwd.api.core.weather.owmap.GeoCodingResponse;
import com.github.fresel.cfwd.api.core.weather.owmap.WeatherClient;
import com.github.fresel.cfwd.api.core.weather.owmap.WeatherResponse;
import org.junit.jupiter.api.Test;

/**
 * Test class for {@link OwDataService}.
 */
public class OwDataServiceTest {

  @Test
  void givenValidCoordinates_whenNow_thenReturnsCurrentWeather() {
    // given
    WeatherClient weatherClientMock = mock(WeatherClient.class);
    WeatherResponse mockCurrentWeather = mock(WeatherResponse.class);
    GeoCodingResponse mockGeoCoding = mock(GeoCodingResponse.class);

    String lat = "51.5074";
    String lon = "0.1278";

    OwDataService service = new OwDataService(weatherClientMock);

    given(weatherClientMock.getReversedGeoCoding(Double.parseDouble(lat), Double.parseDouble(lon)))
        .willReturn(mockGeoCoding);
    given(mockGeoCoding.getName()).willReturn("London");
    given(weatherClientMock.getCurrentWeather(anyString())).willReturn(mockCurrentWeather);

    // when
    var result = service.now(lat, lon);

    // then
    then(result).isNotNull();
    then(result.getLocation()).isEqualTo("London");
    then(result.getTemperature()).isNotNull();
    then(result.getDescription()).isNotNull();

  }

}
