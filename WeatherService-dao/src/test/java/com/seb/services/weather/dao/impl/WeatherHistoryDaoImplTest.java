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
}