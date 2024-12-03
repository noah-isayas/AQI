package com.example.airqualitynotificationservice.EventDriven;

import com.example.airqualitynotificationservice.DTO.AirQualityAlertDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class AirQualityEventHandler {

    private final ObjectMapper objectMapper;

    public AirQualityEventHandler() {
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Register module for LocalDateTime support
    }

    @RabbitListener(queues = "${amqp.queue.alert.name}")
    public void receiveAlert(String message) {
        try {
            Thread.sleep(5000);  // Simulating processing delay
            AirQualityAlertDTO alertDTO = objectMapper.readValue(message, AirQualityAlertDTO.class);
            System.out.println("Received alert: " + alertDTO);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error processing alert message: " + message);
        }
    }

}
