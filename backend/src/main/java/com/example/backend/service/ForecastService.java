package com.example.backend.service;

import com.example.backend.model.Forecast;
import com.example.backend.model.dto.ForecastDTO;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface ForecastService {

    Long addNewForecast(Instant date, Long cityId, double temperature, double minTemperature, double maxTemperature, String weather, String weatherDescription, String icon);
    List<Forecast> getAllForecasts();
    List<ForecastDTO> getAllForecastsForCity(String cityName);
    List<ForecastDTO> getAllForecastsForCityWithMaxTemperature(String cityName, double maxTemperature);
    List<ForecastDTO> getAllForecastsForCityWithRainyDays(String cityName);


}
