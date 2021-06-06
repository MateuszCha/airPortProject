package com.example.airport.components.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@PropertySource("classpath:mailsender.properties")
public class ComponentConfiguration {

    @Bean
    public JavaMailSenderImpl mailSender(Environment env) {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(env.getProperty("spring.mail.host"));
        javaMailSender.setPort(Integer.valueOf(env.getProperty("spring.mail.port")));
        javaMailSender.setPassword(env.getProperty("spring.mail.password"));
        javaMailSender.setUsername(env.getProperty("spring.mail.username"));
        javaMailSender.setDefaultEncoding("UTF-8");
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", Boolean.getBoolean(env.getProperty("spring.mail.password.mail.smtp.auth")));
        prop.put("mail.smtp.starttls.enable", Boolean.getBoolean(env.getProperty("spring.mail.password.mail.smtp.starttls.enable")));
        prop.put("mail.smtp.starttls.required", Boolean.getBoolean(env.getProperty("spring.mail.password.mail.smtp.starttls.required")));
        javaMailSender.setJavaMailProperties(prop);
        return javaMailSender;
    }



}
