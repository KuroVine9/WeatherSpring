package com.kuro9.weather.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "weather.api")
@Getter
@Setter
public class WeatherApiConfig {
    private String midBaseUrl;
    private String shortBaseUrl;
    private String shortPath;
    private String ultraShortPath;
    private String apiKey;
}
