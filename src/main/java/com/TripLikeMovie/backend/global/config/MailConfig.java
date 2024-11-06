package com.TripLikeMovie.backend.global.config;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost("smtp.gmail.com");
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        mailSender.setPort(587);
        mailSender.setJavaMailProperties(getProperties());
        return mailSender;
    }

    private Properties getProperties() {
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.debug", "true");
        properties.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.setProperty("mail.smtp.ssl.enable", "true");
        return properties;
    }
}
