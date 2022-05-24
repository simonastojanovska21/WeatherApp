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

    @Override
    public Optional<City> addNewCity(String name, double latitude, double longitude) {
        City city = new City(name,latitude,longitude);
        return Optional.of(this.cityRepository.save(city));
    }

    @Override
    public City getDetailsAboutCity(Long cityId) {
        return this.cityRepository.findById(cityId).orElseThrow(CityNotFoundException::new);
    }

    @Override
    public City getDetailsAboutCityByName(String cityName) {
        return this.cityRepository.findCityByNameEquals(cityName);
    }

    @Override
    public boolean deleteCity(Long cityId) {
        City city = this.cityRepository.findById(cityId).orElseThrow(CityNotFoundException::new);
        this.cityRepository.delete(city);
        return this.cityRepository.findById(cityId).isEmpty();
    }

    @Override
    public List<String> getAllCitiesNames() {
        return this.cityRepository.findAll().stream().map(City::getName).collect(Collectors.toList());
    }

    @Override
    public List<Long> getAllCitiesIds() {
        return this.cityRepository.findAll().stream().map(City::getId).collect(Collectors.toList());
    }
}
