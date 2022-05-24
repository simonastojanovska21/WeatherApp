package com.example.backend.repository;

import com.example.backend.model.City;
import com.example.backend.model.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Long> {
    List<Measurement> findAllByMeasurementForCity(City measurementForCity);
    List<Measurement> findAllByMeasurementForCityAndMaxTemperatureGreaterThanEqual(City measurementForCity, double maxTemperature);
    List<Measurement> findAllByMeasurementForCityAndWeatherEquals(City city, String weather);
}
