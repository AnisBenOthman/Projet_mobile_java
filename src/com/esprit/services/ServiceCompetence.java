/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.services;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.esprit.entities.Competence;
import com.esprit.utils.Statics;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 *
 * @author abdel
 */
public class ServiceCompetence implements IService<Competence> {

    private boolean responseResult;
    private final List<Competence> competences;
    
    private final String URI = Statics.BASE_URL + "/user/";

    public ServiceCompetence() {
        competences = new ArrayList();
    }

    @Override
    public boolean ajouter(Competence c) {
        ConnectionRequest request = new ConnectionRequest();
        
        request.setUrl(URI + "/insertcompetence");
        request.setHttpMethod("POST");

        request.addArgument("nom", c.getNom());
        request.addArgument("description", c.getDescription());
      

        request.addResponseListener((evt) -> {
            responseResult = request.getResponseCode() == 201; // Code HTTP 201 OK
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return responseResult;
    }

    public boolean modifier(Competence c) {
        ConnectionRequest request = new ConnectionRequest();
        
        request.setUrl(URI + "updatecompetence/" + c.getId_c());
        request.setHttpMethod("PUT");

        request.addArgument("nom", c.getNom());
        request.addArgument("description", c.getDescription());

        request.addResponseListener((evt) -> {
            responseResult = request.getResponseCode() == 200; // Code HTTP 200 OK
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return responseResult;
    }

    public boolean supprimer(Competence c) {
        ConnectionRequest request = new ConnectionRequest();
        
        request.setUrl(URI + "deletecompetence/" + c.getId_c());
        request.setHttpMethod("DELETE");

        request.addResponseListener((evt) -> {
            responseResult = request.getResponseCode() == 200; // Code HTTP 200 OK
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return responseResult;
    }

    public List<Competence> afficher() {
        ConnectionRequest request = new ConnectionRequest();
        
        request.setUrl(URI + "selectcompetence");
        request.setHttpMethod("GET");

        request.addResponseListener((evt) -> {
            try {
                InputStreamReader jsonText = new InputStreamReader(new ByteArrayInputStream(request.getResponseData()), "UTF-8");
                Map<String, Object> result = new JSONParser().parseJSON(jsonText);
                List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("root");

                for (Map<String, Object> obj : list) {
                    int id_c = (int) Float.parseFloat(obj.get("id_c").toString());
                    String nom = obj.get("nom").toString();
                    String description = obj.get("description").toString();
                    competences.add(new Competence(id_c, nom, description));
                }

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return competences;
    }
    
    
    public int GetIdCompetenceByNom (String nom){
                List<Competence> allCompetences = afficher(); 
    int id= 0;
        for (Competence Competence : allCompetences) {
        if (Competence.getNom().equals(nom)) {
            id = Competence.getId_c();
            break;
        }
    }
        return id;
    }
}
