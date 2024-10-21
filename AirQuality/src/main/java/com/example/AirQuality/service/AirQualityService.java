package com.example.AirQuality.service;

import com.example.AirQuality.model.AirQuality;
import com.example.AirQuality.repository.AirQualityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AirQualityService {
    @Autowired
    private AirQualityRepository airQualityRepository;

    public List<AirQuality> getAllAirQualityData() {
        return airQualityRepository.findAll();
    }

    public Optional<AirQuality> getAirQualityById(Long id) {
        return airQualityRepository.findById(id);
    }

    public AirQuality saveAirQuality(AirQuality airQuality) {
        return airQualityRepository.save(airQuality);
    }
    public void deleteAirQuality(Long id) {
        airQualityRepository.deleteById(id);
    }
}
