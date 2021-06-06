package com.example.airport.components.mail;

public interface MailService {
    boolean send(String sendTo, String subject, String textMessage);
}
