package com.example.airqualityhistoryservice;

import com.example.airqualityhistoryservice.service.AirQualityHistoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.example.airqualityhistoryservice.repository")
public class AirQualityHistoryServiceApplication implements CommandLineRunner {

    private final AirQualityHistoryService airQualityHistoryService;

    public AirQualityHistoryServiceApplication(AirQualityHistoryService airQualityHistoryService) {
        this.airQualityHistoryService = airQualityHistoryService;
    }

    public static void main(String[] args) {
        SpringApplication.run(AirQualityHistoryServiceApplication.class, args);
    }

    @Override
    public void run(String... args) {
        // Call the testSave method to test MongoDB connectivity
        //airQualityHistoryService.testSave();
    }
}
