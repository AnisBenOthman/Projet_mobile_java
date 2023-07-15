package com.esprit.services;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.esprit.entities.EtatReclamation;
import com.esprit.entities.Reclamation;
import com.esprit.utils.Statics;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServiceReclamation implements IService<Reclamation> {

    private boolean responseResult;
    private List<Reclamation> reclamations;

    private final String URI = Statics.BASE_URL + "/reclamation/";

    public ServiceReclamation() {
        reclamations = new ArrayList<>();
    }

    public boolean ajouter(Reclamation r) {
        ConnectionRequest request = new ConnectionRequest();

        request.setUrl(URI);
        request.setHttpMethod("POST");

        request.addArgument("reclamation", r.getReclamation());
      request.addArgument("id_user", String.valueOf(r.getId_user()));
 //  request.addArgument("etat", r.getEtat().name());
        request.addArgument("id_offre", String.valueOf(r.getId_offre()));

        request.addResponseListener((evt) -> {
            responseResult = request.getResponseCode() == 201; // Code HTTP 201 OK
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return responseResult;
    }

    public boolean modifier(Reclamation r) {
        ConnectionRequest request = new ConnectionRequest();

        request.setUrl(URI + r.getId_reclamation());
        request.setHttpMethod("PUT");

        request.addArgument("reclamation", r.getReclamation());
       request.addArgument("etat", r.getEtat().name());

        request.addResponseListener((evt) -> {
            responseResult = request.getResponseCode() == 200; // Code HTTP 200 OK
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return responseResult;
    }

    public boolean supprimer(Reclamation r) {
        ConnectionRequest request = new ConnectionRequest();

        request.setUrl(URI + r.getId_reclamation());
        request.setHttpMethod("DELETE");

        request.addResponseListener((evt) -> {
            responseResult = request.getResponseCode() == 200; // Code HTTP 200 OK
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return responseResult;
    }

    public List<Reclamation> afficher() {
        ConnectionRequest request = new ConnectionRequest();

        request.setUrl(URI);
        request.setHttpMethod("GET");

        request.addResponseListener((evt) -> {
            try {
                InputStreamReader jsonText = new InputStreamReader(new ByteArrayInputStream(request.getResponseData()), "UTF-8");
                Map<String, Object> result = new JSONParser().parseJSON(jsonText);
                List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("root");

                for (Map<String, Object> obj : list) {
                    int idReclamation = (int) Float.parseFloat(obj.get("id_reclamation").toString());
                    String reclamation = obj.get("reclamation").toString();
                    int idUser = (int) Float.parseFloat(obj.get("id_user").toString());
                    String etat = obj.get("etat").toString();
                    int idOffre = (int) Float.parseFloat(obj.get("id_offre").toString());

                    Reclamation rec = new Reclamation(idReclamation, reclamation, idUser, EtatReclamation.valueOf(etat), idOffre);
                    reclamations.add(rec);
                }

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return reclamations;
    }
}
