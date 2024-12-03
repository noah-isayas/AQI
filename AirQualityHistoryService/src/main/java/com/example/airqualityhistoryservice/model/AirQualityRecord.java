package com.example.airqualityhistoryservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Map;

@Data
@Document(collection = "air_quality_records")
//@JsonIgnoreProperties(ignoreUnknown = true)
public class AirQualityRecord {
    @Id
    private String id;
    private double latitude;
    private double longitude;
    private Instant timestamp;
    private int aqi;
    private Map<String, Double> pollutants; //Co, N02

    private String message;
}
