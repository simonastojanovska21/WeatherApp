package com.example.backend.web;

import com.example.backend.model.Forecast;
import com.example.backend.model.dto.ForecastDTO;
import com.example.backend.service.ForecastService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:63343")
@AllArgsConstructor
@RequestMapping("/api/forecast")
public class ForecastRestApi {

    private final ForecastService forecastService;

    @GetMapping("/all")
    public List<Forecast> getAllMeasurements(){
        return this.forecastService.getAllForecasts();
    }

    @GetMapping("/city/{city}")
    public List<ForecastDTO> getAllMeasurementsForCity(@PathVariable String city){
        return this.forecastService.getAllForecastsForCity(city);
    }

    @GetMapping("/city/{city}/temperature/{temperature}")
    public List<ForecastDTO> getAllMeasurementsForCityWithMaxTemperature(@PathVariable String city, @PathVariable double temperature){
        return this.forecastService.getAllForecastsForCityWithMaxTemperature(city,temperature);
    }

    @GetMapping("/rain/{city}")
    public List<ForecastDTO> getAllMeasurementsForCityWithRainyDays(@PathVariable String city){
        return this.forecastService.getAllForecastsForCityWithRainyDays(city);
    }
}
