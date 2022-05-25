package com.example.backend.model.dto;

import lombok.AllArgsConstructor;

import java.time.Instant;

//As specified in the requirement we had to return only the name of the city, date and the maximum temperature, so I
//am using this data transfer object
@AllArgsConstructor
public class ResultDTO {
    public String cityName;
    public Instant date;
    public double max_temperature;
}
