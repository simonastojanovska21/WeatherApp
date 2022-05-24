package com.example.backend.service;

import com.example.backend.model.City;

import java.util.List;
import java.util.Optional;

public interface CityService {

    Optional<City> addNewCity(String name, double latitude,double longitude);
    City getDetailsAboutCity(Long cityId);
    City getDetailsAboutCityByName(String cityName);
    boolean deleteCity(Long cityId);
    List<String> getAllCitiesNames();
    List<Long> getAllCitiesIds();
}
