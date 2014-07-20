package com.seb.services.weather.service;

import com.seb.services.weather.dao.CityDao;
import com.seb.services.weather.domain.enums.Province;
import com.seb.services.weather.domain.enums.Region;
import com.seb.services.weather.domain.orm.City;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by sebastien.vandamme@gmail.com on 20/07/2014.
 */
public class TestMain {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
        CityDao cityDao = context.getBean(CityDao.class);

        System.out.println(cityDao.list());

        City city = new City();
        city.setName("Brussels");
        city.setPopulation(1138855);
        city.setProvince(Province.NONE);
        city.setRegion(Region.BRUSSELS);
        cityDao.save(city);

        System.out.println(cityDao.list());

        City brussels = cityDao.getCity("Brussels");
        brussels.setPopulation(1138854);
        cityDao.update(brussels);

        System.out.println(cityDao.list());

        cityDao.delete("Brussels");

        System.out.println(cityDao.list());
    }
}
