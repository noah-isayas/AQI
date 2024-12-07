package com.example.airqualityhistoryservice.service;

import com.example.airqualityhistoryservice.model.AirQualityRecord;
import com.example.airqualityhistoryservice.repository.AirQualityRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
public class AirQualityHistoryService {
    @Autowired
    private AirQualityRecordRepository repository;

    public AirQualityRecord saveRecord(AirQualityRecord record) {
        // Convert Instant to LocalDateTime
        record.setTimestamp(LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()));
        return repository.save(record);
    }

    public Optional<AirQualityRecord> findRecordById(String id) {
        return repository.findById(id);
    }

    public List<AirQualityRecord> findAllRecords() {
        return repository.findAll();
    }

    public void testSave() {
        try {
            AirQualityRecord record = new AirQualityRecord();
            record.setLatitude(59.9127);
            record.setLongitude(10.7461);
            record.setTimestamp(LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()));
            record.setAqi(4);
            record.setMessage("Test message");

            AirQualityRecord.Pollutants pollutants = new AirQualityRecord.Pollutants();
            pollutants.setCo(0.5);
            pollutants.setNo2(0.3);
            pollutants.setPm2_5(0.6);
            record.setPollutants(pollutants);

            saveRecord(record);
            System.out.println("Test record saved: " + record);
        } catch (Exception e) {
            System.err.println("Error saving test record: " + e.getMessage());
            e.printStackTrace();
        }
    }
}