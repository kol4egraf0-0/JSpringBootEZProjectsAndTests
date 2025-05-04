package com.example.demo.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {//для отпраки на почту

    @Autowired
    private JavaMailSender emailSender;

    public void sendVerificationEmail(String to, String subject, String content) throws MessagingException {
        MimeMessage mimeMessage = emailSender.createMimeMessage(); //Multipurpose Internet Mail Extensions
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        //true для модификации, своё кастмной ht,l можно дать чуть
        helper.setTo(to);
        helper.setSubject(subject); //verification кодик
        helper.setText(content, true); //boolean html

        emailSender.send(mimeMessage);
    }
}
