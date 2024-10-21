package com.example.airqualitynotificationservice.configuration;


import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {
    @Bean
    public Queue alertQueue(@Value("${amqp.queue.alert.name}") final String queueName) {
        return new Queue(queueName, true);
    }
}
