package com.github.fresel.cfwd.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.NotBlank;

/**
 * Configuration properties for the API.
 *
 * This class maps to properties defined in the application.yaml file under the prefix
 * "external.api.weather".
 */
@Validated
@ConfigurationProperties(prefix = "external.api.weather")
public record ApiProperties(
    @NotBlank(message = "WEATHER_API_KEY must be set and not empty") String apiKey) {
}
