package com.example.AirQuality.EventDriven;

import com.example.AirQuality.DTO.AirQualityAlertDTO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AirQualityEventPublisher {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void publishAirQualityNotificationEvent(AirQualityAlertDTO alertDTO) {
        try {
            String alertJson = objectMapper.writeValueAsString(alertDTO);
            amqpTemplate.convertAndSend("air-quality-exchange", "airquality.alert.high", alertJson);
            System.out.println("Published event: " + alertDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
