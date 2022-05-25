package com.example.backend.web;

import com.example.backend.model.dto.ResultDTO;
import com.example.backend.service.CityService;
import com.example.backend.service.ForecastService;
import com.example.backend.service.OpenWeatherService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:63343")
@AllArgsConstructor
@RequestMapping("/api")
public class OpenWeatherRestApi {

    private final OpenWeatherService openWeatherService;
    private final CityService cityService;


    @GetMapping("/populateDatabaseWithMeasurements")
    public void populateDatabaseWithMeasurements(){
        this.cityService.getAllCitiesIds().forEach(cityId -> {
            try {
                this.openWeatherService.makeHttpRequestToOpenWeatherApiForCity(cityId);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    @GetMapping("/results")
    public List<ResultDTO> getResults(){
        return this.openWeatherService.getResults();
    }

}
