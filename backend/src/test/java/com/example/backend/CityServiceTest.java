package com.example.backend;

import com.example.backend.model.City;
import com.example.backend.repository.CityRepository;
import com.example.backend.service.CityService;
import com.example.backend.service.impl.CityServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@RunWith(MockitoJUnitRunner.class)
public class CityServiceTest {

    @Mock
    private CityRepository cityRepository;

    private CityService cityService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        City city = new City("Skopje",41.9981,21.4254);
        Mockito.when(this.cityRepository.save(Mockito.any(City.class))).thenReturn(city);
        Mockito.when(this.cityRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(city));
        Mockito.when(this.cityRepository.findCityByNameEquals(Mockito.anyString())).thenReturn(city);
        Mockito.when(this.cityRepository.findAll()).thenReturn(List.of(city,city,city));
        this.cityService = Mockito.spy(new CityServiceImpl(cityRepository));
    }

    @Test
    public void testSuccessfulInsert(){
        City city = this.cityService.addNewCity("Skopje",41.9981,21.4254);
        Assert.assertEquals("Skopje",city.getName());
    }

    @Test
    public void testGetDetails(){
        City city = this.cityService.getDetailsAboutCity(1L);
        Assert.assertEquals("Skopje",city.getName());
    }

    @Test
    public void testGetDetailsAboutCityByName(){
        City city = this.cityService.getDetailsAboutCityByName("Skopje");
        Assert.assertEquals("Skopje",city.getName());
    }

    @Test
    public void testGetAllCitiesNames(){
        List<String> list = this.cityService.getAllCitiesNames();
        Assert.assertArrayEquals(list.toArray(), new String[]{"Skopje", "Skopje", "Skopje"});
    }

    @Test
    public void testGetAllCitiesIds(){
        List<Long> list = this.cityService.getAllCitiesIds();
        Assert.assertEquals(list.size(),3);
    }


}
