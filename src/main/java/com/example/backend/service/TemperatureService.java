package com.example.backend.service;

import com.example.backend.model.Temperature;
import com.example.backend.model.enumerations.PeriodOfDay;
import com.example.backend.model.enumerations.TemperatureType;

import java.util.List;
import java.util.Optional;

public interface TemperatureService {
    Optional<Temperature> addNewTemperatureForMeasurement(Long measurementId, TemperatureType temperatureType,
                                                          PeriodOfDay periodOfDay, double value);
    List<Temperature> getAllTemperaturesForMeasurement(Long measurementId);
    Optional<Temperature> getCurrentTemperatureForMeasurement(Long measurementId);
}
