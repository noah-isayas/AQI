package com.example.airqualityhistoryservice.EventDriven;

import com.example.airqualityhistoryservice.model.AirQualityRecord;
import com.example.airqualityhistoryservice.service.AirQualityHistoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AirQualityEventHandler {

    @Autowired
    private AirQualityHistoryService historyService;

    private final ObjectMapper objectMapper;

    public AirQualityEventHandler() {
        this.objectMapper = new ObjectMapper();
        // Register JavaTimeModule to handle LocalDateTime
        objectMapper.registerModule(new JavaTimeModule());
    }

    @RabbitListener(queues = "${amqp.queue.alert.name}")
    public void handleAirQualityEvent(String message) {
        System.out.println("Received message: " + message);
        try {
            AirQualityRecord record = objectMapper.readValue(message, AirQualityRecord.class);
            System.out.println("Deserialized record: " + record);
            historyService.saveRecord(record);
            System.out.println("Successfully saved record to MongoDB: " + record);
        } catch (Exception e) {
            System.err.println("Error processing the air quality event: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
