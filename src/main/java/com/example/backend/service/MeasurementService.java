package com.example.backend.service;

import com.example.backend.model.Measurement;
import org.json.JSONArray;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface MeasurementService {

    Long addNewMeasurement(Instant date, Long cityId, double minTemperature, double maxTemperature, String weather, String weatherDescription);
    Optional<Measurement> getDetailsAboutMeasurement(Long measurementId);
    List<Measurement> getAllMeasurements();
    List<Measurement> getAllMeasurementsForCity(String cityName);
    List<Measurement> getAllMeasurementsForCityWithMaxTemperature(String cityName, double maxTemperature);
    List<Measurement> getAllMeasurementsForCityWithRainyDays(String cityName);


}
