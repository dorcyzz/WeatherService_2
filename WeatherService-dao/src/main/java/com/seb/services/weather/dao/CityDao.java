package com.seb.services.weather.dao;

import com.seb.services.weather.domain.orm.City;

import java.util.List;

/**
 * Created by sebastien.vandamme@gmail.com on 12/07/2014.
 */
public interface CityDao extends GenericDao<City, String> {

    void save(City city);

    void update(City city);

    void delete(City name);

    City findByPrimaryKey(String id);

    List<City> findAll();
}
