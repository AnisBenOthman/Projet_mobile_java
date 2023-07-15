/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.services;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.esprit.entites.Offre;
import com.esprit.utils.Statics;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


/**
 *
 * @author abdel
 */
public class ServiceOffre {
    private boolean responseResult;
    private List<Offre> offres;
    private Offre offrechercher = null;
    private final String URI = Statics.BASE_URL + "/offre/";

    public ServiceOffre() {
        offres = new ArrayList();
    }
    
    public boolean ajouter(Offre f) {
        ConnectionRequest request = new ConnectionRequest();
        
        request.setUrl(URI);
        request.setHttpMethod("POST");

        request.addArgument("titre", f.getTitre());
        request.addArgument("description", f.getDescription());
        request.addArgument("id_domaine", String.valueOf(f.getId_domaine()));
        request.addArgument("id_entreprise",String.valueOf(f.getId_entreprise()));
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(f.getDate_Expiration());
        request.addArgument("date_expiration", formattedDate);

        request.addResponseListener((evt) -> {
            responseResult = request.getResponseCode() == 201; // Code HTTP 201 OK
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return responseResult;
    }
    
    
    public boolean modifier(Offre f) {
        ConnectionRequest request = new ConnectionRequest();
        
        request.setUrl(URI + f.getId_offre());
        request.setHttpMethod("PUT");

        request.addArgument("titre", f.getTitre());
        request.addArgument("description", f.getDescription());
        request.addArgument("id_domaine", String.valueOf(f.getId_domaine()));
        request.addArgument("id_entreprise",String.valueOf(f.getId_entreprise()));
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDateoff = sdf.format(f.getDate_Expiration());
        request.addArgument("date_offre", formattedDateoff);
        
       
        String formattedDateexp = sdf.format(f.getDate_Expiration());
        request.addArgument("date_expiration", formattedDateexp);
        
        request.addResponseListener((evt) -> {
            responseResult = request.getResponseCode() == 200; // Code HTTP 200 OK
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return responseResult;
    }

   
    public List<Offre> afficher() {
        ConnectionRequest request = new ConnectionRequest();
        
        request.setUrl(URI);
        request.setHttpMethod("GET");

        request.addResponseListener((evt) -> {
            try {
                InputStreamReader jsonText = new InputStreamReader(new ByteArrayInputStream(request.getResponseData()), "UTF-8");
                Map<String, Object> result = new JSONParser().parseJSON(jsonText);
                List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("root");

                for (Map<String, Object> obj : list) {
                    int id = (int) Float.parseFloat(obj.get("id_offre").toString());
                    String nom = obj.get("titre").toString();
                    String prenom = obj.get("description").toString();
                    int iddomaine = (int) Float.parseFloat(obj.get("id_domaine").toString());
                    int identreprise = (int) Float.parseFloat(obj.get("id_entreprise").toString());
                    
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Specify the date format according to your input

                    try {
                        Date dateoffre = dateFormat.parse(obj.get("date_offre").toString());
                        Date dateexp = dateFormat.parse(obj.get("date_expiration").toString());
                        
                        offres.add(new Offre(id, nom, prenom,iddomaine,identreprise,dateoffre,dateexp));


                    } catch (ParseException e) {
                        System.out.println(e.getMessage());
                    }
                    
//                    Date dateoffre = new Date(obj.get("date_offre").toString()) ;
//                    Date dateexp =  new Date(obj.get("date_expiration").toString());
                }

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return offres;
    }
    
    
    public List<Offre> afficherByDomaine(String domaineChercher) {
        ConnectionRequest request = new ConnectionRequest();
        ServiceDomaine sd = new ServiceDomaine();
        request.setUrl(URI);
        request.setHttpMethod("GET");   

        request.addResponseListener((evt) -> {
            try {
                InputStreamReader jsonText = new InputStreamReader(new ByteArrayInputStream(request.getResponseData()), "UTF-8");
                Map<String, Object> result = new JSONParser().parseJSON(jsonText);
                List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("root");

                for (Map<String, Object> obj : list) {
                    int id = (int) Float.parseFloat(obj.get("id_offre").toString());
                    String nom = obj.get("titre").toString();
                    String prenom = obj.get("description").toString();
                    int iddomaine = (int) Float.parseFloat(obj.get("id_domaine").toString());
                    int identreprise = (int) Float.parseFloat(obj.get("id_entreprise").toString());
                    String nomdomaine = sd.getDomaineById(iddomaine).getNom_domaine();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Specify the date format according to your input

                    try {
                        Date dateoffre = dateFormat.parse(obj.get("date_offre").toString());
                        Date dateexp = dateFormat.parse(obj.get("date_expiration").toString());
                        if(domaineChercher.equals(nomdomaine)){
                             offres.add(new Offre(id, nom, prenom,iddomaine,identreprise,dateoffre,dateexp));
                        }
                       


                    } catch (ParseException e) {
                        System.out.println(e.getMessage());
                    }
                    
//                    Date dateoffre = new Date(obj.get("date_offre").toString()) ;
//                    Date dateexp =  new Date(obj.get("date_expiration").toString());
                }

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return offres;
    }
    
    
    public boolean supprimer(Offre t) {
        ConnectionRequest request = new ConnectionRequest();
        
        request.setUrl(URI + t.getId_offre());
        request.setHttpMethod("DELETE");

        request.addResponseListener((evt) -> {
            responseResult = request.getResponseCode() == 200; // Code HTTP 200 OK
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return responseResult;
    }
    
    public boolean supprimerParId(int id) {
        ConnectionRequest request = new ConnectionRequest();
        
        request.setUrl(URI +id);
        request.setHttpMethod("DELETE");

        request.addResponseListener((evt) -> {
            responseResult = request.getResponseCode() == 200; // Code HTTP 200 OK
            System.out.println("supprimer");
        });
        NetworkManager.getInstance().addToQueueAndWait(request);
        
        return responseResult;
    }
    
    
    public Offre afficherOffreByID(int idoff) {
        ConnectionRequest request = new ConnectionRequest();
        
        request.setUrl(URI);
        request.setHttpMethod("GET");

        request.addResponseListener((evt) -> {
            try {
                InputStreamReader jsonText = new InputStreamReader(new ByteArrayInputStream(request.getResponseData()), "UTF-8");
                Map<String, Object> result = new JSONParser().parseJSON(jsonText);
                List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("root");

                for (Map<String, Object> obj : list) {
                    int id = (int) Float.parseFloat(obj.get("id_offre").toString());
                    String titre = obj.get("titre").toString();
                    String desc = obj.get("description").toString();
                    int iddomaine = (int) Float.parseFloat(obj.get("id_domaine").toString());
                    int identreprise = (int) Float.parseFloat(obj.get("id_entreprise").toString());
                    
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Specify the date format according to your input

                    try {
                        Date dateoffre = dateFormat.parse(obj.get("date_offre").toString());
                        Date dateexp = dateFormat.parse(obj.get("date_expiration").toString());
                        
                        if(id == idoff){
                        offrechercher = new Offre(id, titre, desc,iddomaine,identreprise,dateoffre,dateexp);
                        }
                    } catch (ParseException e) {
                        System.out.println(e.getMessage());
                    }
                     
                }

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);
        return offrechercher;
    }
}
