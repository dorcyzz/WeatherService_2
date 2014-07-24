package com.seb.services.weather.service;

import com.seb.services.weather.dao.CityDao;
import com.seb.services.weather.domain.enums.Province;
import com.seb.services.weather.domain.enums.Region;
import com.seb.services.weather.domain.enums.WeatherType;
import com.seb.services.weather.domain.orm.City;
import com.seb.services.weather.domain.orm.WeatherHistory;
import org.joda.time.LocalDateTime;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sebastien.vandamme@gmail.com on 20/07/2014.
 */
public class TestMain {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
        CityDao cityDao = context.getBean(CityDao.class);


        City testCity2014 = cityDao.findByPrimaryKey("TestCity2014");


        System.out.println(testCity2014);


        cityDao.delete(testCity2014);

        testCity2014 = cityDao.findByPrimaryKey("TestCity2014");

        System.out.println(testCity2014);

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

        cityDao.save(city);

        testCity2014 = cityDao.findByPrimaryKey("TestCity2014");


        System.out.println(testCity2014);


        testCity2014.setPopulation(1000);
        cityDao.update(testCity2014);

        testCity2014 = cityDao.findByPrimaryKey("TestCity2014");


        System.out.println(testCity2014);


//        cityDao.delete(testCity2014);
//
//        testCity2014 = cityDao.findByPrimaryKey("TestCity2014");
//
//
//        System.out.println(testCity2014);

    }
}
