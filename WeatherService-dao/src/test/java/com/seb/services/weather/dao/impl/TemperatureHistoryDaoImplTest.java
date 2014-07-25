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
}