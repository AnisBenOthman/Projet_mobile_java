/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.services;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.esprit.entities.Forum;
import com.esprit.utils.Statics;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 *
 * @author acer
 */
public class ServiceForum implements IService<Forum> {

    private boolean responseResult;
    private List<Forum> forums;
    String email;
    private final String URI = Statics.BASE_URL + "/forum/";

    public ServiceForum() {
        forums = new ArrayList();

    }

    public boolean ajouter(Forum t) {
        ConnectionRequest request = new ConnectionRequest();

        request.setUrl(URI);
        request.setHttpMethod("POST");

        request.addArgument("sujet", t.getSujet());
        request.addArgument("contenu", t.getContenu());
        request.addArgument("id_user", String.valueOf(t.getId_user()));
        request.addArgument("id_domaine", String.valueOf(t.getId_domaine()));

        request.addResponseListener((evt) -> {
            responseResult = request.getResponseCode() == 201; // Code HTTP 201 OK
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return responseResult;
    }

    public boolean modifier(Forum t) {
        ConnectionRequest request = new ConnectionRequest();

        request.setUrl(URI + t.getId_forum());
        request.setHttpMethod("PUT");

        request.addArgument("sujet", t.getSujet());
        request.addArgument("contenu", t.getContenu());
        request.addArgument("id_domaine", t.getContenu());

        request.addResponseListener((evt) -> {
            responseResult = request.getResponseCode() == 200; // Code HTTP 200 OK
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return responseResult;
    }

    public boolean supprimer(Forum t) {
        ConnectionRequest request = new ConnectionRequest();

        request.setUrl(URI + t.getId_forum());
        request.setHttpMethod("DELETE");

        request.addResponseListener((evt) -> {
            responseResult = request.getResponseCode() == 200; // Code HTTP 200 OK
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return responseResult;
    }

    public List<Forum> afficher() {
        ConnectionRequest request = new ConnectionRequest();

        request.setUrl(URI);
        request.setHttpMethod("GET");

        request.addResponseListener((evt) -> {
            try {
                InputStreamReader jsonText = new InputStreamReader(new ByteArrayInputStream(request.getResponseData()), "UTF-8");
                Map<String, Object> result = new JSONParser().parseJSON(jsonText);
                List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("root");

                for (Map<String, Object> obj : list) {
                    int id_forum = (int) Float.parseFloat(obj.get("id_forum").toString());
                    String sujet = obj.get("sujet").toString();
                    String contenu = obj.get("contenu").toString();
                    int id_user = (int) Float.parseFloat(obj.get("id_user").toString());
                    int id_domaine = (int) Float.parseFloat(obj.get("id_domaine").toString());
                    forums.add(new Forum(id_forum, sujet, contenu, id_user, id_domaine));
                }

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return forums;
    }

    public String getUserEmailByForumId(int forumId) {
        ConnectionRequest request = new ConnectionRequest();

        // Construct the URL to retrieve the user's email based on the forum ID
        String url = URI + "user-email/" + forumId;
        request.setUrl(url);
        request.setHttpMethod("GET");

        request.addResponseListener((evt) -> {
            try {
                // Parse the response data
                InputStreamReader jsonText = new InputStreamReader(new ByteArrayInputStream(request.getResponseData()), "UTF-8");
                Map<String, Object> result = new JSONParser().parseJSON(jsonText);
                List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("root");
                if(!list.isEmpty()){
                   Map<String, Object> obj = list.get(0);
                   email = obj.get("mail").toString();
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(request);

        return email;
    }

}
