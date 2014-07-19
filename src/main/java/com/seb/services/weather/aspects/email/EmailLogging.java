package com.seb.services.weather.aspects.email;

import com.seb.services.weather.service.email.EmailServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by sebastien.vandamme@gmail.com on 19/07/2014.
 */
@Aspect
public class EmailLogging {
    private static final Logger LOGGER = LogManager.getLogger(EmailServiceImpl.class);

    @Pointcut("execution(* com.seb.services.weather.service.email.EmailService.sendEmail())")
    public void logging() {

    }

    @Around("logging()")
    public void normalLog(ProceedingJoinPoint joinPoint) {
        try {
            LOGGER.info("Starting mail sending...");
            joinPoint.proceed();
            LOGGER.info("Mail sended successfully...");
        } catch (Throwable throwable) {
            LOGGER.error("Problem while sending mail", throwable);
        }
    }
}


