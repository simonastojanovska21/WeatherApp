package com.example.backend.service.impl;

import com.example.backend.model.City;
import com.example.backend.model.dto.ResultDTO;
import com.example.backend.service.CityService;
import com.example.backend.service.ForecastService;
import com.example.backend.service.OpenWeatherService;
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

    private final ForecastService forecastService;
    private final CityService cityService;
    private final String apiKey = "30300a57465cfde74331f1316c72f505";

    //Method used for sending HTTP request to the OpenWeatherApi with the longitude, latitude and my API key
    //I am creating a new HttpClient and building the request. After the request is sent the response object is received
    //and it has the json object retured from the api. The object is processed by getting the daily array that has the data
    //of interest and is later parsed.
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

    //Method for parsing JSONArray that in background calls method for parsing each element in the array
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

    //Main method for parsing that gets the json object that represents a forecast for a specific day about a specific city
    //The date, daily minimum and maximum temperature is extracted from the object along with the icon, weather and weather
    //description data. Finally a forecast object is created and inserted in the database.
    @Override
    public void parseJsonDailyForecastObject(JSONObject object, Long cityId) {
        Instant date = Instant.ofEpochSecond(object.getLong("dt"));
        JSONObject temp = object.getJSONObject("temp");
        double temperature = temp.getDouble("day");
        double minTemperature = temp.getDouble("min");
        double maxTemperature = temp.getDouble("max");
        JSONObject weatherJson = object.getJSONArray("weather").getJSONObject(0);
        String weather =  weatherJson.getString("main");
        String weatherDescription = weatherJson.getString("description");
        String icon = weatherJson.getString("icon");
        forecastService.addNewForecast(date,cityId,temperature,minTemperature,maxTemperature,
                weather.toLowerCase(), weatherDescription, icon);
    }


    @Override
    public List<ResultDTO> getResults() {
        return this.forecastService.getAllForecasts().stream().map(forecast ->
                new ResultDTO(forecast.getForecastForCity().getName(),forecast.getDate(),forecast.getMaxTemperature()))
                .collect(Collectors.toList());

    }

}
