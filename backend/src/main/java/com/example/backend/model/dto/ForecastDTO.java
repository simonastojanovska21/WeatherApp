package com.example.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

//Data transfer object used to return data to the front end application. In this object we have only the necessary data
//required to be rendered.
@Data
@AllArgsConstructor
public class ForecastDTO {
    private String cityName;
    private Instant date;
    private double temperature;
    private double minTemperature;
    private double maxTemperature;
    private String weatherDescription;
    private String icon;
}
