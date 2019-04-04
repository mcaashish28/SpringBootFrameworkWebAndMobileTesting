package com.springproject.controllers;

import com.springproject.services.EmailNotification;
import com.springproject.services.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SendEmail {

    @Autowired
    private EmailNotification emailNotification;

    @RequestMapping("/signup")
    public String signup(){
        return "please signup for our service.";
    }

    @RequestMapping("/signup-success")
    public String signupSuccess(){

        // create user
        User user = new User();
        user.setFirstName("FirstName");
        user.setLastName("LastName");
        user.setEmailAddress("ToEmailID@gmail.com");        // to email address
        // Send email notification

        try{
            emailNotification.sendNotification(user);
        }catch(MailException e){
            System.out.println("Exception in email sending : " + e.getMessage());
        }

        return "Thank you for Signup with us.";
    }

}
