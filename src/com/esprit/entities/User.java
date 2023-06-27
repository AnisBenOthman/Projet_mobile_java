/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.entities;

import java.util.Objects;
import com.codename1.util.regex.RE;


/**
 *
 * @author Anis
 */
public class User {

    private int id;
    private String nom;
    private String prenom;
    private String mail;
    private Integer numero_telephone;
    private String motdepasse;
    private String description;

    
    public static int Codepasse(String motdepasse) {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(motdepasse);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        return true;
    }

    public User() {
    }
    
    
    
   public static boolean emailvalidator(String mail) {
    String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]+$";
    RE pattern = new RE(emailPattern);
    return pattern.match(mail);
}

   




    public User(int id, String nom, String prenom, String mail, Integer numero_telephone, String motdepasse, String description) throws MailException {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.numero_telephone = numero_telephone;
        this.motdepasse = String.valueOf(Codepasse(motdepasse));
        this.description = description;

    }

    public User(String nom, String prenom, String mail, Integer numero_telephone, String motdepasse, String description) throws MailException {
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.numero_telephone = numero_telephone;
        this.motdepasse = String.valueOf(Codepasse(motdepasse));
        this.description = description;
        this.numero_telephone = numero_telephone;
 
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Integer getNumero_telephone() {
        return numero_telephone;
    }

    public void setNumero_telephone(Integer numero_telephone) {
        this.numero_telephone = numero_telephone;
    }

    public String getMotdepasse() {
        return motdepasse;
    }

    public void setMotdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", mail=" + mail + ", numero_telephone=" + numero_telephone + ", motdepasse=" + motdepasse + ", description=" + description + '}';
    }

}
