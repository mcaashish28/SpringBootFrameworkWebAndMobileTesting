package com.springproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.springproject.services.User;

import java.util.UUID;

@Service
public class EmailNotification {

    private JavaMailSender javaMailSender;

    @Autowired
    public EmailNotification(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendNotification(User user) throws MailException {
        // Generate UUID
        UUID uuid = UUID.randomUUID();

        // send email
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmailAddress());
        mailMessage.setFrom("FromEmailId@gmail.com");       // from email address
        mailMessage.setSubject("Your UUID Key for Automation Portal Access");
        mailMessage.setText("Hi Mohan.. Your UUID key to Access Automation Portal is :" + uuid.toString());

        javaMailSender.send(mailMessage);

    }


}
