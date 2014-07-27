package com.seb.services.weather;

import com.seb.services.weather.domain.orm.CityTest;
import com.seb.services.weather.domain.orm.TemperatureHistoryTest;
import com.seb.services.weather.domain.orm.WeatherHistoryTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by sebastien.vandamme@gmail.com on 27/07/2014.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        CityTest.class,
        TemperatureHistoryTest.class,
        WeatherHistoryTest.class
})

public class DomainModelTestSuite {
}