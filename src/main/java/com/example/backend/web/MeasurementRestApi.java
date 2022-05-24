package com.example.backend.web;

import com.example.backend.model.Measurement;
import com.example.backend.service.MeasurementService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@AllArgsConstructor
@RequestMapping("/api/measurement")
public class MeasurementRestApi {

    private final MeasurementService measurementService;

    @GetMapping("/all")
    public List<Measurement> getAllMeasurements(){
        return this.measurementService.getAllMeasurements();
    }

    @GetMapping("/city/{city}")
    public List<Measurement> getAllMeasurementsForCity(@PathVariable String city){
        return this.measurementService.getAllMeasurementsForCity(city);
    }

    @GetMapping("/city/{city}/temperature/{temperature}")
    public List<Measurement> getAllMeasurementsForCityWithMaxTemperature(@PathVariable String city, @PathVariable double temperature){
        return this.measurementService.getAllMeasurementsForCityWithMaxTemperature(city,temperature);
    }

    @GetMapping("/rain/{city}")
    public List<Measurement> getAllMeasurementsForCityWithRainyDays(@PathVariable String city){
        return this.measurementService.getAllMeasurementsForCityWithRainyDays(city);
    }
}
