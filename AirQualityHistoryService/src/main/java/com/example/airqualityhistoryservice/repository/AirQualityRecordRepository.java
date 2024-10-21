package com.example.airqualityhistoryservice.repository;

import com.example.airqualityhistoryservice.model.AirQualityRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AirQualityRecordRepository extends MongoRepository<AirQualityRecord, String> {
}
