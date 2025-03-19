package com.br.foodconnect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public void sendConfirmationEmail(String receiverEmail, String confirmationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(receiverEmail);
        message.setFrom(sender);
        message.setSubject("FoodConnect - Register Confirmation!");
        message.setText("Congrats\nYour confirmation code is: " + confirmationCode);
        mailSender.send(message);
    }
}
