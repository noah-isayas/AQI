package com.example.airqualityhistoryservice.service;

import com.example.airqualityhistoryservice.model.AirQualityRecord;
import com.example.airqualityhistoryservice.repository.AirQualityRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AirQualityHistoryService {
    @Autowired
    private AirQualityRecordRepository repository;

    public AirQualityRecord saveRecord(AirQualityRecord record) {
        return repository.save(record);
    }
    public Optional<AirQualityRecord> findRecordById(String id){
        return repository.findById(id);
    }
    public List<AirQualityRecord> findAllRecords(){
        return repository.findAll();
    }
}
