package com.seb.services.weather.domain.dao;

import com.seb.services.weather.domain.orm.City;

import java.util.List;

/**
 * Created by sebastien.vandamme@gmail.com on 12/07/2014.
 * Project ${PROJECT_NAME}
 */
public interface CityDao {

    City getCity(String name);

    public List<City> list();

    public void save(City city);

    public void update(City city);

    public void delete(int id);
}
