package com.seb.services.weather.domain.orm;

//import com.seb.tools.test.GetterSetterTest;

import com.seb.services.weather.domain.enums.Province;
import com.seb.services.weather.domain.enums.Region;
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

public class TemperatureHistoryTest {
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

        TemperatureHistory temperatureHistory = new TemperatureHistory();
        temperatureHistory.setName(null);
        temperatureHistory.setCity(city);
        temperatureHistory.setDate(LocalDateTime.now());
        temperatureHistory.setTemperature(42);

        Set<ConstraintViolation<TemperatureHistory>> constraintViolations = validator.validate(temperatureHistory);

        assertEquals(1, constraintViolations.size());

        temperatureHistory.setName("");
        constraintViolations = validator.validate(temperatureHistory);
        assertEquals(1, constraintViolations.size());

        temperatureHistory.setName("testCity2014");
        constraintViolations = validator.validate(temperatureHistory);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void cityIsNull() {

        TemperatureHistory temperatureHistory = new TemperatureHistory();
        temperatureHistory.setName("TestCity2014");
        temperatureHistory.setCity(null);
        temperatureHistory.setDate(LocalDateTime.now());
        temperatureHistory.setTemperature(42);

        Set<ConstraintViolation<TemperatureHistory>> constraintViolations = validator.validate(temperatureHistory);

        assertEquals(1, constraintViolations.size());

        temperatureHistory.setCity(new City());
        constraintViolations = validator.validate(temperatureHistory);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void temperatureIsNull() {
        City city = new City();
        city.setName("TestCity2014");
        city.setPopulation(5000);
        city.setProvince(Province.NONE);
        city.setRegion(Region.WALLONIA);

        TemperatureHistory temperatureHistory = new TemperatureHistory();
        temperatureHistory.setName("TestCity2014");
        temperatureHistory.setCity(city);
        temperatureHistory.setDate(LocalDateTime.now());
        temperatureHistory.setTemperature(null);

        Set<ConstraintViolation<TemperatureHistory>> constraintViolations = validator.validate(temperatureHistory);

        assertEquals(1, constraintViolations.size());

        temperatureHistory.setTemperature(12);
        constraintViolations = validator.validate(temperatureHistory);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void dateIsNull() {
        City city = new City();
        city.setName("TestCity2014");
        city.setPopulation(5000);
        city.setProvince(Province.NONE);
        city.setRegion(Region.WALLONIA);

        TemperatureHistory temperatureHistory = new TemperatureHistory();
        temperatureHistory.setName("TestCity2014");
        temperatureHistory.setCity(city);
        temperatureHistory.setDate(null);
        temperatureHistory.setTemperature(42);

        Set<ConstraintViolation<TemperatureHistory>> constraintViolations = validator.validate(temperatureHistory);

        assertEquals(1, constraintViolations.size());

        temperatureHistory.setDate(LocalDateTime.now());
        constraintViolations = validator.validate(temperatureHistory);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(TemperatureHistory.class).withPrefabValues(LocalDateTime.class, LocalDateTime.now(), LocalDateTime.now().plusDays(1)).verify();
    }
}