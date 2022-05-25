package com.example.backend.service.impl;

import com.example.backend.model.City;
import com.example.backend.model.Forecast;
import com.example.backend.model.dto.ForecastDTO;
import com.example.backend.model.exceptions.CityNotFoundException;
import com.example.backend.model.exceptions.ForecastNotFoundException;
import com.example.backend.repository.CityRepository;
import com.example.backend.repository.ForecastRepository;
import com.example.backend.service.ForecastService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ForecastServiceImpl implements ForecastService {

    private final ForecastRepository forecastRepository;
    private final CityRepository cityRepository;

    //Method used for adding new forecast in the database. We have data, cityId (used to get the city object stored in database),
    //the daily temperature, min and maximum temperature, weather and weather description and icon used to create a new object.
    //The created object is stored in the database and it's id is retured.
    @Override
    public Long addNewForecast(Instant date, Long cityId,double temperature, double minTemperature, double maxTemperature, String weather, String weatherDescription, String icon) {
        City city = this.cityRepository.findById(cityId).orElseThrow(CityNotFoundException::new);
        Forecast forecast = new Forecast(date,city,temperature,minTemperature,maxTemperature, weather,weatherDescription,icon);
        return this.forecastRepository.save(forecast).getId();
    }

    //Standard method for getting all recods in the database. List of objects is returned
    @Override
    public List<Forecast> getAllForecasts() {
        return this.forecastRepository.findAll();
    }

    //Method that as input parametar takes the name of the city we want all the forecasts in the database. First using
    //the name of the city the object is found and then all the the forecast from the reposity are fetched. Each of them
    //is then mapped into a ForecastDTO object and returned.
    @Override
    public List<ForecastDTO> getAllForecastsForCity(String cityName) {
        City city = this.cityRepository.findCityByNameEquals(cityName);
        return this.forecastRepository.findAllByForecastForCityOrderByDate(city).stream().map(measurement ->
                new ForecastDTO(cityName,measurement.getDate(),measurement.getTemperature(),measurement.getMinTemperature(),
                        measurement.getMaxTemperature(),measurement.getWeatherDescription(), measurement.getIcon()))
                .collect(Collectors.toList());
    }

    //Method that as input parametar takes the name of the city and the maximum temperature we want all the forecasts in
    // the database to have. First using the name of the city the object is found and then all the the forecast from the
    // reposity are fetched. Each of them is then mapped into a ForecastDTO object and returned.
    @Override
    public List<ForecastDTO> getAllForecastsForCityWithMaxTemperature(String cityName, double maxTemperature) {
        City city = this.cityRepository.findCityByNameEquals(cityName);
        return this.forecastRepository.findAllByForecastForCityAndMaxTemperatureGreaterThanEqualOrderByDate(city,maxTemperature).stream().map(measurement ->
                        new ForecastDTO(cityName,measurement.getDate(),measurement.getTemperature(),measurement.getMinTemperature(),
                                measurement.getMaxTemperature(),measurement.getWeatherDescription(), measurement.getIcon()))
                .collect(Collectors.toList());
    }

    //Method that filters the results and return only the rainy days
    @Override
    public List<ForecastDTO> getAllForecastsForCityWithRainyDays(String cityName) {
        City city = this.cityRepository.findCityByNameEquals(cityName);
        return this.forecastRepository.findAllByForecastForCityAndWeatherEqualsOrderByDate(city, "rain").stream().map(measurement ->
                        new ForecastDTO(cityName,measurement.getDate(),measurement.getTemperature(),measurement.getMinTemperature(),
                                measurement.getMaxTemperature(),measurement.getWeatherDescription(), measurement.getIcon()))
                .collect(Collectors.toList());
    }


}
