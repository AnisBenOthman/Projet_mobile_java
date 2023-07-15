package com.esprit.entities;

import java.util.Properties;
import com.codename1.io.Log;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {

  public static void sendMail(String recepient,String object , String msg) throws MessagingException{
        System.out.println("Préparation du Mail !   ");
        Properties properties = new Properties();
        
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");//dns Encryption
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        
        String myAdr = "findjobreply@gmail.com";
        String password = "pjwzsenustgioixb";
        
        Session session;
        session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAdr,password);
            }
        });
        Message message = prepareMessaage(session,myAdr,recepient,object,msg);
        Transport.send(message);
        System.out.println("Mail envoyé !");
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
            Log.p(Email.class.getName());
        }
        return null;
    }
    
    public static String envoiReclamationMessage(String nomUtilisateur) {
        return 
                "Cher(e) " + nomUtilisateur + ",\n\n" +
                "Nous vous remercions d'avoir pris le temps de nous contacter concernant votre réclamation. Nous comprenons l'importance de votre demande et nous souhaitons vous informer que nous l'avons bien reçue.\n" +
                "\n" +
                "Chez Find Job, la satisfaction de nos clients est notre priorité absolue. Nous tenons à vous assurer que nous traitons votre réclamation avec la plus grande attention. Notre équipe dédiée examine actuellement les détails de votre demande afin de vous apporter une solution adaptée dans les plus brefs délais.\n" +
                "\n" +
                "Nous comprenons que vous souhaitez obtenir une résolution rapide et efficace, et nous mettons tout en œuvre pour y parvenir. Nous vous tiendrons informé(e) de l'avancement de votre réclamation et nous vous fournirons une réponse détaillée dès que possible.\n" +
                "\n" +
                "Si vous avez d'autres questions ou si vous souhaitez fournir des informations supplémentaires concernant votre réclamation, n'hésitez pas à nous contacter à l'adresse e-mail findjobreply@gmail.com ou en répondant directement à ce message.\n" +
                "\n" +
                "Nous vous remercions de votre patience et de votre confiance. Nous sommes déterminés à résoudre votre réclamation de manière satisfaisante. Votre satisfaction en tant que client(e) est primordiale pour nous.\n" +
                "\n" +
                "Cordialement,\n" +
                "\n" +
                "Admin Find Job\n" +
                "[Find Job]\n" +
                "[(+216)54.442.147] ";
    }
    
    public static String envoiRejectMessage(String nomUtilisateur,  int refReclamation ) {
        return
                "Cher(e) " + nomUtilisateur + ",\n\n" +
                "Nous avons examiné attentivement votre réclamation concernant, Ref: [" + refReclamation + "]. Après une analyse approfondie, nous regrettons de vous informer que nous ne sommes pas en mesure de donner une suite favorable à votre demande.\n\n" +
                "Nous comprenons que cela puisse être décevant, mais nous tenons à vous assurer que nous avons pris en compte tous les aspects de votre réclamation.\n\n" +
                "Nous comprenons que vous puissiez avoir des questions supplémentaires ou souhaiter discuter de cette décision plus en détail. Si tel est le cas, n'hésitez pas à nous contacter par e-mail à findjobreply@gmail.com ou en répondant directement à ce message. Nous serons heureux de vous fournir des explications supplémentaires ou d'examiner toute autre préoccupation que vous pourriez avoir.\n\n" +
                "Nous vous remercions de votre compréhension et de votre coopération. Votre satisfaction en tant que client(e) est importante pour nous, même dans les situations où nous ne pouvons pas répondre à vos attentes.\n\n" +
                "Cordialement,\n" +
                "\n" +
                "Admin Find Job\n" +
                "[Find Job]\n" +
                "[(+216)54.442.147] ";
    }
    
        public static String envoiValidMessage(String nomUtilisateur,  int refReclamation ) {
            return
                "Cher(e) " + nomUtilisateur + ",\n\n" +
                "Nous vous remercions d'avoir pris le temps de nous contacter concernant votre réclamation concernant, Ref: [" + refReclamation + "]. Nous comprenons l'importance de votre demande et nous souhaitons vous informer que nous l'avons bien reçue.\n\n" +
                "Chez Find Job, la satisfaction de nos clients est notre priorité absolue. Nous tenons à vous assurer que nous traitons votre réclamation avec la plus grande attention. Notre équipe dédiée examine actuellement les détails de votre demande afin de vous apporter une solution adaptée dans les plus brefs délais.\n\n" +
                "Nous comprenons que vous souhaitez obtenir une résolution rapide et efficace, et nous mettons tout en œuvre pour y parvenir. Nous vous tiendrons informé(e) de l'avancement de votre réclamation et nous vous fournirons une réponse détaillée dès que possible.\n\n" +
                "Si vous avez d'autres questions ou si vous souhaitez fournir des informations supplémentaires concernant votre réclamation, n'hésitez pas à nous contacter à l'adresse e-mail find-job@gmail.com ou en répondant directement à ce message.\n\n" +
                "Nous vous remercions de votre patience et de votre confiance. Nous sommes déterminés à résoudre votre réclamation de manière satisfaisante. Votre satisfaction en tant que client(e) est primordiale pour nous.\n\n" +
                "Cordialement,\n" +
                "\n" +
                "Admin Find Job\n" +
                "[Find Job]\n" +
                "[(+216)54.442.147] ";
    }
}