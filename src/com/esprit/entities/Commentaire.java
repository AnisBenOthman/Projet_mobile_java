/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.entities;

/**
 *
 * @author Acer
 */
public class Commentaire {
    private int id_commentaire;
    private String contenu;
    private int id_forum;
    private int id_user;

    public Commentaire(String contenu, int id_forum, int id_user) {
        this.contenu = contenu;
        this.id_forum = id_forum;
        this.id_user = id_user;
    }

    public Commentaire(int id_commentaire, String contenu, int id_forum, int id_user) {
        this.id_commentaire = id_commentaire;
        this.contenu = contenu;
        this.id_forum = id_forum;
        this.id_user = id_user;
    }

    public int getId_commentaire() {
        return id_commentaire;
    }

    public void setId_commentaire(int id_commentaire) {
        this.id_commentaire = id_commentaire;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public int getId_forum() {
        return id_forum;
    }

    public void setId_forum(int id_forum) {
        this.id_forum = id_forum;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    @Override
    public String toString() {
        return "Commentaire{" + "id_commentaire=" + id_commentaire + ", contenu=" + contenu + ", id_forum=" + id_forum + ", id_user=" + id_user + '}';
    }  
}
