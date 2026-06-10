package com.github.virtuasport2.memoriawebapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendResetPasswordEmail(String to, String resetUrl) {
        SimpleMailMessage message = new SimpleMailMessage();
        // Imposta il mittente (da dove viene inviata l'email)
        message.setFrom("saisei1099@libero.it");
        message.setTo(to);
        message.setSubject("Password Reset Request");
        message.setText("Click on the link to reset your password: " + resetUrl);
        mailSender.send(message);
    }
}
