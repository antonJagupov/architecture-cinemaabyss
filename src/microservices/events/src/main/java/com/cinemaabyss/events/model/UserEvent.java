package com.cinemaabyss.events.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class UserEvent {
    private String eventId;
    private String type;
    @JsonProperty("user_id")
    private Long userId;
    private String username;
    private String email;
    private LocalDateTime timestamp;
    private String action; // CREATED, UPDATED, DELETED

    public UserEvent(String eventId, String type, Long userId, String username, String email, LocalDateTime timestamp, String action) {
        this.eventId = eventId;
        this.type = type;
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.timestamp = timestamp;
        this.action = action;
    }

    public UserEvent() {
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "UserEvent{" +
                "eventId='" + eventId + '\'' +
                ", type='" + type + '\'' +
                ", userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", timestamp=" + timestamp +
                ", action='" + action + '\'' +
                '}';
    }
}