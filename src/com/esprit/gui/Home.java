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
import javafx.scene.control.Alert;

/**
 *
 * @author Anis
 */
public class Home extends Form {

    private Button btnidentifier;
    private Button btnmpoublier;
    private Button btninscription;
    private TextField tflogin;
    private TextField tfmp;

    public Home() {
        super("login", BoxLayout.y());
        OnGui();
        AddAction();
    }

    public void OnGui() {
        btnidentifier = new Button("s'identifier");
        btninscription = new Button("Nouveau sur FindJob ? S'inscrire");
        btnmpoublier = new Button("mot de passe oublié");
        tflogin = new TextField(null, "login");
        tfmp = new TextField(null, "Password", LEFT, TextField.PASSWORD);
        this.addAll(tflogin, tfmp, btnidentifier, btnmpoublier, btninscription);
    }

    public void AddAction() {
        btnidentifier.addActionListener((l) -> {
            ServiceUser su = new ServiceUser();
            if (tflogin.getText().isEmpty() || tfmp.getText().isEmpty()) {
            Dialog.show("Alerte", "Veillez remplir tous les champs", "OK", null);
        }
            if (su.login(tflogin.getText(), tfmp.getText()))
            {
                if (su.idutilisateur(tflogin.getText()).equals("Candidat")) {
                    new AfficherCandidat().show();
                } else if (su.idutilisateur(tflogin.getText()).equals("Entreprise")) {
                    new AfficherEntreprise().show();
                }
            }

        });
        
        btnmpoublier.addActionListener((b) -> {
            new MotdepasseOublier().show();
        });
        
        btninscription.addActionListener((a) -> {
            new Inscription().show();
        });
    }

   

}
