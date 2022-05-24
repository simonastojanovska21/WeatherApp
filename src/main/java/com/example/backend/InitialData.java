package com.example.backend;

import com.example.backend.model.City;
import com.example.backend.service.CityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@AllArgsConstructor
public class InitialData {

    private final CityService cityService;

    @PostConstruct
    public void initData(){
        if(this.cityService.getAllCitiesNames().isEmpty()){
            this.cityService.addNewCity("Skopje",41.9981,21.4254);
            this.cityService.addNewCity("Prilep",41.3441,21.5528);
            this.cityService.addNewCity("Strumica",41.4378,22.6427);
        }
    }
}
