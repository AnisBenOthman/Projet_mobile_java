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
import com.codename1.ui.layouts.GridLayout;
import com.esprit.entities.Candidat;
import com.esprit.entities.Diplome;
import com.esprit.entities.Experience;
import com.esprit.entities.MailException;
import com.esprit.services.ServiceUser;

/**
 *
 * @author Anis
 */
public class AjoutCandidat extends Form {

    private Button btn;
    private TextField nom;
    private TextField prenom;
    private TextField mail;
    private TextField telephone;
    private TextField motdepasse;
    private TextField motdepasse2;
    private ComboBox<Diplome> diplome;
    private ComboBox<Experience> experience;

    public AjoutCandidat() {
        super("Inscription Candidat", BoxLayout.y());
        OnGui();
        AddAction();
    }

    public void OnGui() {
        nom = new TextField(null, "Nom");
        prenom = new TextField(null, "Prénom");
        mail = new TextField(null, "Mail");
        telephone = new TextField(null, "Téléphone");
        motdepasse = new TextField(null, "Password", CENTER, TextField.PASSWORD);
        motdepasse2 = new TextField(null, "Confirmed password", CENTER, TextField.PASSWORD);
        diplome = new ComboBox<>();
        diplome.addItem(Diplome.Autre);
        diplome.addItem(Diplome.Bacclauréat);
        diplome.addItem(Diplome.Ingénierie);
        diplome.addItem(Diplome.Licence);
        diplome.addItem(Diplome.Mastére);
        experience = new ComboBox<>();
        experience.addItem(Experience.Junior);
        experience.addItem(Experience.Confirme);
        experience.addItem(Experience.Senior);
        btn = new Button("Ajouter");
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        addAll(nom, prenom, telephone, mail, diplome, experience, motdepasse, motdepasse2, btn);
        GridLayout layout = new GridLayout(1, 1);
        setLayout(layout);

    }

    public void AddAction() {
        ServiceUser su = new ServiceUser();
        btn.addActionListener((l) -> {
            try {
                su.ajouter(new Candidat(diplome.getSelectedItem(), experience.getSelectedItem(), nom.getText(), prenom.getText(), mail.getText(), Integer.parseInt(telephone.getText()), motdepasse.getText()));
            } catch (MailException ex) {
                System.err.println(ex.getMessage());
            }
            Dialog.show("Confirmation d'ajout", "Inscription reussie", "OK", null);
        });

    }

}
