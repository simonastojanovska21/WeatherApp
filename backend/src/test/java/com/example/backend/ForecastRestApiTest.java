package com.example.backend;

import org.json.JSONArray;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
//For the test to pass OpenWeatherApiTest should be run first to populate the database
@SpringBootTest
public class ForecastRestApiTest {
    @Test
    public void testAllForecastInformation() throws IOException, InterruptedException {
        String url = "http://localhost:8080/api/forecast/all";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assert.assertEquals(response.statusCode(),200);
        JSONArray array = new JSONArray(response.body());
        Assert.assertEquals(array.length(),24);
    }

    @Test
    public void testCityForecast() throws IOException, InterruptedException {
        String url = "http://localhost:8080/api/forecast/city/Skopje";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assert.assertEquals(response.statusCode(),200);
        JSONArray array = new JSONArray(response.body());
        Assert.assertEquals(array.length(),8);
    }

    @Test
    public void testCityAndTemperatureForecast() throws IOException, InterruptedException {
        String url = "http://localhost:8080/api/forecast/city/Skopje/temperature/30";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assert.assertEquals(response.statusCode(),200);
        JSONArray array = new JSONArray(response.body());
        Assert.assertEquals(array.length(),3);
    }

    @Test
    public void testCityAndRainForecast() throws IOException, InterruptedException {
        String url = "http://localhost:8080/api/forecast/rain/Skopje/";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assert.assertEquals(response.statusCode(),200);
        JSONArray array = new JSONArray(response.body());
        Assert.assertEquals(array.length(),5);
    }
}
