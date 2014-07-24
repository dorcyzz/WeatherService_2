package com.seb.services.weather.dao;

import com.seb.services.weather.domain.orm.WeatherHistory;

import java.util.List;

/**
 * Created by sebastien.vandamme@gmail.com on 24/07/2014.
 */
public interface WeatherHistoryDao extends GenericDao<WeatherHistory, Integer> {

    void save(WeatherHistory weatherHistory);

    void update(WeatherHistory weatherHistory);

    void delete(WeatherHistory weatherHistory);

    WeatherHistory findByPrimaryKey(Integer id);

    List<WeatherHistory> findAll();
}
