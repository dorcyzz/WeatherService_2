package com.seb.services.weather.domain.orm;


import com.seb.services.weather.domain.enums.Province;
import com.seb.services.weather.domain.enums.Region;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class CityTest {

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void nameIsBlank() {
        City city = new City();
        city.setName(null);
        city.setPopulation(5000);
        city.setProvince(Province.NONE);
        city.setRegion(Region.WALLONIA);

        Set<ConstraintViolation<City>> constraintViolations = validator.validate(city);

        assertEquals(1, constraintViolations.size());

        city.setName("");
        constraintViolations = validator.validate(city);
        assertEquals(1, constraintViolations.size());


        city.setName("testCity2014");
        constraintViolations = validator.validate(city);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void populationTooLowOrNull() {
        City city = new City();
        city.setName("TestCity2014");
        city.setPopulation(100);
        city.setProvince(Province.NONE);
        city.setRegion(Region.WALLONIA);

        Set<ConstraintViolation<City>> constraintViolations = validator.validate(city);

        assertEquals(1, constraintViolations.size());


        city.setPopulation(null);
        constraintViolations = validator.validate(city);
        assertEquals(1, constraintViolations.size());

        city.setPopulation(5000);
        constraintViolations = validator.validate(city);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void provinceIsNull() {
        City city = new City();
        city.setName("TestCity2014");
        city.setPopulation(5000);
        city.setProvince(null);
        city.setRegion(Region.WALLONIA);

        Set<ConstraintViolation<City>> constraintViolations = validator.validate(city);

        assertEquals(1, constraintViolations.size());

        city.setProvince(Province.NONE);
        constraintViolations = validator.validate(city);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void regionIsNull() {
        City city = new City();
        city.setName("TestCity2014");
        city.setPopulation(5000);
        city.setProvince(Province.EAST_FLANDERS);
        city.setRegion(null);

        Set<ConstraintViolation<City>> constraintViolations = validator.validate(city);

        assertEquals(1, constraintViolations.size());


        city.setRegion(Region.WALLONIA);
        constraintViolations = validator.validate(city);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void temperatureHistoryIsNull() {
        City city = new City();
        city.setName("TestCity2014");
        city.setPopulation(5000);
        city.setProvince(Province.EAST_FLANDERS);
        city.setRegion(Region.WALLONIA);
        city.setTemperatureHistory(null);

        Set<ConstraintViolation<City>> constraintViolations = validator.validate(city);

        assertEquals(1, constraintViolations.size());

        city.setTemperatureHistory(new HashSet<TemperatureHistory>());
        constraintViolations = validator.validate(city);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void weatherHistoryIsNull() {
        City city = new City();
        city.setName("TestCity2014");
        city.setPopulation(5000);
        city.setProvince(Province.EAST_FLANDERS);
        city.setRegion(Region.WALLONIA);
        city.setWeatherHistory(null);

        Set<ConstraintViolation<City>> constraintViolations = validator.validate(city);

        assertEquals(1, constraintViolations.size());

        city.setWeatherHistory(new HashSet<WeatherHistory>());
        constraintViolations = validator.validate(city);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(City.class).verify();
    }
}