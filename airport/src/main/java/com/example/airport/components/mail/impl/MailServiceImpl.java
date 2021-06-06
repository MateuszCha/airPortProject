package com.example.airport.components.mail.impl;

import com.example.airport.components.mail.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;
    private final Logger log = LoggerFactory.getLogger(MailServiceImpl.class);
    private final Environment env;

    @Autowired
    public MailServiceImpl(JavaMailSender mailSender, Environment env) {
        this.mailSender = mailSender;
        this.env = env;
    }

    @Override
    public boolean send(String sendTo, String subject, String textMessage){
        this.isMailsValidate(sendTo,subject,textMessage);
        try{
            String from = env.getProperty("spring.mail.username");
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            if(Objects.nonNull(from) && from.isBlank()) {
                mailMessage.setFrom(from);
            }
            mailMessage.setSubject(subject);
            mailMessage.setText(textMessage);
            mailMessage.setTo(sendTo);
            mailSender.send(mailMessage);
        }catch (MailException err){
            log.error(err.getMessage());
            return false;
        }
        return true;
    }

    private boolean isMailsValidate(String sendTo,String subject,String textMessage){
        if(Objects.isNull(sendTo) || sendTo.isBlank()){
           throw new IllegalArgumentException("sendTo field ");
        }
        if(Objects.isNull(subject) || subject.isBlank()){
            throw new IllegalArgumentException("subject field ");
        }
        if(Objects.isNull(textMessage) || textMessage.isBlank()){
            throw new IllegalArgumentException("textMessage field ");
        }
        return true;
    }

}
