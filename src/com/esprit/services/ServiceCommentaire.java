/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.services;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.esprit.entities.Commentaire;
import com.esprit.utils.Statics;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
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
 * @author acer
 */
public class ServiceCommentaire implements IService<Commentaire> {

    private boolean responseResult;
    private List<Commentaire> commentaires;

    private final String URI = Statics.BASE_URL + "/commentaire/";

    public ServiceCommentaire() {
        commentaires = new ArrayList();
    }

    public boolean ajouter(Commentaire t) {
        ConnectionRequest request = new ConnectionRequest();

        request.setUrl(URI);
        request.setHttpMethod("POST");
        request.addArgument("contenu", t.getContenu());
        request.addArgument("id_forum", String.valueOf(t.getId_forum()));
        request.addArgument("id_user", String.valueOf(t.getId_user()));

        request.addResponseListener((evt) -> {
            responseResult = request.getResponseCode() == 201; // Code HTTP 201 OK
        });
        NetworkManager.getInstance().addToQueueAndWait(request);
        if (responseResult){
            
            String senderEmail = "rafikpidev@gmail.com";
            String senderPassword = "jcugmepbjploduae";

            ServiceForum serviceForum = new ServiceForum();
            String recipientEmail = serviceForum.getUserEmailByForumId(t.getId_forum());

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(senderEmail, senderPassword);
                }
            });

            try {
                Message message = new MimeMessage(session);

                message.setFrom(new InternetAddress(senderEmail));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
                message.setSubject("Commentaire ajouté");
                message.setText("quelq un a commenté sur votre forum");
                Transport.send(message);

                System.out.println("Email sent successfully.");
            } catch (MessagingException e) {
                System.out.println("Failed to send email. Error: " + e.getMessage());
            }
        }
        return responseResult;
    }

    public boolean modifier(Commentaire t) {
        ConnectionRequest request = new ConnectionRequest();

        request.setUrl(URI + t.getId_commentaire());
        request.setHttpMethod("PUT");

        request.addArgument("contenu", t.getContenu());

        request.addResponseListener((evt) -> {
            responseResult = request.getResponseCode() == 200; // Code HTTP 200 OK
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return responseResult;
    }

    public boolean supprimer(Commentaire t) {
        ConnectionRequest request = new ConnectionRequest();

        request.setUrl(URI + t.getId_commentaire());
        request.setHttpMethod("DELETE");

        request.addResponseListener((evt) -> {
            responseResult = request.getResponseCode() == 200; // Code HTTP 200 OK
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return responseResult;
    }

    public List<Commentaire> afficher() {
        ConnectionRequest request = new ConnectionRequest();

        request.setUrl(URI);
        request.setHttpMethod("GET");

        request.addResponseListener((evt) -> {
            try {
                InputStreamReader jsonText = new InputStreamReader(new ByteArrayInputStream(request.getResponseData()), "UTF-8");
                Map<String, Object> result = new JSONParser().parseJSON(jsonText);
                List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("root");

                for (Map<String, Object> obj : list) {
                    int id = (int) Float.parseFloat(obj.get("id_commentaire").toString());
                    String contenu = obj.get("contenu").toString();
                    int id_user = (int) Float.parseFloat(obj.get("id_user").toString());
                    int id_forum = (int) Float.parseFloat(obj.get("id_forum").toString());
                    commentaires.add(new Commentaire(contenu, id_forum, id_user));
                }

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return commentaires;
    }

    public List<Commentaire> afficherCommentaireByForum(int id_forum) {
        ConnectionRequest request = new ConnectionRequest();

        String url = URI + "?id_forum=" + id_forum;  // Append the id_forum parameter to the URL
        request.setUrl(url);
        request.setHttpMethod("GET");

        request.addResponseListener((evt) -> {
            try {
                InputStreamReader jsonText = new InputStreamReader(new ByteArrayInputStream(request.getResponseData()), "UTF-8");
                Map<String, Object> result = new JSONParser().parseJSON(jsonText);
                List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("root");

                for (Map<String, Object> obj : list) {
                    int id_commentaire = (int) Float.parseFloat(obj.get("id_commentaire").toString());
                    String contenu = obj.get("contenu").toString();
                    int id_user = (int) Float.parseFloat(obj.get("id_user").toString());
                    commentaires.add(new Commentaire(id_commentaire, contenu, id_forum, id_user));
                }

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return commentaires;
    }

}
