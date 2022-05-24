package com.example.backend.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private Instant date;

    @ManyToOne
    private City measurementForCity;

    private double minTemperature;

    private double maxTemperature;

    private String weather;

    private String weatherDescription;

    public Measurement(Instant date, City city, double minTemperature,double maxTemperature, String weather, String weatherDescription)
    {
        this.date = date;
        this.measurementForCity = city;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.weather = weather;
        this.weatherDescription = weatherDescription;
    }
}
