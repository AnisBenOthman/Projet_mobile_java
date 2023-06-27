/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.esprit.services.ServiceUser;

/**
 *
 * @author Anis
 */
public class Modifiermotdepasse extends Form{
    private TextField tf;
    private TextField tf1;
    private Button btn;
    public Modifiermotdepasse(String mail) {
        super("Modifier Mot de passe", BoxLayout.y());
        OnGui();
        AddAction(mail);
        
    }
    
    public void OnGui(){
        Label lb = new Label("Choisissez un mouveau mot de passe");
        Label lb1 = new Label("créer un nouveau mot de passe d'au moins 8 caractéres.");
        tf = new TextField(null, "Nouveau mot de passe");
        tf1 = new TextField(null, "Confirmez le nouveau mot de passe"); 
        btn = new Button("envoyer");
        addAll(lb,lb1,tf,tf1,btn);
    }
    
    public void AddAction(String mail){
        ServiceUser su = new ServiceUser();
        btn.addActionListener((l) -> {
            if (tf.getText().isEmpty() || tf1.getText().isEmpty()){
                Dialog.show("Alerte champs vide", "veuillez remplir les deux champs de mot de passe", "OK",null);
            }
            else if(tf.getText().equals(tf1.getText())){
                su.modifiermotdepasse(tf.getText(), mail);
               new Home().showBack(); 
            }
            else {
                Dialog.show("Alerte mot de passe", "les deux mots de passe ne sont pas conformes", "OK",null);
            }
            
        });
        
    }
    
    
    
}
