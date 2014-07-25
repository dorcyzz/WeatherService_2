package com.seb.services.weather;

import com.seb.services.weather.dao.impl.CityDaoImplTest;
import com.seb.services.weather.dao.impl.TemperatureHistoryDaoImplTest;
import com.seb.services.weather.dao.impl.WeatherHistoryDaoImplTest;
import com.seb.services.weather.service.simulator.WeatherFeedSimulatorTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by sebastien.vandamme@gmail.com on 21/07/2014.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
//        CityTest.class,
//        TemperatureHistoryTest.class,
//        WeatherHistoryTest.class,
        CityDaoImplTest.class,
        TemperatureHistoryDaoImplTest.class,
        WeatherHistoryDaoImplTest.class,
        WeatherFeedSimulatorTest.class
})
@ContextConfiguration("classpath:test-application-context.xml")
@TransactionConfiguration(transactionManager = "transactionManager")
@Transactional
public class TestSuite {
}