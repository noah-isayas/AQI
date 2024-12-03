package com.example.airqualityhistoryservice.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Declare the Fanout Exchange bean
    @Bean
    public FanoutExchange airQualityExchange() {
        return new FanoutExchange("air-quality-exchange", true, false);
    }

    // Declare the Queue bean
    @Bean
    public Queue historyQueue() {
        return new Queue("air-quality-history-queue", true);
    }

    // Bind the Queue to the Fanout Exchange
    @Bean
    public Binding historyBinding(Queue historyQueue, FanoutExchange airQualityExchange) {
        return BindingBuilder.bind(historyQueue).to(airQualityExchange);
    }
}
