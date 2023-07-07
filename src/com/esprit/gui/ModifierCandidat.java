/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.gui;

import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.esprit.entities.Candidat;
import com.esprit.entities.Diplome;
import com.esprit.entities.Experience;
import com.esprit.entities.MailException;
import com.esprit.entities.User;
import com.esprit.services.ServiceUser;
import java.io.IOException;
import java.util.List;




/**
 *
 * @author Anis
 */
public class ModifierCandidat extends Form {

    private Button btn;
    private TextField nom;
    private TextField prenom;
    private TextField mail;
    private TextField telephone;
    private TextField motdepasse;
    private TextField motdepasse2;
    private ComboBox<Diplome> diplome;
    private ComboBox<Experience> experience;
    List<Candidat> list;

    public ModifierCandidat(Candidat t) throws MailException {

        super(" Modification des information Candidat", BoxLayout.y());
        OnGui(t);
        AddAction(t);
    }

    public void OnGui(Candidat t) throws MailException {
        nom = new TextField(t.getNom().toString(),"Nom");
        prenom = new TextField(t.getPrenom().toString(),"Prénom");
        mail = new TextField(t.getMail().toString(),"mail");
        telephone = new TextField(t.getNumero_telephone().toString(),"mail");
        motdepasse = new TextField(null,"Password",LEFT,TextField.PASSWORD);
        motdepasse2 = new TextField(null,"Confirmed Password",LEFT,TextField.PASSWORD);
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
        btn = new Button("Modifier");
        
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        addAll(nom, prenom, telephone, mail, diplome, experience, motdepasse, motdepasse2, btn);

    }

    public void AddAction(Candidat t) {
        ServiceUser su = new ServiceUser();
        btn.addActionListener((l) -> {

            if (nom.getText().isEmpty() || prenom.getText().isEmpty() || mail.getText().isEmpty() || telephone.getText().isEmpty() || motdepasse.getText().isEmpty() || experience.getSelectedItem() == null || diplome.getSelectedItem() == null) {
                Dialog.show("Champs vide", "Champs obligatoire à remplir!", "OK", null);
                return;
            }

            
            try {
                list = su.afficherCandidat();
            } catch (MailException ex) {
                
            }

//            Boolean candidatexiste = false;
//            for (Candidat u : list) {
//                if (u.getMail().equals(mail.getText()) || u.getNumero_telephone() == Integer.parseInt(telephone.getText())) {
//                    candidatexiste = true;
//                    break;
//                }
//
//            }
//            if (candidatexiste == true) {
//                Dialog.show("Alerte", "Candidat existe déja!", "OK", null);
//                return;
             if (telephone.getText().length() != 8) {
                Dialog.show("Alerte", "numero téléphone invalide", "OK", null);
                return;
            } else if (motdepasse.getText().length() < 8) {
                Dialog.show("Alerte", "mot de passe invalide, il doit contenir au moins 8 caractéres", "OK", null);
                return;
            } else if (!motdepasse.getText().equals(motdepasse2.getText())) {
                Dialog.show("Alerte", "Mot de passe non conforme!", "OK", null);
                return;
            } else if (!User.emailvalidator(mail.getText())) {
                Dialog.show("Alerte", "Adresse mail invalide!", "OK", null);
                return;
            } else {
                try {
                    su.modifier(new Candidat(t.getId(), nom.getText(), prenom.getText(), mail.getText(), Integer.parseInt(telephone.getText()), motdepasse.getText(), "description", diplome.getSelectedItem(), "git", experience.getSelectedItem()));
                    if ( su.modifier(new Candidat(t.getId(), nom.getText(), prenom.getText(), mail.getText(), Integer.parseInt(telephone.getText()), motdepasse.getText(), "description", diplome.getSelectedItem(), "git", experience.getSelectedItem()))) {
                        Dialog.show("Alerte", "Modification reussi", "OK", null);
                        System.out.println(motdepasse.getText());
                    } else {
                        Dialog.show("Alerte", "echec", "OK", null);
                    }
                } catch (MailException ex) {
                    System.out.println(ex.getMessage());
                }
                try {
                    new AfficherCandidat(su.afficherseulCandidat(mail.getText())).show();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                } catch (MailException ex) {
                    
                }
            }

        });

    }

}
