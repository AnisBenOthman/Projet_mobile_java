/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.entities;

/**
 *
 * @author Acer
 */
public class React {
    private int id_react;
    private boolean liked;
    private int id_Commentaire;
    private int id_user;

    public React(boolean liked, int id_Commentaire, int id_user) {
        this.liked = liked;
        this.id_Commentaire = id_Commentaire;
        this.id_user = id_user;
    }

    public React(int id_react, boolean liked, int id_Commentaire, int id_user) {
        this.id_react = id_react;
        this.liked = liked;
        this.id_Commentaire = id_Commentaire;
        this.id_user = id_user;
    }

    public int getId_react() {
        return id_react;
    }

    public void setId_react(int id_react) {
        this.id_react = id_react;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public int getId_Commentaire() {
        return id_Commentaire;
    }

    public void setId_Commentaire(int id_Commentaire) {
        this.id_Commentaire = id_Commentaire;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    @Override
    public String toString() {
        return "React{" + "id_react=" + id_react + ", liked=" + liked + ", id_Commentaire=" + id_Commentaire + ", id_user=" + id_user + '}';
    }
    
    
}
