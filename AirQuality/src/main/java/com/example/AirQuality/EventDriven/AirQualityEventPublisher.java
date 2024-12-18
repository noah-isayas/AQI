package com.example.AirQuality.EventDriven;

import com.example.AirQuality.DTO.AirQualityAlertDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AirQualityEventPublisher {

    private final AmqpTemplate amqpTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public AirQualityEventPublisher(AmqpTemplate amqpTemplate, ObjectMapper objectMapper) {
        this.amqpTemplate = amqpTemplate;
        this.objectMapper = objectMapper;
    }

    public void publishAirQualityNotificationEvent(AirQualityAlertDTO alertDTO) {
        try {
            String alertJson = objectMapper.writeValueAsString(alertDTO);
            amqpTemplate.convertAndSend("air-quality-exchange", "", alertJson); // Routing key is empty
            System.out.println("Published event: " + alertDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}