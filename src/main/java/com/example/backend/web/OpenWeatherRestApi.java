package com.example.backend.web;

import com.example.backend.model.City;
import com.example.backend.model.Measurement;
import com.example.backend.model.dto.ResultDTO;
import com.example.backend.service.CityService;
import com.example.backend.service.MeasurementService;
import com.example.backend.service.OpenWeatherService;
import com.example.backend.service.impl.OpenWeatherServiceImpl;
import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@AllArgsConstructor
@RequestMapping("/api")
public class OpenWeatherRestApi {

    private final OpenWeatherService openWeatherService;
    private final CityService cityService;
    private final MeasurementService measurementService;


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
