/*package com.example.airqualitynotificationservice.service;

import com.example.airqualitynotificationservice.DTO.AirQualityAlertDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AirQualityNotificationService {

    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = "${amqp.queue.alert.name}")
    public void receiveAlert(String message) {
        try {
            Thread.sleep(5000);
            // Convert the received JSON string back to AirQualityAlertDTO
            AirQualityAlertDTO alertDTO = objectMapper.readValue(message, AirQualityAlertDTO.class);
            System.out.println("Received alert: " + alertDTO);
            // Further processing like storing the alert or sending notifications
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}*/
