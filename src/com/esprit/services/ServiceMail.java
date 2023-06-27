package com.esprit.services;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ServiceMail {

    public static void sendMail(String recipient, String subject, String body) {
        System.out.println("Preparing to send email...");

        // Paramètres de configuration SMTP
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true"); // Activation de l'encryption TLS
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        // Votre adresse e-mail Gmail et mot de passe
        final String myEmail = "findjobreply@gmail.com";
        final String password = "vwzjqneictcidlgm";

        // Création de la session avec l'authentification
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myEmail, password);
            }
        });

        try {
            // Création du message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setText(body);

            // Envoi du message
            Transport.send(message);
            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            System.out.println("Failed to send email. Error message: " + e.getMessage());
        }
    }
    
    
    
    public static void sendMailO(String recepient,String object , String msg) throws MessagingException{
        System.out.println("preparing to send Email !   ");
        Properties properties = new Properties();
        
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");//dns Encryption
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        
        String myAdr = "samaous4321@gmail.com";
        String password = "qaifnkrdqanqzlyk";
        
        Session session;
        session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAdr,password);
            }
        });
        Message message = prepareMessaage(session,myAdr,recepient,object,msg);
        Transport.send(message);
        System.out.println("message sent successfully !");
    }

    private static Message prepareMessaage(Session session, String myAdr, String recepient, String object, String msg) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAdr));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject(object);
            message.setText(msg);
            return message;
        } catch (MessagingException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }
}
