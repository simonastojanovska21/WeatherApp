package com.example.backend.service.impl;

import com.example.backend.model.City;
import com.example.backend.model.Measurement;
import com.example.backend.model.dto.ResultDTO;
import com.example.backend.model.enumerations.PeriodOfDay;
import com.example.backend.model.enumerations.TemperatureType;
import com.example.backend.service.CityService;
import com.example.backend.service.MeasurementService;
import com.example.backend.service.OpenWeatherService;
import com.example.backend.service.TemperatureService;
import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OpenWeatherServiceImpl implements OpenWeatherService {

    private final MeasurementService measurementService;
    private final TemperatureService temperatureService;
    private final CityService cityService;
    private final String apiKey = "30300a57465cfde74331f1316c72f505";

    @Override
    public void makeHttpRequestToOpenWeatherApiForCity(Long cityId) throws IOException, InterruptedException {
        City city = this.cityService.getDetailsAboutCity(cityId);
        String openWeatherURL = "https://api.openweathermap.org/data/2.5/onecall?lat=" + city.getLatitude() +
                "&lon=" + city.getLongitude() + "&exclude=current,minutely,hourly,alerts&units=metric" +
                "&appid=" + apiKey +"";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(openWeatherURL)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject jsonObject = new JSONObject(response.body());
        JSONArray jsonArray = jsonObject.getJSONArray("daily");
        this.parseJsonArray(jsonArray,cityId);
    }

    @Override
    public void parseJsonArray(JSONArray jsonArray, Long cityId) {
        if(!jsonArray.isEmpty()){
            for(int i=0;i<jsonArray.length();i++){
                JSONObject object = jsonArray.getJSONObject(i);
                this.parseJsonDailyForecastObject(object,cityId);
            }
        }
        else {
            throw new RuntimeException();
        }
    }

    @Override
    public void parseJsonDailyForecastObject(JSONObject object, Long cityId) {
        Instant date = Instant.ofEpochSecond(object.getLong("dt"));
        JSONObject temp = object.getJSONObject("temp");
        JSONObject feels_like = object.getJSONObject("feels_like");
        double minTemperature = temp.getDouble("min");
        double maxTemperature = temp.getDouble("max");
        JSONObject weatherJson = object.getJSONArray("weather").getJSONObject(0);
        String weather =  weatherJson.getString("main");
        String weatherDescription = weatherJson.getString("description");
        Long measurementId = measurementService.addNewMeasurement(date,cityId,minTemperature,maxTemperature,
                weather, weatherDescription);
        this.parseJsonTemperatureObject(temp,feels_like,measurementId);
    }

    @Override
    public void parseJsonTemperatureObject(JSONObject temp, JSONObject feels_like, Long measurementId) {
        temperatureService.addNewTemperatureForMeasurement(measurementId, TemperatureType.DAY_TEMPERATURE, PeriodOfDay.DAY,temp.getDouble("day"));
        temperatureService.addNewTemperatureForMeasurement(measurementId, TemperatureType.DAY_TEMPERATURE, PeriodOfDay.NIGHT,temp.getDouble("night"));
        temperatureService.addNewTemperatureForMeasurement(measurementId, TemperatureType.DAY_TEMPERATURE, PeriodOfDay.EVENING,temp.getDouble("eve"));
        temperatureService.addNewTemperatureForMeasurement(measurementId, TemperatureType.DAY_TEMPERATURE, PeriodOfDay.MORNING,temp.getDouble("morn"));

        temperatureService.addNewTemperatureForMeasurement(measurementId, TemperatureType.FEELS_LIKE_TEMPERATURE, PeriodOfDay.DAY,feels_like.getDouble("day"));
        temperatureService.addNewTemperatureForMeasurement(measurementId, TemperatureType.FEELS_LIKE_TEMPERATURE, PeriodOfDay.NIGHT,feels_like.getDouble("night"));
        temperatureService.addNewTemperatureForMeasurement(measurementId, TemperatureType.FEELS_LIKE_TEMPERATURE, PeriodOfDay.EVENING,feels_like.getDouble("eve"));
        temperatureService.addNewTemperatureForMeasurement(measurementId, TemperatureType.FEELS_LIKE_TEMPERATURE, PeriodOfDay.MORNING,feels_like.getDouble("morn"));

    }

    @Override
    public List<ResultDTO> getResults() {
        return this.measurementService.getAllMeasurements().stream().map(measurement ->
                new ResultDTO(measurement.getMeasurementForCity().getName(),measurement.getDate(),measurement.getMaxTemperature()))
                .collect(Collectors.toList());

    }

}
