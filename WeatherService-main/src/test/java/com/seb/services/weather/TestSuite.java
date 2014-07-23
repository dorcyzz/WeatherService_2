package com.seb.services.weather;

import com.seb.services.weather.service.simulator.WeatherFeedSimulatorTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by sebastien.vandamme@gmail.com on 21/07/2014.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
//        CityTest.class,
//        TemperatureHistoryTest.class,
//        WeatherHistoryTest.class,
        WeatherFeedSimulatorTest.class
})
public class TestSuite {
}