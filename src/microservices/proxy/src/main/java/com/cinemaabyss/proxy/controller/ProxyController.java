package com.cinemaabyss.proxy.controller;

import com.cinemaabyss.proxy.config.ProxyConfig;
import com.cinemaabyss.proxy.service.ProxyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@RestController
@RequestMapping("/**")
public class ProxyController {

    private static final Logger logger = LoggerFactory.getLogger(ProxyController.class);

    @Autowired
    private ProxyService proxyService;
    @Autowired
    private ProxyConfig proxyConfig;

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
            RequestMethod.DELETE, RequestMethod.PATCH})
    public ResponseEntity<Object> proxyRequest(
            HttpServletRequest request,
            @RequestBody(required = false) byte[] body) {

        String path = getRequestPath(request);
        HttpMethod method = HttpMethod.valueOf(request.getMethod());

        logger.info("Proxying request: {} {}", method, path);

        // Создаем HttpEntity с заголовками и телом
        HttpEntity<byte[]> requestEntity = createHttpEntity(request, body);

        // Маршрутизируем запрос и возвращаем ответ как есть
        return proxyService.routeRequest(path, method, requestEntity);
    }

    @GetMapping("/health")
    public ResponseEntity<ProxyService.HealthStatus> health() {
        ProxyService.HealthStatus healthStatus = proxyService.checkServicesHealth();
        return ResponseEntity.ok(healthStatus);
    }

    @GetMapping("/config")
    public ResponseEntity<String> config() {
        // Эндпоинт для проверки конфигурации
        String configInfo = String.format(
                "{\"gradual_migration\": %b, \"movies_migration_percent\": %d}",
                proxyConfig.isGradualMigration(),
                proxyConfig.getMoviesMigrationPercent()
        );
        return ResponseEntity.ok(configInfo);
    }

    private String getRequestPath(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        String requestUri = request.getRequestURI();

        if (contextPath != null && !contextPath.isEmpty()) {
            return requestUri.substring(contextPath.length());
        }
        return requestUri;
    }

    private HttpEntity<byte[]> createHttpEntity(HttpServletRequest request, byte[] body) {
        // Копируем заголовки из оригинального запроса
        HttpHeaders headers = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            headers.add(headerName, headerValue);
        }

        return new HttpEntity<>(body, headers);
    }
}