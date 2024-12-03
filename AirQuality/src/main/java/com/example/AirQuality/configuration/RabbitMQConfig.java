package com.example.AirQuality.configuration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

@Configuration
public class RabbitMQConfig {

    // Exchanges
    @Bean
    public FanoutExchange airQualityExchange(
            @Value("${amqp.exchange.name}") final String exchangeName) {
        return ExchangeBuilder.fanoutExchange(exchangeName).durable(true).build();
    }

    // Queues
    @Bean
    public Queue notificationQueue(@Value("${amqp.queue.notification.name}") final String queueName) {
        return QueueBuilder.durable(queueName).build();
    }

    @Bean
    public Queue historyQueue(@Value("${amqp.queue.history.name}") final String queueName) {
        return QueueBuilder.durable(queueName).build();
    }

    // Bindings
    @Bean
    public Binding notificationBinding(final Queue notificationQueue, final FanoutExchange airQualityExchange) {
        return BindingBuilder.bind(notificationQueue).to(airQualityExchange);
    }

    @Bean
    public Binding historyBinding(final Queue historyQueue, final FanoutExchange airQualityExchange) {
        return BindingBuilder.bind(historyQueue).to(airQualityExchange);
    }

    // Message converter
    @Bean
    public MessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        final MappingJackson2MessageConverter jsonConverter = new MappingJackson2MessageConverter();
        jsonConverter.getObjectMapper().registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES));
        factory.setMessageConverter(jsonConverter);
        return factory;
    }

    @Bean
    public RabbitListenerConfigurer rabbitListenerConfigurer(
            final MessageHandlerMethodFactory messageHandlerMethodFactory) {
        return (C) -> C.setMessageHandlerMethodFactory(messageHandlerMethodFactory);
    }
}
