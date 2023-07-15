package com.esprit.services;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.esprit.entities.Document;
import com.esprit.utils.Statics;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServiceGererDocument {
    public boolean responseResult;
    public List<Document> documents;

    public final String URI = Statics.BASE_URL + "/document/";

    public ServiceGererDocument() {
        documents = new ArrayList<>();
    }

    public boolean ajouter(Document document) {
        ConnectionRequest request = new ConnectionRequest();

        request.setUrl(URI);
        request.setHttpMethod("POST");

        request.addArgument("titre_document", document.getTitre_document());
        request.addArgument("description_document", document.getDescription_document());
        request.addArgument("type", document.getType());
        request.addArgument("lien", document.getLien());
        request.addArgument("id_user", String.valueOf(document.getId_user()));

        request.addResponseListener((evt) -> {
            responseResult = request.getResponseCode() == 201; // HTTP 201 CREATED
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return responseResult;
    }

    public boolean modifier(Document document) {
        ConnectionRequest request = new ConnectionRequest();

        request.setUrl(URI + document.getId_document());
        request.setHttpMethod("PUT");

        request.addArgument("titre_document", document.getTitre_document());
        request.addArgument("description_document", document.getDescription_document());
        request.addArgument("type", document.getType());
        request.addArgument("lien", document.getLien());
        request.addArgument("id_user", String.valueOf(document.getId_user()));

        request.addResponseListener((evt) -> {
            responseResult = request.getResponseCode() == 200; // HTTP 200 OK
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return responseResult;
    }

    public boolean supprimer(Document document) {
        ConnectionRequest request = new ConnectionRequest();

        request.setUrl(URI + document.getId_document());
        request.setHttpMethod("DELETE");

        request.addResponseListener((evt) -> {
            responseResult = request.getResponseCode() == 200; // HTTP 200 OK
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return responseResult;
    }

    public List<Document> afficher() {
        ConnectionRequest request = new ConnectionRequest();

        request.setUrl(URI);
        request.setHttpMethod("GET");

        request.addResponseListener((evt) -> {
            try {
                InputStreamReader jsonText = new InputStreamReader(new ByteArrayInputStream(request.getResponseData()), "UTF-8");
                Map<String, Object> result = new JSONParser().parseJSON(jsonText);
                List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("root");

                for (Map<String, Object> obj : list) {
                    int id_document = (int) Double.parseDouble(obj.get("id_document").toString());
                    String titre_document = obj.get("titre_document").toString();
                    String description_document = obj.get("description_document").toString();
                    String type = obj.get("type").toString();
                    String lien = obj.get("lien").toString();
                    int id_user = (int) Double.parseDouble(obj.get("id_user").toString());

                    documents.add(new Document(id_document, titre_document, description_document, type, lien, id_user));
                }

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return documents;
    }
    public void clearDocuments() {
    documents.clear();
}

}
