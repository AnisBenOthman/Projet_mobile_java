package com.esprit.entities;

public class Review {
    private int id_review;
    private int nbr_etoile;
    private String commentaire;
    private int id_entreprise;
    private int id_user;

    public Review(int id_review, int nbr_etoile, int id_user, String commentaire, int id_entreprise) {
        this.id_review = id_review;
        this.nbr_etoile = nbr_etoile;
        this.id_user = id_user;
        this.commentaire = commentaire;
        this.id_entreprise = id_entreprise;
    }

    public Review(int nbr_etoile, int id_user, String commentaire, int id_entreprise) {
        this.nbr_etoile = nbr_etoile;
        this.id_user = id_user;
        this.commentaire = commentaire;
        this.id_entreprise = id_entreprise;
    }

    public int getId_review() {
        return id_review;
    }

    public void setId_review(int id_review) {
        this.id_review = id_review;
    }

    public int getNbr_etoile() {
        return nbr_etoile;
    }

    public void setNbr_etoile(int nbr_etoile) {
        this.nbr_etoile = nbr_etoile;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public int getId_entreprise() {
        return id_entreprise;
    }

    public void setId_entreprise(int id_entreprise) {
        this.id_entreprise = id_entreprise;
    }
 


    @Override
    public String toString() {
        return "Review [id_review=" + id_review + ", nbr_etoile=" + nbr_etoile + ", commentaire=" + commentaire
                + ", id_entreprise=" + id_entreprise + ", id_user=" + id_user + "]";
    }
}
