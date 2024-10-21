package com.example.AirQuality.controller;

import com.example.AirQuality.service.AirQualityApiClient;
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
    public Mono<String> getAirQuality(@RequestParam double lat, @RequestParam double lon) {
        return airQualityApiClient.getAirQuality(lat, lon);
    }
}

