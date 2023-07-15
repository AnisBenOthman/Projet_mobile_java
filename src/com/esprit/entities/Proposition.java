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
public class Proposition {
    private int id_proposition;
    private String description;
    private String etat;
    private int id_question;

    public Proposition(int id_proposition, String description, String etat, int id_question) {
        this.id_proposition = id_proposition;
        this.description = description;
        this.etat = etat;
        this.id_question = id_question;
    }

    public Proposition(String description, String etat, int id_question) {
        this.description = description;
        this.etat = etat;
        this.id_question = id_question;
    }

    public int getId_proposition() {
        return id_proposition;
    }

    public void setId_proposition(int id_proposition) {
        this.id_proposition = id_proposition;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public int getId_question() {
        return id_question;
    }

    public void setId_question(int id_question) {
        this.id_question = id_question;
    }

    @Override
    public String toString() {
        return "proposition{" + "id_proposition=" + id_proposition + ", description=" + description + ", etat=" + etat + ", id_question=" + id_question + '}';
    }

   
    
  
}

