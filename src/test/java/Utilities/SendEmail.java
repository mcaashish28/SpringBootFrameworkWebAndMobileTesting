package Utilities;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.UUID;

public class SendEmail {


    public static UUID uuid;
    public static void main(String[] args){
        uuid = UUID.randomUUID();
        System.out.println("UUID key is :" + uuid.toString());

        // SendEmailWithoutAttachment(uuid);

        SendEmail(uuid);


    }

    public static void SendEmailWithoutAttachment(UUID uuid) {

        final String username = "username";
        final String password = "password";

        // Recipient email id :
        String toEmail = "test@gmail.com";
        // Sender email id :
        String fromEmail = "test2@gmail.com";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gamil.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        // start mail message

        try{
            // create a default MimeMessage object
            MimeMessage message = new MimeMessage(session);
            // set fromEmail ; header field
            message.setFrom(new InternetAddress(fromEmail));
            // set toEmail ; header field
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            // set subject line
            message.setSubject("Please find your UUID key to use Automation Framework Portal.");

            // set the actual message
            message.setText("UUID key is :" + uuid.toString());

            // send message
            Transport.send(message);

            System.out.println("UUID in Email is sent successfully.");


        }catch(MessagingException e){
            System.out.println("Exception in sending email: " + e.getMessage());
        }


    }





    public static void SendEmail(UUID uuid){

        // Recipient email id :
        String toEmail = "test@gmail.com";
        // Sender email id :
        String fromEmail = "test2@gmail.com";
        String password = "password";
        // get system properties
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "25");


        // get the default session object
        Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail,password);
            }
        });
        try{
            // create a default MimeMessage object
            MimeMessage message = new MimeMessage(session);
            // set fromEmail ; header field
            message.setFrom(new InternetAddress(fromEmail));
            // set toEmail ; header field
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            // set subject line
            message.setSubject("Please find your UUID key to use Automation Framework Portal.");

            // set the actual message
            message.setText("UUID key is :" + uuid.toString());

            // send message
            Transport.send(message);

            System.out.println("UUID in Email is sent successfully.");


        }catch(Exception e){
            System.out.println("Exception in sending email: " + e.getMessage());
        }


    }


}
