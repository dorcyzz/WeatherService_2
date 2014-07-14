package com.seb.services.weather.data.simulator;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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