package com.cinemaabyss.events.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

public class MovieEvent {
    private String eventId;
    private String type;
    @JsonProperty("movie_id")
    private Long movieId;
    private String title;
    private String genre;
    private Integer duration;
    private Double rating;
    private List<String> actors;
    private LocalDateTime timestamp;
    private String action; // CREATED, UPDATED, DELETED

    public MovieEvent(String eventId, String type, Long movieId, String title, String genre, Integer duration, Double rating, List<String> actors, LocalDateTime timestamp, String action) {
        this.eventId = eventId;
        this.type = type;
        this.movieId = movieId;
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.rating = rating;
        this.actors = actors;
        this.timestamp = timestamp;
        this.action = action;
    }

    public MovieEvent() {
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

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public List<String> getActors() {
        return actors;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
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
        return "MovieEvent{" +
                "eventId='" + eventId + '\'' +
                ", type='" + type + '\'' +
                ", movieId=" + movieId +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", duration=" + duration +
                ", rating=" + rating +
                ", actors=" + actors +
                ", timestamp=" + timestamp +
                ", action='" + action + '\'' +
                '}';
    }
}