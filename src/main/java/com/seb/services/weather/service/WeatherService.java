package com.seb.services.weather.service;

import com.seb.services.weather.enums.WeatherType;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * Created by sebastien.vandamme@gmail.com on 12/07/2014.
 */
@WebService(targetNamespace = "http://seb.weather.com")
public interface WeatherService {

    @WebMethod
    short getCurrentTemperature(String city);

    @WebMethod
    WeatherType getCurrentWeather(String city);
}