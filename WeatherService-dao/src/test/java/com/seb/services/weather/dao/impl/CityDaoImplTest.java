package com.seb.services.weather.dao.impl;

import com.seb.services.weather.dao.CityDao;
import com.seb.services.weather.domain.enums.Province;
import com.seb.services.weather.domain.enums.Region;
import com.seb.services.weather.domain.enums.WeatherType;
import com.seb.services.weather.domain.orm.City;
import com.seb.services.weather.domain.orm.TemperatureHistory;
import com.seb.services.weather.domain.orm.WeatherHistory;
import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-application-context.xml")
@TransactionConfiguration(transactionManager = "transactionManager")
@Transactional
public class CityDaoImplTest {
    @Autowired
    private CityDao cityDao;

    @Test
    public void testCrud() {
        City testCity2014 = cityDao.findByPrimaryKey("TestCity2014");

        assertNull(testCity2014);

        City city = new City();
        city.setName("TestCity2014");
        city.setPopulation(50000000);
        city.setProvince(Province.NONE);
        city.setRegion(Region.WALLONIA);

        WeatherHistory weatherHistory1 = new WeatherHistory();
        weatherHistory1.setName("TestCity2014");
        weatherHistory1.setCity(city);
        weatherHistory1.setDate(LocalDateTime.now());
        weatherHistory1.setWeather(WeatherType.CLOUDY);

        WeatherHistory weatherHistory2 = new WeatherHistory();
        weatherHistory2.setName("TestCity2014");
        weatherHistory2.setCity(city);
        weatherHistory2.setDate(LocalDateTime.now());
        weatherHistory2.setWeather(WeatherType.CLOUDY);

        List<WeatherHistory> weatherHistoryList = new ArrayList<>();
        weatherHistoryList.add(weatherHistory1);
        weatherHistoryList.add(weatherHistory2);

        city.setWeatherHistory(weatherHistoryList);

        TemperatureHistory temperatureHistory = new TemperatureHistory();
        temperatureHistory.setName("TestCity2014");
        temperatureHistory.setCity(city);
        temperatureHistory.setDate(LocalDateTime.now());
        temperatureHistory.setTemperature(37);

        List<TemperatureHistory> temperatureHistoryList = new ArrayList<>();
        temperatureHistoryList.add(temperatureHistory);

        city.setTemperatureHistory(temperatureHistoryList);
        String cityId = cityDao.save(city);

        testCity2014 = cityDao.findByPrimaryKey(cityId);

        assertNotNull(testCity2014);
        assertEquals("TestCity2014", testCity2014.getName());
        assertEquals("TestCity2014", cityId);
        assertEquals(50000000, testCity2014.getPopulation());
        assertEquals(Province.NONE, testCity2014.getProvince());
        assertEquals(Region.WALLONIA, testCity2014.getRegion());

        testCity2014.setPopulation(1000);
        cityDao.update(testCity2014);

        testCity2014 = cityDao.findByPrimaryKey("TestCity2014");

        assertNotNull(testCity2014);
        assertEquals("TestCity2014", testCity2014.getName());
        assertEquals(1000, testCity2014.getPopulation());
        assertEquals(Province.NONE, testCity2014.getProvince());
        assertEquals(Region.WALLONIA, testCity2014.getRegion());

        cityDao.delete(testCity2014);

        testCity2014 = cityDao.findByPrimaryKey("TestCity2014");

        assertNull(testCity2014);
    }
}