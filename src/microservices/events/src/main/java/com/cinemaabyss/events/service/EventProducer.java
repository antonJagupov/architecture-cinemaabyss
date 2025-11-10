package com.cinemaabyss.events.service;

import com.cinemaabyss.events.model.UserEvent;
import com.cinemaabyss.events.model.PaymentEvent;
import com.cinemaabyss.events.model.MovieEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EventProducer {

    private static final Logger log = LoggerFactory.getLogger(EventProducer.class);

    private static final String USER_TOPIC = "user-events";
    private static final String PAYMENT_TOPIC = "payment-events";
    private static final String MOVIE_TOPIC = "movie-events";

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendUserEvent(UserEvent userEvent) {
        userEvent.setEventId(UUID.randomUUID().toString());
        userEvent.setTimestamp(LocalDateTime.now());
        userEvent.setType("USER_EVENT");
        
        sendMessage(USER_TOPIC, userEvent.getUserId().toString(), userEvent);
    }

    public void sendPaymentEvent(PaymentEvent paymentEvent) {
        paymentEvent.setEventId(UUID.randomUUID().toString());
        paymentEvent.setTimestamp(LocalDateTime.now());
        paymentEvent.setType("PAYMENT_EVENT");
        
        sendMessage(PAYMENT_TOPIC, paymentEvent.getUserId().toString(), paymentEvent);
    }

    public void sendMovieEvent(MovieEvent movieEvent) {
        movieEvent.setEventId(UUID.randomUUID().toString());
        movieEvent.setTimestamp(LocalDateTime.now());
        movieEvent.setType("MOVIE_EVENT");
        
        sendMessage(MOVIE_TOPIC, movieEvent.getMovieId().toString(), movieEvent);
    }

    private void sendMessage(String topic, String key, Object message) {
        ListenableFuture<SendResult<String, Object>> future = 
            kafkaTemplate.send(topic, key, message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onSuccess(SendResult<String, Object> result) {
                log.info("Sent message=[{}] with offset=[{}] to topic=[{}]", 
                    message, result.getRecordMetadata().offset(), topic);
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("Unable to send message=[{}] to topic=[{}] due to: {}", 
                    message, topic, ex.getMessage());
            }
        });
    }
}