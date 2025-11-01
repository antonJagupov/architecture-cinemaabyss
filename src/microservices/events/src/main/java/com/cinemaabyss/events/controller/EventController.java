package com.cinemaabyss.events.controller;

import com.cinemaabyss.events.model.UserEvent;
import com.cinemaabyss.events.model.PaymentEvent;
import com.cinemaabyss.events.model.MovieEvent;
import com.cinemaabyss.events.service.EventProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private static final Logger log = LoggerFactory.getLogger(EventProducer.class);

    @Autowired
    private EventProducer eventProducer;

    @PostMapping("/user")
    public ResponseEntity<String> createUserEvent(@RequestBody UserEvent userEvent) {
        log.info("Creating user event: {}", userEvent);
        eventProducer.sendUserEvent(userEvent);
        return ResponseEntity.ok("User event created and sent to Kafka");
    }

    @PostMapping("/payment")
    public ResponseEntity<String> createPaymentEvent(@RequestBody PaymentEvent paymentEvent) {
        log.info("Creating payment event: {}", paymentEvent);
        eventProducer.sendPaymentEvent(paymentEvent);
        return ResponseEntity.ok("Payment event created and sent to Kafka");
    }

    @PostMapping("/movie")
    public ResponseEntity<String> createMovieEvent(@RequestBody MovieEvent movieEvent) {
        log.info("Creating movie event: {}", movieEvent);
        eventProducer.sendMovieEvent(movieEvent);
        return ResponseEntity.ok("Movie event created and sent to Kafka");
    }

    @PostMapping("/generate-sample")
    public ResponseEntity<String> generateSampleEvents() {
        // Generate sample user event
        UserEvent userEvent = new UserEvent();
        userEvent.setUserId(1L);
        userEvent.setUsername("john_doe");
        userEvent.setEmail("john@example.com");
        userEvent.setAction("CREATED");
        eventProducer.sendUserEvent(userEvent);

        // Generate sample payment event
        PaymentEvent paymentEvent = new PaymentEvent();
        paymentEvent.setPaymentId(1001L);
        paymentEvent.setUserId(1L);
        paymentEvent.setAmount(new BigDecimal("25.50"));
        paymentEvent.setCurrency("USD");
        paymentEvent.setStatus("COMPLETED");
        eventProducer.sendPaymentEvent(paymentEvent);

        // Generate sample movie event
        MovieEvent movieEvent = new MovieEvent();
        movieEvent.setMovieId(201L);
        movieEvent.setTitle("Inception");
        movieEvent.setGenre("Sci-Fi");
        movieEvent.setDuration(148);
        movieEvent.setRating(8.8);
        movieEvent.setActors(Arrays.asList("Leonardo DiCaprio", "Marion Cotillard"));
        movieEvent.setAction("CREATED");
        eventProducer.sendMovieEvent(movieEvent);

        return ResponseEntity.ok("Sample events generated and sent to Kafka");
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Events Service is running");
    }
}