package com.cinemaabyss.proxy.service;

import com.cinemaabyss.proxy.config.ProxyConfig;
import com.cinemaabyss.proxy.dto.ProxyResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Random;

@Service
public class ProxyService {
    
    private static final Logger logger = LoggerFactory.getLogger(ProxyService.class);
    private final Random random = new Random();
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private ProxyConfig config;
    
    public ProxyResponse routeRequest(String path, HttpMethod method, HttpEntity<?> requestEntity) {
        try {
            // Определяем целевой сервис на основе пути
            if (path.startsWith("/movies")) {
                return routeToMoviesService(path, method, requestEntity);
            } else if (path.startsWith("/events")) {
                return routeToEventsService(path, method, requestEntity);
            } else {
                // Для всех остальных запросов используем монолит
                return routeToMonolith(path, method, requestEntity);
            }
        } catch (Exception e) {
            logger.error("Error routing request to path: {}", path, e);
            return new ProxyResponse("Internal server error: " + e.getMessage());
        }
    }
    
    private ProxyResponse routeToMoviesService(String path, HttpMethod method, HttpEntity<?> requestEntity) {
        if (config.isGradualMigration()) {
            // Стратегия постепенной миграции
            if (shouldRouteToMicroservice(config.getMoviesMigrationPercent())) {
                logger.info("Routing movies request to microservice: {}", path);
                return callService(config.getMoviesServiceUrl() + path, method, requestEntity, "movies-service");
            } else {
                logger.info("Routing movies request to monolith: {}", path);
                return callService(config.getMonolithUrl() + path, method, requestEntity, "monolith");
            }
        } else {
            // Полная миграция - всегда направляем в микросервис
            return callService(config.getMoviesServiceUrl() + path, method, requestEntity, "movies-service");
        }
    }
    
    private ProxyResponse routeToEventsService(String path, HttpMethod method, HttpEntity<?> requestEntity) {
        // Пока всегда направляем в микросервис events
        return callService(config.getEventsServiceUrl() + path, method, requestEntity, "events-service");
    }
    
    private ProxyResponse routeToMonolith(String path, HttpMethod method, HttpEntity<?> requestEntity) {
        return callService(config.getMonolithUrl() + path, method, requestEntity, "monolith");
    }
    
    private ProxyResponse callService(String url, HttpMethod method, HttpEntity<?> requestEntity, String serviceName) {
        try {
            logger.debug("Calling {}: {} with method {}", serviceName, url, method);
            
            ResponseEntity<String> response = restTemplate.exchange(
                url, 
                method, 
                requestEntity, 
                String.class
            );
            
            return new ProxyResponse(response.getBody(), serviceName);
            
        } catch (Exception e) {
            logger.error("Error calling {}: {}", serviceName, url, e);
            return new ProxyResponse("Error calling " + serviceName + ": " + e.getMessage());
        }
    }
    
    private boolean shouldRouteToMicroservice(int percent) {
        return random.nextInt(100) < percent;
    }
    
    // Метод для health check всех сервисов
    public HealthStatus checkServicesHealth() {
        HealthStatus status = new HealthStatus();
        
        status.setMonolith(checkServiceHealth(config.getMonolithUrl()));
        status.setMoviesService(checkServiceHealth(config.getMoviesServiceUrl()));
        status.setEventsService(checkServiceHealth(config.getEventsServiceUrl()));
        
        return status;
    }
    
    private boolean checkServiceHealth(String url) {
        try {
            String healthUrl = UriComponentsBuilder.fromHttpUrl(url)
                    .path("/actuator/health")
                    .toUriString();
            
            ResponseEntity<String> response = restTemplate.getForEntity(healthUrl, String.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            logger.warn("Health check failed for: {}", url);
            return false;
        }
    }
    
    public static class HealthStatus {
        private boolean monolith;
        private boolean moviesService;
        private boolean eventsService;
        
        // Геттеры и сеттеры
        public boolean isMonolith() { return monolith; }
        public void setMonolith(boolean monolith) { this.monolith = monolith; }
        public boolean isMoviesService() { return moviesService; }
        public void setMoviesService(boolean moviesService) { this.moviesService = moviesService; }
        public boolean isEventsService() { return eventsService; }
        public void setEventsService(boolean eventsService) { this.eventsService = eventsService; }
    }
}