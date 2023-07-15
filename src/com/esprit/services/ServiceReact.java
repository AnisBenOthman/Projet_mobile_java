/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.services;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.esprit.entities.React;
import com.esprit.utils.Statics;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 *
 * @author acer
 */
public class ServiceReact implements IService<React> {

    private boolean responseResult;
    private List<React> reacts;

    private final String URI = Statics.BASE_URL + "/react/";

    public ServiceReact() {
        reacts = new ArrayList();
    }

    public boolean ajouter(React t) {
        ConnectionRequest request = new ConnectionRequest();

        request.setUrl(URI);
        request.setHttpMethod("POST");
        request.addArgument("liked", String.valueOf(t.isLiked()));
        request.addArgument("id_user", String.valueOf(t.getId_user()));
        request.addArgument("id_commentaire", String.valueOf(t.getId_Commentaire()));
        request.addResponseListener((evt) -> {
            responseResult = request.getResponseCode() == 201; // Code HTTP 201 OK
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return responseResult;
    }

    public boolean modifier(React t) {
        ConnectionRequest request = new ConnectionRequest();

        request.setUrl(URI + t.getId_react());
        request.setHttpMethod("PUT");

        request.addArgument("liked", String.valueOf(t.isLiked()));
        request.addArgument("id_user", String.valueOf(t.getId_user()));
        request.addArgument("id_commentaire", String.valueOf(t.getId_Commentaire()));

        request.addResponseListener((evt) -> {
            responseResult = request.getResponseCode() == 200; // Code HTTP 200 OK
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return responseResult;
    }

    public boolean supprimer(React t) {
        ConnectionRequest request = new ConnectionRequest();

        request.setUrl(URI + t.getId_react());
        request.setHttpMethod("DELETE");

        request.addResponseListener((evt) -> {
            responseResult = request.getResponseCode() == 200; // Code HTTP 200 OK
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return responseResult;
    }

    public List<React> afficher() {
        return null;
    }
}
