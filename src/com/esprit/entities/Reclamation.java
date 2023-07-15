package com.esprit.entities;

public class Reclamation {

    private int id_reclamation;
    private String reclamation;
    private EtatReclamation etat;
    private String output;

    private int id_offre;
    private int id_user;

    public Reclamation(int id_reclamation, String reclamation, int id_user, EtatReclamation etat, int id_offre) {
        this.id_reclamation = id_reclamation;
        this.reclamation = reclamation;
        this.id_user = id_user;
        this.etat = etat;
        this.id_offre = id_offre;
    }

  

    public int getId_reclamation() {
        return id_reclamation;
    }

    public void setId_reclamation(int id_reclamation) {
        this.id_reclamation = id_reclamation;
    }

    public String getReclamation() {
        return reclamation;
    }

    public void setReclamation(String reclamation) {
        this.reclamation = reclamation;
    }

    public EtatReclamation getEtat() {
        return etat;
    }

    public void setEtat(EtatReclamation etat) {
        this.etat = etat;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_offre() {
        return id_offre;
    }

    public void setId_offre(int id_offre) {
        this.id_offre = id_offre;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    @Override
    public String toString() {
        return output;
    }
}
