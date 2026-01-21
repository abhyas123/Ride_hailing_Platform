package com.program.config;

import com.program.kafka.producer.LocationUpdateProducer.LocationUpdateEvent;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    // ===============================
    // PRODUCER FACTORY
    // ===============================
    @Bean
    public ProducerFactory<String, LocationUpdateEvent> producerFactory() {

        Map<String, Object> props = new HashMap<>();

        props.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092"
        );

        props.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class
        );

        props.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class
        );

        return new DefaultKafkaProducerFactory<>(props);
    }

    // ===============================
    // KAFKA TEMPLATE
    // ===============================
    @Bean
    public KafkaTemplate<String, LocationUpdateEvent> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
