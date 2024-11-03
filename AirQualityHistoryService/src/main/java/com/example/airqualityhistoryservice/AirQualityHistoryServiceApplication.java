package com.example.airqualityhistoryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.example.airqualityhistoryservice.repository")
public class AirQualityHistoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AirQualityHistoryServiceApplication.class, args);
    }

}
