package com.example.backend;


import com.example.backend.model.City;
import com.example.backend.model.Forecast;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;


@SpringBootTest
public class OpenWeatherApiTest {

    @Test
    @Order(1)
    public void testAddForecastFromJSON(){
        String json = "{\n" +
                "dt: 1653472800,\n" +
                "sunrise: 1653447941,\n" +
                "sunset: 1653501424,\n" +
                "moonrise: 1653439680,\n" +
                "moonset: 1653483420,\n" +
                "moon_phase: 0.84,\n" +
                "temp: {\n" +
                "day: 26.16,\n" +
                "min: 14.82,\n" +
                "max: 31.59,\n" +
                "night: 19.92,\n" +
                "eve: 30.13,\n" +
                "morn: 18.02\n" +
                "},\n" +
                "feels_like: {\n" +
                "day: 26.16,\n" +
                "night: 19.77,\n" +
                "eve: 30.93,\n" +
                "morn: 17.97\n" +
                "},\n" +
                "pressure: 1016,\n" +
                "humidity: 51,\n" +
                "dew_point: 15.24,\n" +
                "wind_speed: 2.6,\n" +
                "wind_deg: 122,\n" +
                "wind_gust: 3.73,\n" +
                "weather: [\n" +
                "{\n" +
                "id: 801,\n" +
                "main: \"Clouds\",\n" +
                "description: \"few clouds\",\n" +
                "icon: \"02d\"\n" +
                "}\n" +
                "],\n" +
                "clouds: 18,\n" +
                "pop: 0.18,\n" +
                "uvi: 8.37\n" +
                "}";
        JSONObject object = new JSONObject(json);

        Instant date = Instant.ofEpochSecond(object.getLong("dt"));
        JSONObject temp = object.getJSONObject("temp");
        double temperature = temp.getDouble("day");
        double minTemperature = temp.getDouble("min");
        double maxTemperature = temp.getDouble("max");
        JSONObject weatherJson = object.getJSONArray("weather").getJSONObject(0);
        String weather =  weatherJson.getString("main");
        String weatherDescription = weatherJson.getString("description");
        String icon = weatherJson.getString("icon");

        Forecast forecast = new Forecast(date,new City(),temperature,minTemperature,maxTemperature,
                weather.toLowerCase(), weatherDescription, icon);

        Assert.assertEquals(26.16,forecast.getTemperature(),0.001);
        Assert.assertEquals(14.82,forecast.getMinTemperature(),0.001);
        Assert.assertEquals(31.59,forecast.getMaxTemperature(),0.001);
        Assert.assertEquals("clouds",forecast.getWeather());
        Assert.assertEquals("few clouds",forecast.getWeatherDescription());
        Assert.assertEquals("02d",forecast.getIcon());

    }

    //I am testing the rest api of the application, so it should be started to be able to make request
    //In the data.txt I copied the result I got from the OpenWeatherApi at the time the testing was conducted
    //so if you test the application in different time you might get different results i.e the test will fail
    @Test
    @Order(2)
    public void testPopulateDatabaseApi() throws IOException, InterruptedException {
        String url = "http://localhost:8080/api/populateDatabaseWithMeasurements";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assert.assertEquals(response.statusCode(),200);
    }





}
