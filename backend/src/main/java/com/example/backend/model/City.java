package com.example.backend.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//Model or entity that represents the city for which we will get weather information. For each city we keep it's ID that
//is automatically generated, it's name and geo coordinated - latitude and longitude.
@Entity
@Data
@NoArgsConstructor
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String name;

    private double latitude;

    private double longitude;

    public City(String name, double latitude,double longitude)
    {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
