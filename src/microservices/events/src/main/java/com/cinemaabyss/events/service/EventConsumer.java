package com.cinemaabyss.events.service;

import com.cinemaabyss.events.model.UserEvent;
import com.cinemaabyss.events.model.PaymentEvent;
import com.cinemaabyss.events.model.MovieEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EventConsumer {

    private static final Logger log = LoggerFactory.getLogger(EventProducer.class);

    @KafkaListener(topics = "user-events", groupId = "events-group")
    public void consumeUserEvent(UserEvent userEvent) {
        log.info("=== CONSUMED USER EVENT ===");
        log.info("Event ID: {}", userEvent.getEventId());
        log.info("User ID: {}", userEvent.getUserId());
        log.info("Username: {}", userEvent.getUsername());
        log.info("Action: {}", userEvent.getAction());
        log.info("Timestamp: {}", userEvent.getTimestamp());
        log.info("===========================");
    }

    @KafkaListener(topics = "payment-events", groupId = "events-group")
    public void consumePaymentEvent(PaymentEvent paymentEvent) {
        log.info("=== CONSUMED PAYMENT EVENT ===");
        log.info("Event ID: {}", paymentEvent.getEventId());
        log.info("Payment ID: {}", paymentEvent.getPaymentId());
        log.info("User ID: {}", paymentEvent.getUserId());
        log.info("Amount: {} {}", paymentEvent.getAmount(), paymentEvent.getCurrency());
        log.info("Status: {}", paymentEvent.getStatus());
        log.info("Timestamp: {}", paymentEvent.getTimestamp());
        log.info("==============================");
    }

    @KafkaListener(topics = "movie-events", groupId = "events-group")
    public void consumeMovieEvent(MovieEvent movieEvent) {
        log.info("=== CONSUMED MOVIE EVENT ===");
        log.info("Event ID: {}", movieEvent.getEventId());
        log.info("Movie ID: {}", movieEvent.getMovieId());
        log.info("Title: {}", movieEvent.getTitle());
        log.info("Genre: {}", movieEvent.getGenre());
        log.info("Rating: {}", movieEvent.getRating());
        log.info("Action: {}", movieEvent.getAction());
        log.info("Timestamp: {}", movieEvent.getTimestamp());
        log.info("============================");
    }
}