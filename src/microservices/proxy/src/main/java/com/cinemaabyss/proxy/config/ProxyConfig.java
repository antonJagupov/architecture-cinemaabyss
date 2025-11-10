package com.cinemaabyss.proxy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ProxyConfig {
    
    @Value("${monolith.url}")
    private String monolithUrl;
    
    @Value("${movies.service.url}")
    private String moviesServiceUrl;
    
    @Value("${events.service.url}")
    private String eventsServiceUrl;
    
    @Value("${gradual.migration:false}")
    private boolean gradualMigration;
    
    @Value("${movies.migration.percent:0}")
    private int moviesMigrationPercent;
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
    // Геттеры
    public String getMonolithUrl() { return monolithUrl; }
    public String getMoviesServiceUrl() { return moviesServiceUrl; }
    public String getEventsServiceUrl() { return eventsServiceUrl; }
    public boolean isGradualMigration() { return gradualMigration; }
    public int getMoviesMigrationPercent() { return moviesMigrationPercent; }
}