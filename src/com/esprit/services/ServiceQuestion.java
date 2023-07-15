/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.services;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.esprit.entities.Question;
import com.esprit.utils.Statics;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


import com.codename1.db.Cursor;
import com.codename1.db.Database;
import com.codename1.db.Row;
import com.codename1.db.RowExt;
import com.codename1.io.Log;
import com.codename1.ui.Display;

/**
 *
 * @author abdel
 */
public class ServiceQuestion implements IService<Question> {

    private boolean responseResult;
    private List<Question> questions;
    
    private final String URI = Statics.BASE_URL + "/user";

    public ServiceQuestion() {
        questions = new ArrayList();
    }

    public boolean ajouter(Question q) {
        ConnectionRequest request = new ConnectionRequest();
        
        request.setUrl(URI);
        request.setHttpMethod("POST");

        request.addArgument("libelle", q.getLibelle());
        request.addArgument("etat_question", q.getEtat_question());
        request.addArgument("id_c", String.valueOf(q.getId_c()));

        request.addResponseListener((evt) -> {
            responseResult = request.getResponseCode() == 201; // Code HTTP 201 OK
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return responseResult;
    }

    public boolean modifier(Question q) {
        ConnectionRequest request = new ConnectionRequest();
        
        request.setUrl(URI + q.getId_question());
        request.setHttpMethod("PUT");

        request.addArgument("libelle", q.getLibelle());
        request.addArgument("etat_question",q.getEtat_question());

        request.addResponseListener((evt) -> {
            responseResult = request.getResponseCode() == 200; // Code HTTP 200 OK
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return responseResult;
    }

    public boolean supprimer(Question q) {
        ConnectionRequest request = new ConnectionRequest();
        
        request.setUrl(URI + q.getId_question());
        request.setHttpMethod("DELETE");

        request.addResponseListener((evt) -> {
            responseResult = request.getResponseCode() == 200; // Code HTTP 200 OK
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return responseResult;
    }

    public List<Question> afficher() {
        ConnectionRequest request = new ConnectionRequest();
        
        request.setUrl(URI + "/selectquestion" );
        request.setHttpMethod("GET");

        request.addResponseListener((evt) -> {
            try {
                InputStreamReader jsonText = new InputStreamReader(new ByteArrayInputStream(request.getResponseData()), "UTF-8");
                Map<String, Object> result = new JSONParser().parseJSON(jsonText);
                List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("root");

                for (Map<String, Object> obj : list) {
                    int id_question = (int) Float.parseFloat(obj.get("id_question").toString());
                    String libelle = obj.get("libelle").toString();
                    String etat_question = obj.get("etat_question").toString();
                    int id_c = (int) Float.parseFloat(obj.get("id_c").toString());
                    questions.add(new Question(id_question, libelle, etat_question, id_c));
                }

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return questions;
    }
    
    
    
  public List<Question> getQuestionsByIdCompetence(int idCompetence) {
    List<Question> questionsByCompetence = new ArrayList<>();
    
    List<Question> allQuestions = afficher(); 
    
    for (Question question : allQuestions) {
        if (question.getId_c() == idCompetence) {
            questionsByCompetence.add(question);
        }
    }
    
    return questionsByCompetence;
}  
    
    
}