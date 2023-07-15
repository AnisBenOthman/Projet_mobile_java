/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.entities;

/**
 *
 * @author Acer
 */
public class Forum {
    private int id_forum;
    private String sujet;
    private String contenu;
    private int id_user;
    private int id_domaine;

    public Forum(String sujet, String contenu, int id_user, int id_domaine) {
        this.sujet = sujet;
        this.contenu = contenu;
        this.id_user = id_user;
        this.id_domaine = id_domaine;
    }

    public Forum(int id_forum, String sujet, String contenu, int id_user, int id_domaine) {
        this.id_forum = id_forum;
        this.sujet = sujet;
        this.contenu = contenu;
        this.id_user = id_user;
        this.id_domaine = id_domaine;
    }

    public int getId_forum() {
        return id_forum;
    }

    public void setId_forum(int id_forum) {
        this.id_forum = id_forum;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_domaine() {
        return id_domaine;
    }

    public void setId_domaine(int id_domaine) {
        this.id_domaine = id_domaine;
    }

    @Override
    public String toString() {
        return "Forum{" + "id_forum=" + id_forum + ", sujet=" + sujet + ", contenu=" + contenu + ", id_user=" + id_user + ", id_domaine=" + id_domaine + '}';
    }

    
    
}
