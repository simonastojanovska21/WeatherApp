package com.example.backend.model;

import com.example.backend.model.enumerations.PeriodOfDay;
import com.example.backend.model.enumerations.TemperatureType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Temperature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Enumerated(EnumType.STRING)
    private TemperatureType temperatureType;

    @Enumerated(EnumType.STRING)
    private PeriodOfDay periodOfDay;

    private double value;

    @ManyToOne
    private Measurement temperatureForMeasurement;

    public Temperature(TemperatureType temperatureType, PeriodOfDay periodOfDay, double value, Measurement temperatureForMeasurement){
        this.temperatureType = temperatureType;
        this.periodOfDay = periodOfDay;
        this.value = value;
        this.temperatureForMeasurement = temperatureForMeasurement;
    }
}
