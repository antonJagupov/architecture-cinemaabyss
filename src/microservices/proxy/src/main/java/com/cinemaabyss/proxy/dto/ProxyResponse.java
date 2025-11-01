package com.cinemaabyss.proxy.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProxyResponse {
    private Object data;
    private String source;
    private String error;
    
    public ProxyResponse() {}
    
    public ProxyResponse(Object data, String source) {
        this.data = data;
        this.source = source;
    }
    
    public ProxyResponse(String error) {
        this.error = error;
    }
    
    // Геттеры и сеттеры
    public Object getData() { return data; }
    public void setData(Object data) { this.data = data; }
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
}