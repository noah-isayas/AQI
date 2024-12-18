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

        return getLocationNameByCoordinates(latitude, longitude)
                .flatMap(location -> webClient.get()
                        .uri(uri)
                        .retrieve()
                        .bodyToMono(String.class)
                        .doOnNext(response -> {
                            try {
                                JsonNode root = new ObjectMapper().readTree(response);
                                JsonNode main = root.path("list").get(0).path("main");
                                JsonNode components = root.path("list").get(0).path("components");
                                int aqi = main.path("aqi").asInt();

                                // If AQI is higher than 1, create the DTO and publish the event
                                if (aqi > 1) {
                                    AirQualityAlertDTO alertDTO = new AirQualityAlertDTO(
                                            "Air quality is poor",
                                            location,
                                            latitude,
                                            longitude,
                                            aqi,
                                            components.path("co").asDouble(),
                                            components.path("no").asDouble(),
                                            components.path("no2").asDouble(),
                                            components.path("o3").asDouble(),
                                            components.path("so2").asDouble(),
                                            components.path("pm2_5").asDouble(),
                                            components.path("pm10").asDouble(),
                                            components.path("nh3").asDouble()
                                    );
                                    eventPublisher.publishAirQualityNotificationEvent(alertDTO);  // Delegate publishing
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        })
                        .onErrorResume(WebClientResponseException.class, e -> {
                            return Mono.error(new RuntimeException("Error fetching air quality data: " + e.getMessage()));
                        })
                );
    }

    public Mono<String> getLocationNameByCoordinates(double latitude, double longitude) {
        String uri = String.format("http://api.openweathermap.org/geo/1.0/reverse?lat=%s&lon=%s&limit=1&appid=%s", latitude, longitude, apiKey);

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> {
                    try {
                        JsonNode root = new ObjectMapper().readTree(response).get(0);
                        return root.path("name").asText();
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to parse response", e);
                    }
                });
    }

    public Mono<double[]> getCoordinatesByLocationName(String location) {
        String uri = String.format("http://api.openweathermap.org/geo/1.0/direct?q=%s&limit=1&appid=%s", location, apiKey);

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> {
                    try {
                        JsonNode root = new ObjectMapper().readTree(response).get(0);
                        double lat = root.path("lat").asDouble();
                        double lon = root.path("lon").asDouble();
                        return new double[]{lat, lon};
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to parse response", e);
                    }
                });
    }
    public Mono<String> getHistoricalAirQuality(double latitude, double longitude) {
        long end = System.currentTimeMillis() / 1000; // Current time in Unix timestamp
        long start = end - (5 * 24 * 60 * 60); // 5 days ago

        String uri = String.format(
                "http://api.openweathermap.org/data/2.5/air_pollution/history?lat=%s&lon=%s&start=%d&end=%d&appid=%s",
                latitude, longitude, start, end, apiKey);

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(response -> System.out.println("Fetched historical data: " + response))
                .onErrorResume(WebClientResponseException.class, e -> {
                    return Mono.error(new RuntimeException("Error fetching historical air quality data: " + e.getMessage()));
                });
    }

}