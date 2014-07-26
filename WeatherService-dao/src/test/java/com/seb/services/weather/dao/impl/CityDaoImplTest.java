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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        Set<WeatherHistory> weatherHistoryList = new HashSet<>();
        weatherHistoryList.add(weatherHistory1);
        weatherHistoryList.add(weatherHistory2);

        city.setWeatherHistory(weatherHistoryList);

        TemperatureHistory temperatureHistory = new TemperatureHistory();
        temperatureHistory.setName("TestCity2014");
        temperatureHistory.setCity(city);
        temperatureHistory.setDate(LocalDateTime.now());
        temperatureHistory.setTemperature(37);

        Set<TemperatureHistory> temperatureHistoryList = new HashSet<>();
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

    @Test
    public void testFindAll() {
        List<City> cities = cityDao.findAll();

        assertEquals(0, cities.size());

        City city1 = new City();
        city1.setName("TestCity2014");
        city1.setPopulation(50000000);
        city1.setProvince(Province.NONE);
        city1.setRegion(Region.WALLONIA);

        WeatherHistory weatherHistory1 = new WeatherHistory();
        weatherHistory1.setName("TestCity2014");
        weatherHistory1.setCity(city1);
        weatherHistory1.setDate(LocalDateTime.now());
        weatherHistory1.setWeather(WeatherType.CLOUDY);

        WeatherHistory weatherHistory2 = new WeatherHistory();
        weatherHistory2.setName("TestCity2014");
        weatherHistory2.setCity(city1);
        weatherHistory2.setDate(LocalDateTime.now());
        weatherHistory2.setWeather(WeatherType.CLOUDY);

        Set<WeatherHistory> weatherHistoryList = new HashSet<>();
        weatherHistoryList.add(weatherHistory1);
        weatherHistoryList.add(weatherHistory2);
        city1.setWeatherHistory(weatherHistoryList);

        TemperatureHistory temperatureHistory = new TemperatureHistory();
        temperatureHistory.setName("TestCity2014");
        temperatureHistory.setCity(city1);
        temperatureHistory.setDate(LocalDateTime.now());
        temperatureHistory.setTemperature(37);

        Set<TemperatureHistory> temperatureHistoryList = new HashSet<>();
        temperatureHistoryList.add(temperatureHistory);

        city1.setTemperatureHistory(temperatureHistoryList);
        String city1Id = cityDao.save(city1);

        City city2 = new City();
        city2.setName("TestCity2015");
        city2.setPopulation(127);
        city2.setProvince(Province.EAST_FLANDERS);
        city2.setRegion(Region.FLANDERS);

        city2.setWeatherHistory(weatherHistoryList);
        city2.setTemperatureHistory(temperatureHistoryList);

        String city2Id = cityDao.save(city2);

        cities = cityDao.findAll();

        assertEquals(2, cities.size());

        city1 = cities.get(0);

        assertNotNull(city1);
        assertEquals("TestCity2014", city1.getName());
        assertEquals("TestCity2014", city1Id);
        assertEquals(50000000, city1.getPopulation());
        assertEquals(Province.NONE, city1.getProvince());
        assertEquals(Region.WALLONIA, city1.getRegion());
        assertEquals(2, city1.getWeatherHistory().size());
        assertEquals(1, city1.getTemperatureHistory().size());

        city2 = cities.get(1);

        assertNotNull(city2);
        assertEquals("TestCity2015", city2.getName());
        assertEquals("TestCity2015", city2Id);
        assertEquals(127, city2.getPopulation());
        assertEquals(Province.EAST_FLANDERS, city2.getProvince());
        assertEquals(Region.FLANDERS, city2.getRegion());
        assertEquals(2, city2.getWeatherHistory().size());
        assertEquals(1, city2.getTemperatureHistory().size());
    }
}