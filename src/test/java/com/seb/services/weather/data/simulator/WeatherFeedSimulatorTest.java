package com.seb.services.weather.data.simulator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-context.xml")
public class WeatherFeedSimulatorTest {

    @Autowired
    private WeatherFeed feed;

    @Test
    public void testFeedTemperature() {
        for (int i = 0; i < 100; ++i) {
            System.out.println(this.feed.feedTemperature(null));
        }
    }

    @Test
    public void testFeedWeather() {
        for (int i = 0; i < 100; ++i) {
            System.out.println(this.feed.feedWeather(null));
        }
    }
}