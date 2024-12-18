package com.example.airqualityhistoryservice.EventDriven;

import com.example.airqualityhistoryservice.model.AirQualityRecord;
import com.example.airqualityhistoryservice.service.AirQualityHistoryService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

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
            JsonNode root = objectMapper.readTree(message);

            AirQualityRecord record = new AirQualityRecord();
            record.setLatitude(root.path("latitude").asDouble());
            record.setLongitude(root.path("longitude").asDouble());
            record.setCity(root.path("city").asText());
            System.out.println("Extracted city name: " + record.getCity()); // Debug statement
            record.setAqi(root.path("aqi").asInt());
            record.setMessage(root.path("message").asText());

            AirQualityRecord.Pollutants pollutants = new AirQualityRecord.Pollutants();
            pollutants.setCo(root.path("co").asDouble());
            pollutants.setNo(root.path("no").asDouble());
            pollutants.setNo2(root.path("no2").asDouble());
            pollutants.setO3(root.path("o3").asDouble());
            pollutants.setSo2(root.path("so2").asDouble());
            pollutants.setPm2_5(root.path("pm2_5").asDouble());
            pollutants.setPm10(root.path("pm10").asDouble());
            pollutants.setNh3(root.path("nh3").asDouble());

            record.setPollutants(pollutants);
            record.setTimestamp(LocalDateTime.now());

            historyService.saveRecord(record);
            System.out.println("Successfully saved record to MongoDB: " + record);
        } catch (Exception e) {
            System.err.println("Error processing the air quality event: " + e.getMessage());
            e.printStackTrace();
        }
    }
}