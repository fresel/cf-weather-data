# CF Weather Data

CF Weather Data is a service providing weather data for the provided coordinates via a REST API.

## Overview
![Overview](design/overview.png)

## Functionality

Based on the longitude and latitude provided, the API can:

- Get current weather
- Get a forecast for the next 5 days

## Usage

To use the CF Weather Data API, make a GET request to the following endpoint and include the latitude, longitude, and type of data you want (current weather or forecast) as query parameters:

```
GET /api/weather?lat={latitude}&lon={longitude}&type=current|forecast
```

**Example requests:**

> Replace `{latitude}` and `{longitude}` with the desired coordinates. The API will return the weather data for the specified location.

For current weather data, use the following endpoint:
```
GET /api/weather?lat={latitude}&lon={longitude}&type=current
```
```json
{
	"dateTime": "2025-09-22T19:45:02",
	"location": "London",
	"temperature": 11.22,
	"main": "Clear",
	"description": "clear sky"
}
```

For the 5-day forecast (3-hour intervals), use the following endpoint:

```
GET /api/weather?lat={latitude}&lon={longitude}&type=forecast
```
```json
{
	"location": "London",
	"forecastDays": [
		{
			"dateTime": "2025-09-22T18:00:00",
			"minTemperature": 13.55,
			"maxTemperature": 14.87,
			"main": "Clouds",
			"description": "broken clouds"
		},
		{
			"dateTime": "2025-09-22T21:00:00",
			"minTemperature": 10.09,
			"maxTemperature": 11.9,
			"main": "Clouds",
			"description": "scattered clouds"
		}
        // ... more forecast data, in total 40 measurements covering the next 5 days in 3 hour intervals ...
    ]
}
```
## Error Handling

If the request is invalid or if there is an issue with the external weather service, the API will return an appropriate error message and status code.

```json
{
    "timestamp": "2025-09-22T19:45:02",
    "status": 400,
    "error": "Invalid type parameter",
    "description": "The type parameter must be either 'current' or 'forecast'."
}
```

## Technologies Used
- Java 21
- Spring Boot
- OpenAPI
- Maven
- Docker
- OpenWeatherMap API

## Setup and Installation
Prerequisites:
- Java 21
- Maven
- Environment variable `WEATHER_API_KEY` set with your OpenWeatherMap API key.
- Environment variable `WEATHER_API_URL_JUNIT` set with the URL of a API for integration testing purposes.

To set up and run the CF Weather Data API locally, follow these steps:
1. Clone the repository:
   ```
   git clone
    ```
2. Navigate to the project directory:
   ```
   cd cf-weather-data/backend/cf-weather-data
   ```
3. Run the project using Maven:
   ```
   mvn spring-boot:run
   ```
4. The API will be accessible at `http://localhost:8080/api/weather`.
