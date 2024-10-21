package com.example.airqualitynotificationservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AirQualityAlertDTO {
    private String message;
    private double latitude;
    private double longitude;
    private int aqi;

}
