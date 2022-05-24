package com.example.backend.model.dto;

import lombok.AllArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
public class ResultDTO {
    public String cityName;
    public Instant date;
    public double max_temperature;
}
