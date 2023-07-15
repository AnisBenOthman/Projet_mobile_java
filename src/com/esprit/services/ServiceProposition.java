/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.services;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.esprit.entities.Proposition;
import com.esprit.utils.Statics;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 *
 * @author abdel
 */
public class ServiceProposition implements IService<Proposition> {

    private boolean responseResult;
    private List<Proposition> propositions;
    
    private final String URI = Statics.BASE_URL + "/user/";

    public ServiceProposition() {
        propositions = new ArrayList();
    }

    public boolean ajouter(Proposition p) {
        ConnectionRequest request = new ConnectionRequest();
        
        request.setUrl(URI);
        request.setHttpMethod("POST");

        request.addArgument("description", p.getDescription());
        request.addArgument("etat", p.getEtat());
        request.addArgument("id_question", String.valueOf(p.getId_question()));

        request.addResponseListener((evt) -> {
            responseResult = request.getResponseCode() == 201; // Code HTTP 201 OK
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return responseResult;
    }

    public boolean modifier(Proposition p) {
        ConnectionRequest request = new ConnectionRequest();
        
        request.setUrl(URI + p.getId_proposition());
        request.setHttpMethod("PUT");

        request.addArgument("description", p.getDescription());
        request.addArgument("etat",p.getEtat());
        request.addArgument("id_question", String.valueOf(p.getId_question()));

        request.addResponseListener((evt) -> {
            responseResult = request.getResponseCode() == 200; // Code HTTP 200 OK
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return responseResult;
    }

    public boolean supprimer(Proposition p) {
        ConnectionRequest request = new ConnectionRequest();
        
        request.setUrl(URI + p.getId_proposition());
        request.setHttpMethod("DELETE");

        request.addResponseListener((evt) -> {
            responseResult = request.getResponseCode() == 200; // Code HTTP 200 OK
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return responseResult;
    }

    public List<Proposition> afficher() {
        ConnectionRequest request = new ConnectionRequest();
        
        request.setUrl(URI + "/selectproposition");
        request.setHttpMethod("GET");

        request.addResponseListener((evt) -> {
            try {
                InputStreamReader jsonText = new InputStreamReader(new ByteArrayInputStream(request.getResponseData()), "UTF-8");
                Map<String, Object> result = new JSONParser().parseJSON(jsonText);
                List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("root");

                for (Map<String, Object> obj : list) {
                    int id_proposition = (int) Float.parseFloat(obj.get("id_proposition").toString());
                    String description = obj.get("description").toString();
                    String etat = obj.get("etat").toString();
                    int id_question = (int) Float.parseFloat(obj.get("id_question").toString());
                    propositions.add(new Proposition(id_proposition, description, etat, id_question));
                }

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return propositions;
    }
    
    
    
   public List<Proposition> getPropositionsByIdQuestion(int idQuestion) {
    List<Proposition> propositionByQuestion = new ArrayList<>();
    
    List<Proposition> allPropositions = afficher(); 
    
    for (Proposition prop : allPropositions) {
        if (prop.getId_question() == idQuestion) {
            propositionByQuestion.add(prop);
        }
    }
    
    return propositionByQuestion;
    
}   
   
   
   
   public String getTruePropositionByQuestionId (int IdQuestion){
       String correct = "";
       List<Proposition> allPropositions = getPropositionsByIdQuestion(IdQuestion);
       for (Proposition prop : allPropositions) {
        if (prop.getEtat().equals("vrai")){
            correct = prop.getDescription();
        }
       }
        return correct;
       
   }
}