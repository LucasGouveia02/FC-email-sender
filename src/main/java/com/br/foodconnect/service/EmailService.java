package com.br.foodconnect.service;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
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

    public void sendConfirmationEmail(String receiverEmail, String confirmationCode) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(receiverEmail);
        helper.setSubject("FoodConnect - Account Activation");

        String imagePath = "src/main/resources/static/logo.jpg";
        File imageFile = new File(imagePath);

        String htmlBody = """
                <!DOCTYPE html>
                <html lang="pt">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>E-mail Confirmation</title>
                    <style>
                        body { font-family: Arial, sans-serif; text-align: center; padding: 20px; }
                        .container { max-width: 500px; margin: auto; padding: 20px; border: 1px solid #ccc; border-radius: 10px; }
                        h1 { color: #F31C1C; }
                        p { font-size: 18px; }
                        .code { font-size: 24px; font-weight: bold; color: #28a745; }
                        .image { margin-top: 20px; width: 150px; }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <h1>E-mail Confirmation</h1>
                        <p>Your validation code is:</p>
                        <p class="code">""" + confirmationCode + """
                        </p>
                        <p>Use this code to confirm your identity.</p>
                        <img src="cid:logoImage" class="image" alt="Logo">
                    </div>
                </body>
                </html>
            """;

        helper.setText(htmlBody, true);
        helper.addInline("logoImage", imageFile);
        mailSender.send(message);
    }
}
