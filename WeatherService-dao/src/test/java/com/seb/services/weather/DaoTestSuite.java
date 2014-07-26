package com.seb.services.weather;

import com.seb.services.weather.dao.impl.CityDaoImplTest;
import com.seb.services.weather.dao.impl.TemperatureHistoryDaoImplTest;
import com.seb.services.weather.dao.impl.WeatherHistoryDaoImplTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by sebastien.vandamme@gmail.com on 26/07/2014.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        CityDaoImplTest.class,
        TemperatureHistoryDaoImplTest.class,
        WeatherHistoryDaoImplTest.class
})

public class DaoTestSuite {
}