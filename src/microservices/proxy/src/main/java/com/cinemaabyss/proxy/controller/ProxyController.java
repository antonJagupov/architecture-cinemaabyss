package com.cinemaabyss.proxy.controller;

import com.cinemaabyss.proxy.dto.ProxyResponse;
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
    
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, 
                             RequestMethod.DELETE, RequestMethod.PATCH})
    public ResponseEntity<ProxyResponse> proxyRequest(
            HttpServletRequest request,
            @RequestBody(required = false) String body) {
        
        String path = getRequestPath(request);
        HttpMethod method = HttpMethod.valueOf(request.getMethod());
        
        logger.info("Proxying request: {} {}", method, path);
        
        // Создаем HttpEntity с заголовками и телом
        HttpEntity<?> requestEntity = createHttpEntity(request, body);
        
        // Маршрутизируем запрос
        ProxyResponse proxyResponse = proxyService.routeRequest(path, method, requestEntity);
        
        // Возвращаем ответ
        if (proxyResponse.getError() != null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(proxyResponse);
        } else {
            return ResponseEntity.ok(proxyResponse);
        }
    }
    
    @GetMapping("/health")
    public ResponseEntity<ProxyService.HealthStatus> health() {
        ProxyService.HealthStatus healthStatus = proxyService.checkServicesHealth();
        return ResponseEntity.ok(healthStatus);
    }
    
    @GetMapping("/config")
    public ResponseEntity<String> config() {
        // Эндпоинт для проверки конфигурации (можно удалить в production)
        return ResponseEntity.ok("Proxy service is running with gradual migration");
    }
    
    private String getRequestPath(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        String requestUri = request.getRequestURI();
        
        if (contextPath != null && !contextPath.isEmpty()) {
            return requestUri.substring(contextPath.length());
        }
        return requestUri;
    }
    
    private HttpEntity<?> createHttpEntity(HttpServletRequest request, String body) {
        // Копируем заголовки из оригинального запроса
        HttpHeaders headers = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();
        
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            headers.add(headerName, headerValue);
        }
        
        // Устанавливаем Content-Type, если не установлен
        if (body != null && !body.isEmpty() && headers.getContentType() == null) {
            headers.setContentType(MediaType.APPLICATION_JSON);
        }
        
        return new HttpEntity<>(body, headers);
    }
}