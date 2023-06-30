/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.gui;

import com.codename1.ui.Button;
import static com.codename1.ui.Component.LEFT;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.esprit.services.ServiceUser;

/**
 *
 * @author Anis
 */
public class Inscription extends Menubar {

    private Button btnentreprise;
    private Button btncandidat;
    
    public Inscription() {
        super("Inscription", BoxLayout.y());
        OnGui();
        AddAction();
    }
    
     public void OnGui() {
        btncandidat = new Button("Candidat");
        btnentreprise = new Button("Entreprise");
        this.addAll(btncandidat,btnentreprise);
    }
     
      public void AddAction() {
        btncandidat.addActionListener((l) -> {
            
            new AjoutCandidat().show();
           
      

        });
        
        btnentreprise.addActionListener((b) -> {
            new AjoutEntreprise().show();
        });
        
        
    }

}
