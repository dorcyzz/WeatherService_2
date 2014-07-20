package com.seb.services.weather.service.simulator;

import com.seb.services.weather.domain.enums.WeatherType;
import com.seb.services.weather.domain.orm.City;

/**
 * Created by sebastien.vandamme@gmail.com on 14/07/2014.
 */
public interface WeatherFeed {

    int feedTemperature(City city);

    WeatherType feedWeather(City city);
}
