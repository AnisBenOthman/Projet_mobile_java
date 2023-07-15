/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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



/**
 *
 * @author ASUS
 */
public class ServiceMail0{
    
        
    
    public static void sendMail(String recepient,String object , String msg) throws MessagingException{
        System.out.println("preparing to send Email !   ");
        Properties properties = new Properties();
        
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");//dns Encryption
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        
        String myAdr = "samaous4321@gmail.com";
        String password = "mvfuhjueitpyadge";
        
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
            System.out.println(ex.getMessage());
        }
        return null;
    }
    
    
}
