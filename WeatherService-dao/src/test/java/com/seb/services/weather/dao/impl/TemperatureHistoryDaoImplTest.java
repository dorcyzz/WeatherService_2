package com.seb.services.weather.dao.impl;

import com.seb.services.weather.dao.CityDao;
import com.seb.services.weather.dao.TemperatureHistoryDao;
import com.seb.services.weather.domain.enums.Province;
import com.seb.services.weather.domain.enums.Region;
import com.seb.services.weather.domain.orm.City;
import com.seb.services.weather.domain.orm.TemperatureHistory;
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
public class TemperatureHistoryDaoImplTest {
    @Autowired
    private TemperatureHistoryDao temperatureHistoryDao;

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

        TemperatureHistory temperatureHistory = new TemperatureHistory();
        temperatureHistory.setName("TestCity2014");
        temperatureHistory.setCity(city);
        final LocalDateTime now = LocalDateTime.now();
        temperatureHistory.setDate(now);
        temperatureHistory.setTemperature(37);

        Integer id = temperatureHistoryDao.save(temperatureHistory);

        temperatureHistory = temperatureHistoryDao.findByPrimaryKey(id);

        assertNotNull(temperatureHistory);
        assertEquals("TestCity2014", temperatureHistory.getName());
        assertEquals(id.intValue(), temperatureHistory.getId());
        assertEquals(testCity2014, temperatureHistory.getCity());
        assertEquals(now, temperatureHistory.getDate());
        assertEquals(37, temperatureHistory.getTemperature());

        temperatureHistory.setTemperature(42);
        temperatureHistoryDao.update(temperatureHistory);

        temperatureHistory = temperatureHistoryDao.findByPrimaryKey(id);

        assertNotNull(temperatureHistory);
        assertEquals("TestCity2014", temperatureHistory.getName());
        assertEquals(id.intValue(), temperatureHistory.getId());
        assertEquals(testCity2014, temperatureHistory.getCity());
        assertEquals(now, temperatureHistory.getDate());
        assertEquals(42, temperatureHistory.getTemperature());


        temperatureHistoryDao.delete(temperatureHistory);

        temperatureHistory = temperatureHistoryDao.findByPrimaryKey(id);

        assertNull(temperatureHistory);
    }

    @Test
    public void testFindAll() {
        List<TemperatureHistory> temperatureHistories = temperatureHistoryDao.findAll();

        assertEquals(0, temperatureHistories.size());

        City city = new City();
        city.setName("TestCity2014");
        city.setPopulation(50000000);
        city.setProvince(Province.NONE);
        city.setRegion(Region.WALLONIA);
        cityDao.save(city);
        City testCity2014 = cityDao.findByPrimaryKey("TestCity2014");

        assertNotNull(testCity2014);

        TemperatureHistory weatherHistory1 = new TemperatureHistory();
        weatherHistory1.setName("TestCity2014");
        weatherHistory1.setCity(city);
        final LocalDateTime now1 = LocalDateTime.now();
        weatherHistory1.setDate(now1);
        weatherHistory1.setTemperature(42);

        Integer id1 = temperatureHistoryDao.save(weatherHistory1);

        TemperatureHistory weatherHistory2 = new TemperatureHistory();
        weatherHistory2.setName("TestCity2014");
        weatherHistory2.setCity(city);
        final LocalDateTime now2 = LocalDateTime.now();
        weatherHistory2.setDate(now2);
        weatherHistory2.setTemperature(40);

        Integer id2 = temperatureHistoryDao.save(weatherHistory2);

        temperatureHistories = temperatureHistoryDao.findAll();

        assertEquals(2, temperatureHistories.size());

        weatherHistory1 = temperatureHistories.get(0);

        assertNotNull(weatherHistory1);
        assertEquals("TestCity2014", weatherHistory1.getName());
        assertEquals(id1.intValue(), weatherHistory1.getId());
        assertEquals(testCity2014, weatherHistory1.getCity());
        assertEquals(now1, weatherHistory1.getDate());
        assertEquals(42, weatherHistory1.getTemperature());

        weatherHistory2 = temperatureHistories.get(1);

        assertNotNull(weatherHistory2);
        assertEquals("TestCity2014", weatherHistory2.getName());
        assertEquals(id2.intValue(), weatherHistory2.getId());
        assertEquals(testCity2014, weatherHistory2.getCity());
        assertEquals(now2, weatherHistory2.getDate());
        assertEquals(40, weatherHistory2.getTemperature());
    }
}