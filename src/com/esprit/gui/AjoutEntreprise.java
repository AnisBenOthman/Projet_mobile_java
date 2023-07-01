/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.gui;

import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.esprit.entities.Candidat;
import com.esprit.entities.Domaine;
import com.esprit.entities.Entreprise;
import com.esprit.entities.MailException;
import com.esprit.entities.Taille;
import com.esprit.entities.User;
import com.esprit.services.*;
import com.esprit.services.ServiceUser.Entreprisedomaine;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author Anis
 */
public class AjoutEntreprise extends Menubar{
    
    private Button bnt;
    private Button retour;
    private TextField NomEntreprise;
    private TextField mail;
    private TextField motdepasse;
    private TextField motdepasse2;
    private ComboBox<Taille> taille;
    private ComboBox<String> domaine;
    private Vector<String> list;
    
    public AjoutEntreprise() {
        super("Inscription Entreprise", BoxLayout.y());
        OnGui();
        AddAction();
    }
    
    public void OnGui(){
        ServiceUser su = new ServiceUser();
        list = new Vector<>(su.afficherDomainebynom());
        NomEntreprise = new TextField(null,"Nom de l'entreprise");
        mail = new TextField(null, "mail");
        taille = new ComboBox();
        domaine = new ComboBox<>(list);
        taille.addItem(Taille.DE_1_A_10_EMPLOYES);
        taille.addItem(Taille.DE_11_A_50_EMPLOYES);
        taille.addItem(Taille.PLUS_DE_50_EMPLOYES);
        bnt = new Button("Ajouter");
        retour = new Button("Retour");
        motdepasse = new TextField(null, "Password", CENTER, TextField.PASSWORD);
        motdepasse2 = new TextField(null, "Confirmed Password", CENTER, TextField.PASSWORD);
        addAll(NomEntreprise,mail,taille,domaine,motdepasse,motdepasse2,bnt,retour);
        
        
    }
    
    public void AddAction(){
        ServiceUser su = new ServiceUser();
        bnt.addActionListener((l) -> {
            try {
                if (NomEntreprise.getText().isEmpty() || mail.getText().isEmpty() || motdepasse.getText().isEmpty() || taille.getSelectedItem() == null || domaine.getSelectedItem() == null) {
                    Dialog.show("Champs vide", "Champs obligatoire à remplir!", "OK", null);
                    return;
                }
                
                List<Entreprisedomaine> list = su.afficherentreprise();

                Boolean entreprisexiste = false;
                for (Entreprisedomaine u : list) {
                    if (u.getMail().equals(mail.getText())) {
                        entreprisexiste = true;
                        break;
                    }
                

                }
                if (entreprisexiste == true) {
                    Dialog.show("Alerte", "Entreprise existe déja!", "OK", null);
                    return;
                }  else if (motdepasse.getText().length() < 8) {
                    Dialog.show("Alerte", "mot de passe invalide, il doit contenir au moins 8 caractéres", "OK", null);
                    return;
                } else if (!motdepasse.getText().equals(motdepasse2.getText())) {
                    Dialog.show("Alerte", "Mot de passe non conforme!", "OK", null);
                    return;
                }else if(!User.emailvalidator(mail.getText())){
                    Dialog.show("Alerte", "Adresse mail invalide!", "OK", null);
                    return; 
                }
                else {
                    
                    su.ajouter(new Entreprise("Foulen", "ben Foulen", mail.getText(), 71122546, motdepasse.getText(), "description", NomEntreprise.getText(), taille.getSelectedItem(), "SiteWeb", "Linkedin",su.getIdDomaineByName(domaine.getSelectedItem())));
                    Dialog.show("Confirmation d'ajout", "Inscription reussie", "OK", null);
                    new Home().showBack();
                }
            } catch (MailException ex) {
                System.err.println(ex.getMessage());
            }
        });
        retour.addActionListener((l) -> { 
            new Inscription().showBack();
            
        });
        
    }
    
    
    
}
