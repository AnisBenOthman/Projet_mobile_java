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
import com.esprit.entities.CodeGenerator;
import com.esprit.services.ServiceMail;
import com.esprit.services.ServiceUser;

/**
 *
 * @author Anis
 */
public class MotdepasseOublier extends Form{
    
    private Button btnrinitialiser;
    private TextField tf;
    public MotdepasseOublier() {
        super("mot de passe oublié", BoxLayout.y());
        OnGui();
        AddAction();
    }
    public void OnGui(){
        Label lb = new Label("Mot de passe oublié ?");
        Label lb1 = new Label("Réinitialisez le mot de passe en deux étapes :");
        tf = new TextField(null,"email ou téléphone");
        btnrinitialiser = new Button("Réinitialiser");
        addAll(lb,lb1,tf,btnrinitialiser);
    }
    
    public void AddAction(){
         btnrinitialiser.addActionListener((l) -> {
             ServiceUser su = new ServiceUser();
             ServiceMail sm= new ServiceMail();
             if(tf.getText().isEmpty()){
                 Dialog.show("Alerte", "champs vide", "OK", null);
             }
             else if(su.loginpasse(tf.getText())){
                
                String code = CodeGenerator.generateCode();
                String subject = "Code de vérification";
                String body = "Bonjour,\n" +
                "\n" +
                "Nous avons reçu une demande de réinitialisation du mot de passe de votre compte Findjob.\n" +
                "\n" +
                code + "\n" +
                "Veuillez saisir ce code pour finaliser la réinitialisation.\n" +
                "\n" +
                "Merci de nous aider à maintenir la sécurité de votre compte.\n" +
                "\n" +
                "L'équipe Findjob\"";
                sm.sendMail(tf.getText(), subject, body);
                
                new VerificationCode(code,tf.getText()).show();
                
            } 
            else {
                
                  Dialog.show("Alerte", "Nous n'avons trouvé aucun compte associé à " + tf.getText() + "Veuillez essayer avec une adresse e-mail ou un numéro de téléphone alternatif", "OK",null);
            }
         });
    }
    
    
    
}
