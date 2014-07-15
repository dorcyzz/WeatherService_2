package com.seb.services.weather.data.dao;

import com.seb.services.weather.data.orm.City;

import java.util.List;

/**
 * Created by sebastien.vandamme@gmail.com on 12/07/2014.
 */
public interface CityDao {

     City getCity(String name);

    public List<City> list();

    public void saveOrUpdate(City city);

    public void delete(int id);
}
