package com.example.AirQuality.controller;

import com.example.AirQuality.DTO.AirQualityAlertDTO;
import com.example.AirQuality.service.AirQualityApiClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class AirQualityController {

    private final AirQualityApiClient airQualityApiClient;

    public AirQualityController(AirQualityApiClient airQualityApiClient) {
        this.airQualityApiClient = airQualityApiClient;
    }

    @GetMapping("/air-quality")
    public Mono<AirQualityAlertDTO> getAirQuality(@RequestParam double lat, @RequestParam double lon) {
        return airQualityApiClient.getAirQuality(lat, lon)
                .map(response -> {
                    try {
                        JsonNode root = new ObjectMapper().readTree(response);
                        JsonNode main = root.path("list").get(0).path("main");

                        AirQualityAlertDTO dto = new AirQualityAlertDTO();
                        dto.setLatitude(lat);
                        dto.setLongitude(lon);
                        dto.setAqi(main.path("aqi").asInt());
                        dto.setMessage("Air quality data retrieved successfully");

                        return dto;
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to parse response", e);
                    }
                });
    }
}