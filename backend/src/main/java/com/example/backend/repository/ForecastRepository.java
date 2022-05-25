package com.example.backend.repository;

import com.example.backend.model.City;
import com.example.backend.model.Forecast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//Repository used to send/receive data from the forecast table. I have defined method that can find the forecast for the specified
//city, a method that can find the forecast for the city and forecast that has temperature greater than specified and
//finally a method that can find the forecast for the specified city and specified weather conditions
@Repository
public interface ForecastRepository extends JpaRepository<Forecast, Long> {
    List<Forecast> findAllByForecastForCityOrderByDate(City measurementForCity);
    List<Forecast> findAllByForecastForCityAndMaxTemperatureGreaterThanEqualOrderByDate (City measurementForCity, double maxTemperature);
    List<Forecast> findAllByForecastForCityAndWeatherEqualsOrderByDate(City city, String weather);
}
