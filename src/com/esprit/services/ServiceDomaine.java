/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.services;


import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.esprit.entities.Domaine;
import com.esprit.utils.Statics;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ASUS
 */
public class ServiceDomaine {
    private boolean responseResult;
    private List<String> NomDomaines;
    private final String URI = Statics.BASE_URL + "/domaine/";
    Domaine d = null ;
    
    public ServiceDomaine() {
        NomDomaines = new ArrayList();
    }
    
    public List<String> afficherNomDomaine() {
        ConnectionRequest request = new ConnectionRequest();
        
        request.setUrl(URI);
        request.setHttpMethod("GET");

        request.addResponseListener((evt) -> {
            try {
                InputStreamReader jsonText = new InputStreamReader(new ByteArrayInputStream(request.getResponseData()), "UTF-8");
                Map<String, Object> result = new JSONParser().parseJSON(jsonText);
                List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("root");

                for (Map<String, Object> obj : list) {
                    
                    String nomd = obj.get("nom_domaine").toString();
                    NomDomaines.add(nomd);
                  
                }

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return NomDomaines;
    }
    
    
    
    public Domaine getDomaineByNom(String nom) {
        ConnectionRequest request = new ConnectionRequest();
        request.setUrl(URI);
        request.setHttpMethod("GET");
        
        request.addResponseListener((evt) -> {
            try {
                InputStreamReader jsonText = new InputStreamReader(new ByteArrayInputStream(request.getResponseData()), "UTF-8");
                Map<String, Object> result = new JSONParser().parseJSON(jsonText);
                List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("root");

                for (Map<String, Object> obj : list) {
                    int id = (int) Float.parseFloat(obj.get("id_domaine").toString());
                    String nomd = obj.get("nom_domaine").toString();
                    if (nomd.equals(nom)) { 
                        // Check if the nom_domaine matches the requested nom
                        d = new Domaine(id, nomd);
                        break;
                    }
                }
                

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return  d;
    }
    
    
    
    public Domaine getDomaineById(int id) {
        ConnectionRequest request = new ConnectionRequest();
        request.setUrl(URI);
        request.setHttpMethod("GET");
        
        request.addResponseListener((evt) -> {
            try {
                InputStreamReader jsonText = new InputStreamReader(new ByteArrayInputStream(request.getResponseData()), "UTF-8");
                Map<String, Object> result = new JSONParser().parseJSON(jsonText);
                List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("root");

                for (Map<String, Object> obj : list) {
                    int idd = (int) Float.parseFloat(obj.get("id_domaine").toString());
                    String nomd = obj.get("nom_domaine").toString();
                    if (id == idd) { 
                        // Check if the nom_domaine matches the requested nom
                        d = new Domaine(idd, nomd);
                        break;
                    }
                }
                

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return  d;
    }
    
}
