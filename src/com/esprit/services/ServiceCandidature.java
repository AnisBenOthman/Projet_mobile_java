/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.services;


import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.esprit.entites.Candidature;
import com.esprit.utils.Statics;

/**
 *
 * @author ASUS
 */
public class ServiceCandidature {
    private boolean responseResult;
    private final String URI = Statics.BASE_URL + "/candidature/";

    
    public ServiceCandidature() {  
    }
    
    
    
    public boolean ajouter(Candidature f) {
        System.out.println("hello from  1");
        ConnectionRequest request = new ConnectionRequest();
        
        request.setUrl(URI);
        request.setHttpMethod("POST");

        request.addArgument("id_user",String.valueOf( f.getId_user()));
        request.addArgument("id_offre",String.valueOf(f.getId_offre()));
                System.out.println("hello from  2");

        request.addResponseListener((evt) -> {
            responseResult = request.getResponseCode() == 201; // Code HTTP 201 OK
        });
        NetworkManager.getInstance().addToQueueAndWait(request);
                System.out.println("hello from  3");

        return responseResult;
    }
    
    
}
