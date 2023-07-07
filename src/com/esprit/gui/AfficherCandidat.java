/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.esprit.entities.Candidat;
import com.esprit.entities.MailException;
import com.esprit.services.ServiceUser;
import java.io.IOException;
import javafx.scene.image.ImageView;

/**
 *
 * @author Anis
 */
public class AfficherCandidat extends Menubar {
    private Button modifier;
    private Button supprimer;
    public AfficherCandidat(Candidat can) throws IOException {

        super("Candidat" + " " + can.getNom(), BoxLayout.y());
        OnGui(can);
        AddAction(can);
    }

    public void OnGui(Candidat can) throws IOException {
        modifier = new Button("Modifier");
        supprimer = new Button("Supprimer");
        ImageViewer image = new ImageViewer(Image.createImage("/utilisateur.png"));
        SpanLabel nom = new SpanLabel("Nom :" + can.getNom());
        SpanLabel prenom = new SpanLabel("Prénom : " + can.getPrenom());
        SpanLabel telephone = new SpanLabel("Téléphone : " + String.valueOf(can.getNumero_telephone()));
        SpanLabel mail = new SpanLabel("mail :" + can.getMail());
        SpanLabel diplome = new SpanLabel("Diplome :" + String.valueOf(can.getEducation()));
        SpanLabel experience = new SpanLabel("Experience :" + String.valueOf(can.getExperience()));
        Container c = new Container(BoxLayout.y());
        c.addAll(nom, prenom, telephone, mail, diplome, experience);
        Container c1 = new Container(BoxLayout.x());
        c1.addAll(image, c);
        addAll(c1,modifier,supprimer);

    }

    public void AddAction(Candidat can) {
        ServiceUser su = new ServiceUser();
        modifier.addActionListener((l) -> {
            try {
                new ModifierCandidat(can).show();
            } catch (MailException ex) {
                System.out.println(ex.getMessage()); 
            }
           
        });
        
        supprimer.addActionListener((l) -> {
            Dialog.show("Confirmation", "Êtes-vous sûr de vouloir supprimer ce candidat ?", "OK", "Annuler");
            if(Dialog.show("Confirmation", "Êtes-vous sûr de vouloir supprimer ce candidat ?", "OK", "Annuler")){
             su.supprimer(can);
             Dialog.show("Confirmation", "candidat supprimer", "OK",null);
             new Home().showBack();
            }
            
        });

    }

}
