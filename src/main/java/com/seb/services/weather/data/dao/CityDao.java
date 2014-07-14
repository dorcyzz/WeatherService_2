package com.seb.services.weather.data.dao;

import com.seb.services.weather.data.orm.City;

/**
 * Created by sebastien.vandamme@gmail.com on 12/07/2014.
 */
public interface CityDao {

     City getCity(String name);
}
