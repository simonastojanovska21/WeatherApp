package com.example.backend.repository;

import com.example.backend.model.Measurement;
import com.example.backend.model.Temperature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemperatureRepository extends JpaRepository<Temperature,Long> {
    List<Temperature> findAllByTemperatureForMeasurement(Measurement temperatureForMeasurement);
}
