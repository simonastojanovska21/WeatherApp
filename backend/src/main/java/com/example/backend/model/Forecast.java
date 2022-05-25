package com.example.backend.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

//Model - entity that represents the measurement read from the API. For each forecast we have it's ID, date and time
//of the forecasted data, the city that the measurement is for, daily temperature, max and min temperature for the day
//string that represents the weather (rain,cloud,sunny), a string that represents the weather description and
//finally an icon that will be used for the image when displaying the result.
@Entity
@Data
@NoArgsConstructor
public class Forecast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private Instant date;

    @ManyToOne
    private City forecastForCity;

    private double temperature;

    private double minTemperature;

    private double maxTemperature;

    private String weather;

    private String weatherDescription;

    private String icon;

    public Forecast(Instant date, City city, double temperature, double minTemperature, double maxTemperature, String weather, String weatherDescription, String icon)
    {
        this.date = date;
        this.forecastForCity = city;
        this.temperature = temperature;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.weather = weather;
        this.weatherDescription = weatherDescription;
        this.icon = icon;
    }
}
