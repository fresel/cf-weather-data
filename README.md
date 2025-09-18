# CF Weather Data

CF Weather Data is a REST API providing weather data for the provided cooridnates.

This service is a wrapper of the API provided by https://openweathermap.org.

## Functionality

Based on the longitude and latitude provided, the API can:

- Get current weather
- Get a forecast for the next 5 days

## Usage

To use the CF Weather Data API, make a GET request to the following endpoint and include the latitude, longitude, and type of data you want (current weather or forecast) as query parameters:

```
GET /weather?lat={latitude}&lon={longitude}&type=current|forecast
```

**Example requests:**

For current weather data, use the following endpoint:
```
GET /weather?lat={latitude}&lon={longitude}&type=current
```

Replace `{latitude}` and `{longitude}` with the desired coordinates. The API will return the current weather data for the specified location.

For the 5-day forecast, use the following endpoint:

```
GET /weather?lat={latitude}&lon={longitude}&type=forecast
```

Again, replace `{latitude}` and `{longitude}` with the desired coordinates. The API will return the weather forecast for the next 5 days.