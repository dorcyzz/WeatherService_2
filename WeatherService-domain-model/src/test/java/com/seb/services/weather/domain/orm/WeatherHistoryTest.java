package com.seb.services.weather.domain.orm;

//import com.seb.tools.test.GetterSetterTest;

import com.seb.services.weather.domain.enums.Province;
import com.seb.services.weather.domain.enums.Region;
import com.seb.services.weather.domain.enums.WeatherType;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.joda.time.LocalDateTime;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class WeatherHistoryTest {
    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void nameIsBlank() {
        City city = new City();
        city.setName("TestCity2014");
        city.setPopulation(5000);
        city.setProvince(Province.NONE);
        city.setRegion(Region.WALLONIA);

        WeatherHistory weatherHistory = new WeatherHistory();
        weatherHistory.setName(null);
        weatherHistory.setCity(city);
        weatherHistory.setDate(LocalDateTime.now());
        weatherHistory.setWeather(WeatherType.SUNNY);

        Set<ConstraintViolation<WeatherHistory>> constraintViolations = validator.validate(weatherHistory);

        assertEquals(1, constraintViolations.size());

        weatherHistory.setName("");
        constraintViolations = validator.validate(weatherHistory);
        assertEquals(1, constraintViolations.size());

        weatherHistory.setName("testCity2014");
        constraintViolations = validator.validate(weatherHistory);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void cityIsNull() {

        WeatherHistory weatherHistory = new WeatherHistory();
        weatherHistory.setName("TestCity2014");
        weatherHistory.setCity(null);
        weatherHistory.setDate(LocalDateTime.now());
        weatherHistory.setWeather(WeatherType.SUNNY);

        Set<ConstraintViolation<WeatherHistory>> constraintViolations = validator.validate(weatherHistory);

        assertEquals(1, constraintViolations.size());

        weatherHistory.setCity(new City());
        constraintViolations = validator.validate(weatherHistory);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void weatherIsNull() {
        City city = new City();
        city.setName("TestCity2014");
        city.setPopulation(5000);
        city.setProvince(Province.NONE);
        city.setRegion(Region.WALLONIA);

        WeatherHistory weatherHistory = new WeatherHistory();
        weatherHistory.setName("TestCity2014");
        weatherHistory.setCity(city);
        weatherHistory.setDate(LocalDateTime.now());
        weatherHistory.setWeather(null);

        Set<ConstraintViolation<WeatherHistory>> constraintViolations = validator.validate(weatherHistory);

        assertEquals(1, constraintViolations.size());

        weatherHistory.setWeather(WeatherType.SUNNY);
        constraintViolations = validator.validate(weatherHistory);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void dateIsNull() {
        City city = new City();
        city.setName("TestCity2014");
        city.setPopulation(5000);
        city.setProvince(Province.NONE);
        city.setRegion(Region.WALLONIA);

        WeatherHistory weatherHistory = new WeatherHistory();
        weatherHistory.setName("TestCity2014");
        weatherHistory.setCity(city);
        weatherHistory.setDate(null);
        weatherHistory.setWeather(WeatherType.SUNNY);

        Set<ConstraintViolation<WeatherHistory>> constraintViolations = validator.validate(weatherHistory);

        assertEquals(1, constraintViolations.size());

        weatherHistory.setDate(LocalDateTime.now());
        constraintViolations = validator.validate(weatherHistory);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(WeatherHistory.class).withPrefabValues(LocalDateTime.class, LocalDateTime.now(), LocalDateTime.now().plusDays(1)).verify();
    }
}