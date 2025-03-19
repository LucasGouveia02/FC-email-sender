package com.br.foodconnect.service;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    Dotenv dotenv = Dotenv.configure()
            .directory("env/local")
            .filename("env.conf")
            .load();

    String usernamePath = dotenv.get("GMAIL_USERNAME");
    String username = new String(Files.readAllBytes(Paths.get(usernamePath)));

    private final String sender = username;

    public EmailService() throws IOException {
    }

    public void sendConfirmationEmail(String receiverEmail, String confirmationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(receiverEmail);
        message.setFrom(sender);
        message.setSubject("FoodConnect - Register Confirmation!");
        message.setText("Congrats\nYour confirmation code is: " + confirmationCode);
        mailSender.send(message);
    }
}
