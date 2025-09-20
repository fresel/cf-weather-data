package com.github.fresel.cfwd.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Data;

/**
 * Configuration properties for the API.
 *
 * This class maps to properties defined in the application.yaml file under the prefix
 * "external.api.weather".
 */
@Configuration
@ConfigurationProperties(prefix = "external.api.weather")
@Data
public class ApiProperties {

  private String baseUrl;
  private String apiKey;

}
