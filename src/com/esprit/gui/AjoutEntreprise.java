/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.gui;

import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.esprit.entities.Domaine;
import com.esprit.entities.Taille;
import com.esprit.services.ServiceUser;
import java.util.Vector;

/**
 *
 * @author Anis
 */
public class AjoutEntreprise extends Menubar{
    
    private Button bnt;
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
        motdepasse = new TextField(null, "Password", CENTER, TextField.PASSWORD);
        motdepasse2 = new TextField(null, "Confirmed Password", CENTER, TextField.PASSWORD);
        addAll(NomEntreprise,mail,taille,domaine,motdepasse,motdepasse2,bnt);
        
        
    }
    
    public void AddAction(){
        
        
    }
    
    
    
}
