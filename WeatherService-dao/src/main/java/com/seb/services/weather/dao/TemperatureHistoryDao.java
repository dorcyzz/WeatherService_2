package com.seb.services.weather.dao;

import com.seb.services.weather.domain.orm.TemperatureHistory;

import java.util.List;

/**
 * Created by sebastien.vandamme@gmail.com on 24/07/2014.
 */
public interface TemperatureHistoryDao extends GenericDao<TemperatureHistory, Integer> {

    Integer save(TemperatureHistory temperatureHistory);

    void update(TemperatureHistory temperatureHistory);

    void delete(TemperatureHistory temperatureHistory);

    TemperatureHistory findByPrimaryKey(Integer id);

    List<TemperatureHistory> findAll();
}