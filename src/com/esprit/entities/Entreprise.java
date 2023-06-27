/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.entities;

/**
 *
 * @author Anis
 */
public class Entreprise extends User {
    
    private String NomEntreprise;
    private Taille TailleEntreprise;
    private String SiteWeb;
    private String Linkedin;
    private int id_domaine;

    public Entreprise( int id, String nom, String prenom, String mail, Integer numero_telephone, String motdepasse, String description, String NomEntreprise, Taille TailleEntreprise, String SiteWeb, String Linkedin, int id_domaine) throws MailException {
        super(id, nom, prenom, mail, numero_telephone, motdepasse, description);
        this.NomEntreprise = NomEntreprise;
        this.TailleEntreprise = TailleEntreprise;
        this.SiteWeb = SiteWeb;
        this.Linkedin = Linkedin;
        this.id_domaine = id_domaine;
         if (!emailvalidator(mail)) {
            throw new MailException("Mail invalide");
        }

        if (numero_telephone.toString().length() != 8) {
            throw new MailException("Le numéro de téléphone doit contenir 8 chiffres");
        }
        if (motdepasse.length() < 8) {
            throw new MailException("Le mot de passe ne doit pas être inferieur à 8 caractéres");
        }
    }

    public Entreprise(String nom, String prenom, String mail, Integer numero_telephone, String motdepasse, String description, String NomEntreprise, Taille TailleEntreprise, String SiteWeb, String Linkedin, int id_domaine) throws MailException {
        super(nom, prenom, mail, numero_telephone, motdepasse, description);
        this.NomEntreprise = NomEntreprise;
        this.TailleEntreprise = TailleEntreprise;
        this.SiteWeb = SiteWeb;
        this.Linkedin = Linkedin;
        this.id_domaine = id_domaine;
         if (!emailvalidator(mail)) {
            throw new MailException("Mail invalide");
        }

        if (numero_telephone.toString().length() != 8) {
            throw new MailException("Le numéro de téléphone doit contenir 8 chiffres");
        }
        if (motdepasse.length() < 8) {
            throw new MailException("Le mot de passe ne doit pas être inferieur à 8 caractéres");
        }
    }

    public Taille getTailleEntreprise() {
        return TailleEntreprise;
    }

    public void setTailleEntreprise(Taille TailleEntreprise) {
        this.TailleEntreprise = TailleEntreprise;
    }

    public String getSiteWeb() {
        return SiteWeb;
    }

    public void setSiteWeb(String SiteWeb) {
        this.SiteWeb = SiteWeb;
    }

    public String getLinkedin() {
        return Linkedin;
    }

    public void setLinkedin(String Linkedin) {
        this.Linkedin = Linkedin;
    }

    public int getId_domaine() {
        return id_domaine;
    }

    public void setId_domaine(int id_domaine) {
        this.id_domaine = id_domaine;
    }

    

    public String getNomEntreprise() {
        return NomEntreprise;
    }

    public void setNomEntreprise(String NomEntreprise) {
        this.NomEntreprise = NomEntreprise;
    }

    @Override
    public String toString() {
        return "Entreprise{" + super.toString() + "NomEntreprise=" + NomEntreprise + ", TailleEntreprise=" + TailleEntreprise + ", SiteWeb=" + SiteWeb + ", Linkedin=" + Linkedin + ", id_domaine=" + id_domaine + '}';
    }
    
   

    

    

    

    
    
    
}
