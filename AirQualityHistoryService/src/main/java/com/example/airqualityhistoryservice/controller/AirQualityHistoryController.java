package com.example.airqualityhistoryservice.controller;

import com.example.airqualityhistoryservice.model.AirQualityRecord;
import com.example.airqualityhistoryservice.service.AirQualityHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/history")
public class AirQualityHistoryController {
    @Autowired
    private AirQualityHistoryService historyService;

    @PostMapping
    public AirQualityRecord addRecord(@RequestBody AirQualityRecord record) {
        return historyService.saveRecord(record);
    }

    @GetMapping("/{id}")
    public Optional<AirQualityRecord> getRecordById(@PathVariable String id) {
        return historyService.findRecordById(id);
    }
    @GetMapping
    public List<AirQualityRecord> getAllRecords(){
        return historyService.findAllRecords();
    }
}
