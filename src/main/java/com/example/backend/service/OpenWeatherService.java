package com.example.backend.service;

import com.example.backend.model.dto.ResultDTO;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;


public interface OpenWeatherService {

    void makeHttpRequestToOpenWeatherApiForCity(Long cityId) throws IOException, InterruptedException;
    void parseJsonArray(JSONArray jsonArray, Long cityId);
    void parseJsonDailyForecastObject(JSONObject object,Long cityId);
    void parseJsonTemperatureObject(JSONObject temp, JSONObject feels_like, Long measurementId);
    List<ResultDTO> getResults();
}
