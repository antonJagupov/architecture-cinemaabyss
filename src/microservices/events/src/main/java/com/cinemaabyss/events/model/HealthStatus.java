package com.cinemaabyss.events.model;

public class HealthStatus {

    public HealthStatus(boolean status) {
        this.status = status;
    }

    public HealthStatus() {
    }

    private boolean status = true;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
