package com.example.backend.service;

import com.example.backend.model.City;

import java.util.List;
import java.util.Optional;

public interface CityService {

    City addNewCity(String name, double latitude,double longitude);
    City getDetailsAboutCity(Long cityId);
    City getDetailsAboutCityByName(String cityName);
    List<String> getAllCitiesNames();
    List<Long> getAllCitiesIds();
}
