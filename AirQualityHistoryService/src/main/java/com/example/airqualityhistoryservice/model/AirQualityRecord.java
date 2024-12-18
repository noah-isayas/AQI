package com.example.airqualityhistoryservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "airQualityRecords")
public class AirQualityRecord {
    @Id
    private String id;
    private String city;
    private double latitude;
    private double longitude;
    private LocalDateTime timestamp;
    private int aqi;
    private Pollutants pollutants;
    private String message;

    @Data
    public static class Pollutants {
        private double co;
        private double no;
        private double no2;
        private double o3;
        private double so2;
        private double pm2_5;
        private double pm10;
        private double nh3;
    }
}