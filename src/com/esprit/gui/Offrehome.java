/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;

/**
 *
 * @author ASUS
 */
public class Offrehome extends Menubar{
     private Button btnAddPerson;
    private Button btnShowPerson;

    public Offrehome() {
        super("Home", BoxLayout.y());
        OnGui();
        AddActions();
    }
    
    private void OnGui() {
        btnAddPerson = new Button("Ajouter");
        btnShowPerson = new Button("Afficher");
        this.addAll(new Label("Choisissez une option :"), btnAddPerson, btnShowPerson);
    }
    
    private void AddActions() {
        btnAddPerson.addActionListener((evt) -> {
            new AjouterOffreForm(this).show();
        });
        btnShowPerson.addActionListener((evt) -> {
            new AfficherOffreForm(this).show();
        });
    }
}
