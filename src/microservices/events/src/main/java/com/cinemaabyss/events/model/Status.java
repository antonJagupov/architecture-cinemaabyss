package com.cinemaabyss.events.model;

public class Status {

    public Status(String status) {
        this.status = status;
    }

    public Status() {
    }

    private String status = "success";

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
