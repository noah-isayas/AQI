package com.example.airqualityhistoryservice.EventDriven;

import com.example.airqualityhistoryservice.model.AirQualityRecord;
import com.example.airqualityhistoryservice.service.AirQualityHistoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AirQualityEventHandler {
    @Autowired
    private AirQualityHistoryService historyService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = "${amqp.queue.alert.name}")
    public void handleAirQualityEvent(String message) {
        try {
            // This will deserialize the incoming message into AirQualityRecord
            AirQualityRecord record = objectMapper.readValue(message, AirQualityRecord.class);
            // Saves to MongoDb
            historyService.saveRecord(record);
            System.out.println("Stored air quality record" + record);
        }catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error processing the air quality event" + message);
        }
    }
}
