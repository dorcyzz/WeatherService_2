package com.seb.services.weather.service.email;

/**
 * Created by sebastien.vandamme@gmail.com on 19/07/2014.
 */
public interface EmailService {

    void sendEmail(String to, String message);
}
