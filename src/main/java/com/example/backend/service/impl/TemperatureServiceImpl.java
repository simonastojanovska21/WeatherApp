package com.example.backend.service.impl;

import com.example.backend.model.Measurement;
import com.example.backend.model.Temperature;
import com.example.backend.model.enumerations.PeriodOfDay;
import com.example.backend.model.enumerations.TemperatureType;
import com.example.backend.model.exceptions.MeasurementNotFoundException;
import com.example.backend.repository.MeasurementRepository;
import com.example.backend.repository.TemperatureRepository;
import com.example.backend.service.TemperatureService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TemperatureServiceImpl implements TemperatureService {

    private final TemperatureRepository temperatureRepository;
    private final MeasurementRepository measurementRepository;

    @Override
    public Optional<Temperature> addNewTemperatureForMeasurement(Long measurementId, TemperatureType temperatureType, PeriodOfDay periodOfDay, double value) {
        Measurement measurement = this.measurementRepository.findById(measurementId).orElseThrow(MeasurementNotFoundException::new);
        Temperature temperature = new Temperature(temperatureType,periodOfDay,value,measurement);
        return Optional.of(this.temperatureRepository.save(temperature));
    }

    @Override
    public List<Temperature> getAllTemperaturesForMeasurement(Long measurementId) {
        Measurement measurement = this.measurementRepository.findById(measurementId).orElseThrow(MeasurementNotFoundException::new);
        return this.temperatureRepository.findAllByTemperatureForMeasurement(measurement);
    }

    @Override
    public Optional<Temperature> getCurrentTemperatureForMeasurement(Long measurementId) {
        List<Temperature> temperatures = this.getAllTemperaturesForMeasurement(measurementId);

        return Optional.empty();
    }
}
