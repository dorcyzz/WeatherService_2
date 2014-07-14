package com.seb.services.weather.data.simulator;

import com.seb.services.weather.data.orm.City;
import com.seb.services.weather.enums.WeatherType;

/**
 * Created by sebastien.vandamme@gmail.com on 14/07/2014.
 */
public interface WeatherFeed {

    int feedTemperature(City city);

    WeatherType feedWeather(City city);
}
