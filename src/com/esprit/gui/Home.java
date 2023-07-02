/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.gui;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.esprit.entities.MailException;
import com.esprit.services.ServiceUser;
import java.io.IOException;
import javafx.scene.control.Alert;

/**
 *
 * @author Anis
 */
public class Home extends Menubar {

    private Button btnidentifier;
    private Button btnmpoublier;
    private Button btninscription;
    private TextField tflogin;
    private TextField tfmp;
    private String link = "http://localhost:8080/mobile/findjob.PNG";
    private EncodedImage placeHolder;

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
        try {
            placeHolder = EncodedImage.create("/findjob.png");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        Image img = URLImage.createToStorage(placeHolder, link, link, URLImage.RESIZE_SCALE_TO_FILL).scaled(400, 400);
        
        this.addAll(new ImageViewer(img),tflogin, tfmp, btnidentifier, btnmpoublier, btninscription);
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
                    
                        new Inscription().show();
                    
                } else if (su.idutilisateur(tflogin.getText()).equals("Entreprise")) {
                    new MotdepasseOublier().show();
                }
            }
            else {
                Dialog.show("Alerte", "Identifiant ou mot de passe non valide", "OK", null);
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
