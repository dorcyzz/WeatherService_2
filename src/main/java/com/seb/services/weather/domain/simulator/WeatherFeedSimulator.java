package com.seb.services.weather.domain.simulator;

import com.seb.services.weather.domain.orm.City;
import com.seb.services.weather.enums.WeatherType;
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
