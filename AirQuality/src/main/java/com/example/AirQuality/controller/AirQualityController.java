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
    public Mono<AirQualityAlertDTO> getAirQuality(@RequestParam(required = false) Double lat,
                                                  @RequestParam(required = false) Double lon,
                                                  @RequestParam(required = false) String location) {
        if (location != null) {
            return airQualityApiClient.getCoordinatesByLocationName(location)
                    .flatMap(coords -> airQualityApiClient.getAirQuality(coords[0], coords[1])
                            .map(response -> {
                                try {
                                    JsonNode root = new ObjectMapper().readTree(response);
                                    JsonNode main = root.path("list").get(0).path("main");
                                    JsonNode components = root.path("list").get(0).path("components");

                                    AirQualityAlertDTO dto = new AirQualityAlertDTO();
                                    dto.setLatitude(coords[0]);
                                    dto.setLongitude(coords[1]);
                                    dto.setAqi(main.path("aqi").asInt());
                                    dto.setMessage("Air quality data retrieved successfully");
                                    dto.setCo(components.path("co").asDouble());
                                    dto.setNo(components.path("no").asDouble());
                                    dto.setNo2(components.path("no2").asDouble());
                                    dto.setO3(components.path("o3").asDouble());
                                    dto.setSo2(components.path("so2").asDouble());
                                    dto.setPm2_5(components.path("pm2_5").asDouble());
                                    dto.setPm10(components.path("pm10").asDouble());
                                    dto.setNh3(components.path("nh3").asDouble());

                                    return dto;
                                } catch (Exception e) {
                                    throw new RuntimeException("Failed to parse response", e);
                                }
                            }));
        } else if (lat != null && lon != null) {
            return airQualityApiClient.getAirQuality(lat, lon)
                    .map(response -> {
                        try {
                            JsonNode root = new ObjectMapper().readTree(response);
                            JsonNode main = root.path("list").get(0).path("main");
                            JsonNode components = root.path("list").get(0).path("components");

                            AirQualityAlertDTO dto = new AirQualityAlertDTO();
                            dto.setLatitude(lat);
                            dto.setLongitude(lon);
                            dto.setAqi(main.path("aqi").asInt());
                            dto.setMessage("Air quality data retrieved successfully");
                            dto.setCo(components.path("co").asDouble());
                            dto.setNo(components.path("no").asDouble());
                            dto.setNo2(components.path("no2").asDouble());
                            dto.setO3(components.path("o3").asDouble());
                            dto.setSo2(components.path("so2").asDouble());
                            dto.setPm2_5(components.path("pm2_5").asDouble());
                            dto.setPm10(components.path("pm10").asDouble());
                            dto.setNh3(components.path("nh3").asDouble());

                            return dto;
                        } catch (Exception e) {
                            throw new RuntimeException("Failed to parse response", e);
                        }
                    });
        } else {
            return Mono.error(new RuntimeException("Either coordinates or location name must be provided"));
        }
    }
}