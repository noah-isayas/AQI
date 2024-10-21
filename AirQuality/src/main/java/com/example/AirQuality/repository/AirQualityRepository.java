package com.example.AirQuality.repository;

import com.example.AirQuality.model.AirQuality;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirQualityRepository extends JpaRepository<AirQuality, Long> {
}
