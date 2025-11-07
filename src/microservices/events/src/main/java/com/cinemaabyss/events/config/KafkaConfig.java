package com.cinemaabyss.events.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        String kafkaBrokers = "kafka:9092";
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBrokers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProps.put(ProducerConfig.ACKS_CONFIG, "all");
        configProps.put(ProducerConfig.RETRIES_CONFIG, 6);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public NewTopic userTopic() {
        return TopicBuilder.name("user-events")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic paymentTopic() {
        return TopicBuilder.name("payment-events")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic movieTopic() {
        return TopicBuilder.name("movie-events")
                .partitions(3)
                .replicas(1)
                .build();
    }

}
