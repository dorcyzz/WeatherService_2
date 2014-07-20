package com.seb.services.weather.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * Created by sebastien.vandamme@gmail.com on 19/07/2014.
 */
@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public EmailServiceImpl() {
        super();
    }

    public static final void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
        EmailService emailService = context.getBean(EmailService.class);

        emailService.sendEmail("sebastien.vandamme@gmail.com", "The weather is changing !");
    }

    @Override
    public void sendEmail(String to, String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom("noreply@seb.weather.com");
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject("Message from seb.weather.com");
        simpleMailMessage.setText(message);

        mailSender.send(simpleMailMessage);
    }
}
