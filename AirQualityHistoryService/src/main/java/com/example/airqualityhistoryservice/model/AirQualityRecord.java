package com.example.airqualityhistoryservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Document(collection = "air_quality_records")
public class AirQualityRecord {
    @Id
    private String id;
    private double latitude;
    private double longitude;
    private LocalDateTime timestamp;
    private int aqi;
    private Map<String, Double> pollutants; //Co, N02
}
