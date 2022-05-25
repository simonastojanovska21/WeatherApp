package com.example.backend.service.impl;

import com.example.backend.model.City;
import com.example.backend.model.exceptions.CityNotFoundException;
import com.example.backend.repository.CityRepository;
import com.example.backend.service.CityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    //Method used for adding new city/record in the database. Input parameters are name of the city, latitude and longitude
    //as double
    @Override
    public City addNewCity(String name, double latitude, double longitude) {
        City city = new City(name,latitude,longitude);
        return this.cityRepository.save(city);
    }

    //Method used for getting details for the city. As input we specify the id of the city and later on we check
    //if there is a record with the specified id - if not an exception is thrown else the object is returned
    @Override
    public City getDetailsAboutCity(Long cityId) {
        return this.cityRepository.findById(cityId).orElseThrow(CityNotFoundException::new);
    }

    //Same as before, the only difference is that we specify the name of the city as input
    @Override
    public City getDetailsAboutCityByName(String cityName) {
        return this.cityRepository.findCityByNameEquals(cityName);
    }

    //Methods that gets all the cities in tha database and maps the object to their name. Finally a list of city
    //names is returned.
    @Override
    public List<String> getAllCitiesNames() {
        return this.cityRepository.findAll().stream().map(City::getName).collect(Collectors.toList());
    }

    //Methods that gets all the cities in tha database and maps the object to their id. Finally a list of city
    //ids is returned.
    @Override
    public List<Long> getAllCitiesIds() {
        return this.cityRepository.findAll().stream().map(City::getId).collect(Collectors.toList());
    }
}
