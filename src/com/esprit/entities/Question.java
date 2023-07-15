/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.entities;
import java.util.Objects;
/**
 *
 * @author Mayssen
 */
public class Question {
    private int id_question;
    private String libelle;
    private String etat_question;
    private int id_c;

    public Question(int id_question, String libelle, String etat_question, int id_c) {
        this.id_question = id_question;
        this.libelle = libelle;
        this.etat_question = etat_question;
        this.id_c = id_c;
    }

    public Question(String libelle,int id_c) {
        this.libelle = libelle;
        this.etat_question = "Active";
        this.id_c = id_c;
    }

    public int getId_question() {
        return id_question;
    }

    public void setId_question(int id_question) {
        this.id_question = id_question;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getEtat_question() {
        return etat_question;
    }

    public void setEtat_question(String etat_question) {
        this.etat_question = etat_question;
    }

    public int getId_c() {
        return id_c;
    }

    public void setId_c(int id_c) {
        this.id_c = id_c;
    }

    @Override
    public String toString() {
        return "Question{" + "id_question=" + id_question + ", libelle=" + libelle + ", etat_question=" + etat_question + ", id_c=" + id_c + '}';
    }
}
