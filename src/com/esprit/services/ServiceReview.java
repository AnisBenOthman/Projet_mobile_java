package com.esprit.services;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.esprit.entities.Review;
import com.esprit.utils.Statics;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServiceReview implements IService<Review> {

    private boolean responseResult;
    private List<Review> reviews;

    private final String URI = Statics.BASE_URL + "/review/";

    public ServiceReview() {
        reviews = new ArrayList<>();
    }

    @Override
    public boolean ajouter(Review review) {
        // Create a new review by sending a POST request to the server
        ConnectionRequest request = new ConnectionRequest();
        request.setUrl(URI);
        request.setHttpMethod("POST");

        request.addArgument("nbr_etoile", String.valueOf(review.getNbr_etoile()));
        request.addArgument("id_user", String.valueOf(review.getId_user()));
        request.addArgument("commentaire", review.getCommentaire());
        request.addArgument("id_entreprise", String.valueOf(review.getId_entreprise()));

        request.addResponseListener((evt) -> {
            responseResult = request.getResponseCode() == 201; // HTTP 201 Created
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return responseResult;
    }

    @Override
    public boolean modifier(Review review) {
        // Update an existing review by sending a PUT request to the server
        ConnectionRequest request = new ConnectionRequest();
        request.setUrl(URI + review.getId_review());
        request.setHttpMethod("PUT");

        request.addArgument("nbr_etoile", String.valueOf(review.getNbr_etoile()));
        request.addArgument("commentaire", review.getCommentaire());

        request.addResponseListener((evt) -> {
            responseResult = request.getResponseCode() == 200; // HTTP 200 OK
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return responseResult;
    }

    @Override
    public boolean supprimer(Review review) {
        // Delete a review by sending a DELETE request to the server
        ConnectionRequest request = new ConnectionRequest();
        request.setUrl(URI + review.getId_review());
        request.setHttpMethod("DELETE");

        request.addResponseListener((evt) -> {
            responseResult = request.getResponseCode() == 200; // HTTP 200 OK
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return responseResult;
    }

    @Override
    public List<Review> afficher() {
        // Retrieve and return a list of all reviews from the server
        ConnectionRequest request = new ConnectionRequest();
        request.setUrl(URI);
        request.setHttpMethod("GET");

        request.addResponseListener((evt) -> {
            try {
                InputStreamReader jsonText = new InputStreamReader(new ByteArrayInputStream(request.getResponseData()), "UTF-8");
                Map<String, Object> result = new JSONParser().parseJSON(jsonText);
                List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("root");

                for (Map<String, Object> obj : list) {
                    int idReview = (int) Float.parseFloat(obj.get("id_review").toString());
                    int nbrEtoile = (int) Float.parseFloat(obj.get("nbr_etoile").toString());
                    int idUser = (int) Float.parseFloat(obj.get("id_user").toString());
                    String commentaire = obj.get("commentaire").toString();
                    int idEntreprise = (int) Float.parseFloat(obj.get("id_entreprise").toString());

                    Review rev = new Review(idReview, nbrEtoile, idUser, commentaire, idEntreprise);
                    reviews.add(rev);
                }

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);

        return reviews;
    }
}
