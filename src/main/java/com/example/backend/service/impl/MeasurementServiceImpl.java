package com.example.backend.service.impl;

import com.example.backend.model.City;
import com.example.backend.model.Measurement;
import com.example.backend.model.exceptions.CityNotFoundException;
import com.example.backend.model.exceptions.MeasurementNotFoundException;
import com.example.backend.repository.CityRepository;
import com.example.backend.repository.MeasurementRepository;
import com.example.backend.service.MeasurementService;
import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MeasurementServiceImpl implements MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final CityRepository cityRepository;

    @Override
    public Long addNewMeasurement(Instant date, Long cityId, double minTemperature, double maxTemperature, String weather, String weatherDescription) {
        City city = this.cityRepository.findById(cityId).orElseThrow(CityNotFoundException::new);
        Measurement measurement = new Measurement(date,city,minTemperature,maxTemperature, weather,weatherDescription);
        return this.measurementRepository.save(measurement).getId();
    }

    @Override
    public Optional<Measurement> getDetailsAboutMeasurement(Long measurementId) {
        return this.measurementRepository.findById(measurementId);
    }

    @Override
    public List<Measurement> getAllMeasurements() {
        return this.measurementRepository.findAll();
    }

    @Override
    public List<Measurement> getAllMeasurementsForCity(String cityName) {
        City city = this.cityRepository.findCityByNameEquals(cityName);
        return this.measurementRepository.findAllByMeasurementForCity(city);
    }

    @Override
    public List<Measurement> getAllMeasurementsForCityWithMaxTemperature(String cityName, double maxTemperature) {
        City city = this.cityRepository.findCityByNameEquals(cityName);
        return this.measurementRepository.findAllByMeasurementForCityAndMaxTemperatureGreaterThanEqual(city,maxTemperature);
    }

    @Override
    public List<Measurement> getAllMeasurementsForCityWithRainyDays(String cityName) {
        City city = this.cityRepository.findCityByNameEquals(cityName);
        return this.measurementRepository.findAllByMeasurementForCityAndWeatherEquals(city, "Rain");
    }


}
