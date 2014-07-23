package com.seb.services.weather.dao;

import com.seb.services.weather.domain.enums.Province;
import com.seb.services.weather.domain.enums.Region;
import com.seb.services.weather.domain.orm.City;
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
public class CityDaoImplTest {
    @Autowired
    private CityDao cityDao;

    @Test
    public void testCrud() {
        City testCity2014 = cityDao.getCity("TestCity2014");

        assertNull(testCity2014);

        City city = new City();
        city.setName("TestCity2014");
        city.setPopulation(50000000);
        city.setProvince(Province.NONE);
        city.setRegion(Region.WALLONIA);
        cityDao.save(city);

        testCity2014 = cityDao.getCity("TestCity2014");

        assertNotNull(testCity2014);
        assertEquals("TestCity2014", testCity2014.getName());
        assertEquals(50000000, testCity2014.getPopulation());
        assertEquals(Province.NONE, testCity2014.getProvince());
        assertEquals(Region.WALLONIA, testCity2014.getRegion());

        testCity2014.setPopulation(1000);
        cityDao.update(testCity2014);

        testCity2014 = cityDao.getCity("TestCity2014");

        assertNotNull(testCity2014);
        assertEquals("TestCity2014", testCity2014.getName());
        assertEquals(1000, testCity2014.getPopulation());
        assertEquals(Province.NONE, testCity2014.getProvince());
        assertEquals(Region.WALLONIA, testCity2014.getRegion());

        cityDao.delete("testCity2014");

        testCity2014 = cityDao.getCity("TestCity2014");

        assertNull(testCity2014);
    }
}