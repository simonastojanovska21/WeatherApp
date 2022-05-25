package com.example.backend.repository;

import com.example.backend.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
//Repository used to communicate with the database. Despite the already implemented crud operation I have defined
//a method used to find the city by it's name, not by the id.
@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    City findCityByNameEquals(String cityName);
}
