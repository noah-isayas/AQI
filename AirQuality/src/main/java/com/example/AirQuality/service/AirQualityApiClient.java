package com.example.AirQuality.service;

import com.example.AirQuality.DTO.AirQualityAlertDTO;
import com.example.AirQuality.EventDriven.AirQualityEventPublisher;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
public class AirQualityApiClient {

    private final WebClient webClient;
    private final AirQualityEventPublisher eventPublisher;

    @Value("${api.key}")
    private String apiKey;

    public AirQualityApiClient(WebClient.Builder webClientBuilder, AirQualityEventPublisher eventPublisher) {
        this.webClient = webClientBuilder.baseUrl("https://api.openweathermap.org/data/2.5/air_pollution").build();
        this.eventPublisher = eventPublisher;
    }

    public Mono<String> getAirQuality(double latitude, double longitude) {
        String uri = String.format("?lat=%s&lon=%s&appid=%s", latitude, longitude, apiKey);

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(response -> {
                    try {
                        JsonNode root = new ObjectMapper().readTree(response);
                        int aqi = root.path("list").get(0).path("main").path("aqi").asInt();

                        // If AQI is higher than 3, create the DTO and publish the event
                        if (aqi > 3) {
                            AirQualityAlertDTO alertDTO = new AirQualityAlertDTO(
                                    "Air quality is poor", latitude, longitude, aqi);
                            eventPublisher.publishAirQualityNotificationEvent(alertDTO);  // Delegate publishing
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                })
                .onErrorResume(WebClientResponseException.class, e -> {
                    return Mono.error(new RuntimeException("Error fetching air quality data: " + e.getMessage()));
                });
    }
}

