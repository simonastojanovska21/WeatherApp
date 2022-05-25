package com.example.backend.web;

import com.example.backend.model.City;
import com.example.backend.service.CityService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:63343")
@AllArgsConstructor
@RequestMapping("/api/city")
public class CityRestApi {

    private CityService cityService;

    @GetMapping("/all")
    public List<String> getCitiesNames(){
        return this.cityService.getAllCitiesNames();
    }
}
