package com.example.airqualitynotificationservice.configuration;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

    @Bean
    public FanoutExchange airQualityExchange() {
        return new FanoutExchange("air-quality-exchange", true, false);
    }

    @Bean
    public Queue alertQueue(@Value("${amqp.queue.alert.name}") final String queueName) {
        return new Queue(queueName, true);
    }

    @Bean
    public Binding alertBinding(Queue alertQueue, FanoutExchange airQualityExchange) {
        return BindingBuilder.bind(alertQueue).to(airQualityExchange);
    }
}
