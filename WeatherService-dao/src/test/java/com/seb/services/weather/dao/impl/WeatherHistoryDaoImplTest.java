package com.seb.services.weather.dao.impl;

import com.seb.services.weather.dao.CityDao;
import com.seb.services.weather.dao.WeatherHistoryDao;
import com.seb.services.weather.domain.enums.Province;
import com.seb.services.weather.domain.enums.Region;
import com.seb.services.weather.domain.enums.WeatherType;
import com.seb.services.weather.domain.orm.City;
import com.seb.services.weather.domain.orm.WeatherHistory;
import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-application-context.xml")
@TransactionConfiguration(transactionManager = "transactionManager")
@Transactional
public class WeatherHistoryDaoImplTest {
    @Autowired
    private WeatherHistoryDao weatherHistoryDao;

    @Autowired
    private CityDao cityDao;

    @Test
    public void testCrud() {
        City city = new City();
        city.setName("TestCity2014");
        city.setPopulation(50000000);
        city.setProvince(Province.NONE);
        city.setRegion(Region.WALLONIA);
        cityDao.save(city);
        City testCity2014 = cityDao.findByPrimaryKey("TestCity2014");

        assertNotNull(testCity2014);

        WeatherHistory weatherHistory = new WeatherHistory();
        weatherHistory.setName("TestCity2014");
        weatherHistory.setCity(city);
        final LocalDateTime now = LocalDateTime.now();
        weatherHistory.setDate(now);
        weatherHistory.setWeather(WeatherType.SUNNY);

        Integer id = weatherHistoryDao.save(weatherHistory);

        weatherHistory = weatherHistoryDao.findByPrimaryKey(id);

        assertNotNull(weatherHistory);
        assertEquals("TestCity2014", weatherHistory.getName());
        assertEquals(id.intValue(), weatherHistory.getId());
        assertEquals(testCity2014, weatherHistory.getCity());
        assertEquals(now, weatherHistory.getDate());
        assertEquals(WeatherType.SUNNY, weatherHistory.getWeather());

        weatherHistory.setWeather(WeatherType.CLOUDY);
        weatherHistoryDao.update(weatherHistory);

        weatherHistory = weatherHistoryDao.findByPrimaryKey(id);

        assertNotNull(weatherHistory);
        assertEquals("TestCity2014", weatherHistory.getName());
        assertEquals(id.intValue(), weatherHistory.getId());
        assertEquals(testCity2014, weatherHistory.getCity());
        assertEquals(now, weatherHistory.getDate());
        assertEquals(WeatherType.CLOUDY, weatherHistory.getWeather());

        weatherHistoryDao.delete(weatherHistory);

        weatherHistory = weatherHistoryDao.findByPrimaryKey(id);

        assertNull(weatherHistory);
    }

    @Test
    public void testFindAll() {
        List<WeatherHistory> weatherHistories = weatherHistoryDao.findAll();

        assertEquals(0, weatherHistories.size());

        City city = new City();
        city.setName("TestCity2014");
        city.setPopulation(50000000);
        city.setProvince(Province.NONE);
        city.setRegion(Region.WALLONIA);
        cityDao.save(city);
        City testCity2014 = cityDao.findByPrimaryKey("TestCity2014");

        assertNotNull(testCity2014);

        WeatherHistory weatherHistory1 = new WeatherHistory();
        weatherHistory1.setName("TestCity2014");
        weatherHistory1.setCity(city);
        final LocalDateTime now1 = LocalDateTime.now();
        weatherHistory1.setDate(now1);
        weatherHistory1.setWeather(WeatherType.SUNNY);

        Integer id1 = weatherHistoryDao.save(weatherHistory1);

        WeatherHistory weatherHistory2 = new WeatherHistory();
        weatherHistory2.setName("TestCity2014");
        weatherHistory2.setCity(city);
        final LocalDateTime now2 = LocalDateTime.now();
        weatherHistory2.setDate(now2);
        weatherHistory2.setWeather(WeatherType.CLOUDY);

        Integer id2 = weatherHistoryDao.save(weatherHistory2);

        weatherHistories = weatherHistoryDao.findAll();

        assertEquals(2, weatherHistories.size());

        weatherHistory1 = weatherHistories.get(0);

        assertNotNull(weatherHistory1);
        assertEquals("TestCity2014", weatherHistory1.getName());
        assertEquals(id1.intValue(), weatherHistory1.getId());
        assertEquals(testCity2014, weatherHistory1.getCity());
        assertEquals(now1, weatherHistory1.getDate());
        assertEquals(WeatherType.SUNNY, weatherHistory1.getWeather());

        weatherHistory2 = weatherHistories.get(1);

        assertNotNull(weatherHistory2);
        assertEquals("TestCity2014", weatherHistory2.getName());
        assertEquals(id2.intValue(), weatherHistory2.getId());
        assertEquals(testCity2014, weatherHistory2.getCity());
        assertEquals(now2, weatherHistory2.getDate());
        assertEquals(WeatherType.CLOUDY, weatherHistory2.getWeather());
    }
}