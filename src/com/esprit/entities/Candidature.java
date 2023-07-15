/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.entites;

import java.util.Date;


/**
 *
 * @author ASUS
 */
public class Candidature {
    private int id_user;
    private int id_offre;
    private int id_candidature;
    private Date date_condidature;
    private EtatCandidature etat;

    public Candidature(int id_user, int id_offre) {
        this.id_user = id_user;
        this.id_offre = id_offre;
        this.etat = EtatCandidature.En_Cour;
    }

    public Candidature(int id_user, int id_offre, int id_candidature, Date date_condidature, EtatCandidature etat) {
        this.id_user = id_user;
        this.id_offre = id_offre;
        this.id_candidature = id_candidature;
        this.date_condidature = date_condidature;
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

    public int getId_candidature() {
        return id_candidature;
    }

    public void setId_candidature(int id_candidature) {
        this.id_candidature = id_candidature;
    }

    public Date getDate_condidature() {
        return date_condidature;
    }

    public void setDate_condidature(Date date_condidature) {
        this.date_condidature = date_condidature;
    }

    public EtatCandidature getEtat() {
        return etat;
    }

    public void setEtat(EtatCandidature etat) {
        this.etat = etat;
    }

    @Override
    public String toString() {
        return "Candidature{" + "id_user=" + id_user + ", id_offre=" + id_offre + ", id_candidature=" + id_candidature + ", date_condidature=" + date_condidature + ", etat=" + etat.toString() + '}';
    }

   

   

  

  
    
    
}
 