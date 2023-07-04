
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.gui;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.esprit.entities.CodeGenerator;
import com.esprit.services.ServiceUser;

/**
 *
 * @author Anis
 */
public class VerificationCode extends Form{
    
    private Button envoyer;
    private TextField tf;
    public VerificationCode(String code, String mail) {
        super("Vérification code", BoxLayout.y());
        OnGui();
        AddAction(code, mail);
    }
    public void OnGui(){
        SpanLabel lb = new SpanLabel("Nous avons envoyé un code de vérification à votre adresse E-mail ");
        SpanLabel lb1 = new SpanLabel("Saisisez le code de vérification à 6 chiffres envoyé à votre adresse e-mail");
        SpanLabel lb2 = new SpanLabel("Si vous ne recevez pas l'email, vérifiez  dans les courriels indésirables de votre messagerie");
        tf = new TextField(null, "code de vérification");
        envoyer = new Button("envoyer");
        addAll(lb,lb1,tf,envoyer,lb2);
    }
    
    public void AddAction(String code, String mail){
        
       envoyer.addActionListener((l) -> {
           String cd = tf.getText();
           if (cd.equals(code)){
               new Modifiermotdepasse(mail).show();
           }
       });
         
    }
    
    
    
}
