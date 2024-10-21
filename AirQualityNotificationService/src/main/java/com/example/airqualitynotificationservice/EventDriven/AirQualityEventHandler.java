package com.example.airqualitynotificationservice.EventDriven;

import com.example.airqualitynotificationservice.DTO.AirQualityAlertDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class AirQualityEventHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = "${amqp.queue.alert.name}")
    public void receiveAlert(String message) {
        try {
            Thread.sleep(5000);  // Simulating processing delay
            // Convert the received JSON string back to AirQualityAlertDTO
            AirQualityAlertDTO alertDTO = objectMapper.readValue(message, AirQualityAlertDTO.class);
            System.out.println("Received alert: " + alertDTO);
            // Further processing like storing the alert or sending notifications
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

