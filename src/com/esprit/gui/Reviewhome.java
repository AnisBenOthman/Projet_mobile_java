package com.esprit.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.esprit.entities.Review;
import com.esprit.services.ServiceReview;

public class Reviewhome extends Menubar {
    private Button btnAddReclamation;
    private Button btnShowReclamations;
    private Button btnAddReview;
    private Button btnShowReviews;

    public Reviewhome() {
        super("Home", BoxLayout.y());
        setupUI();
        AddActions();
    }

    private void setupUI() {
        btnAddReclamation = new Button("Ajouter Réclamation");
        btnShowReclamations = new Button("Afficher Réclamations");
        btnAddReview = new Button("Ajouter Review");
        btnShowReviews = new Button("Afficher Reviews");

        addAll(new Label("Choisissez une option :"), btnAddReclamation, btnShowReclamations, btnAddReview, btnShowReviews);
    }

    private void AddActions() {
        btnAddReclamation.addActionListener((evt) -> {
            int id_user = 1; // Replace with the actual id_user value from your database
            int id_offre = 5; // Replace with the actual id_offre value from your database
            new AjoutReclamationForm(this, id_user, id_offre).show();
        });

        btnShowReclamations.addActionListener((evt) -> {
            new AfficheReclamationsForm(this).show();
        });

        btnAddReview.addActionListener((evt) -> {
            new AjoutReviewForm(this).show();
        });
        

        btnShowReviews.addActionListener((evt) -> {
            new AfficheReviewsForm(this).show();
        });
    }
}
