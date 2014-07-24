package com.seb.services.weather.service.simulator;

import com.seb.services.weather.dao.CityDao;
import com.seb.services.weather.domain.enums.WeatherType;
import com.seb.services.weather.domain.orm.City;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Created by sebastien.vandamme@gmail.com on 14/07/2014.
 */
@Component
public class WeatherFeedSimulator implements WeatherFeed {

    protected WeatherFeedSimulator() {
        super();
    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
        CityDao cityDao = context.getBean(CityDao.class);

        System.out.println(cityDao.findAll());

//        City city = new City();
//        city.setName("Brussels");
//        city.setPopulation(1138855);
//        city.setProvince(Province.NONE);
//        city.setRegion(Region.BRUSSELS);
//        city.set();
//        city.set();

//        cityDao.delete(2);
//        cityDao.save(city);
        City brussels = cityDao.findByPrimaryKey("Brussels");
        brussels.setPopulation(1138854);
        cityDao.update(brussels);

        System.out.println(cityDao.findAll());
    }

    @Override
    public int feedTemperature(City city) {
        Random generator = new Random();
        return generator.nextInt(45);
    }

    @Override
    public WeatherType feedWeather(City city) {
        Random generator = new Random();
        return WeatherType.values()[generator.nextInt(7)];
    }
}
