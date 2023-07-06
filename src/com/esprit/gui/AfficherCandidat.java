/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.esprit.entities.Candidat;
import java.io.IOException;
import javafx.scene.image.ImageView;

/**
 *
 * @author Anis
 */
public class AfficherCandidat extends Menubar {

    public AfficherCandidat(Candidat can) throws IOException {

        super("Candidat" + " " + can.getNom(), BoxLayout.y());
        OnGui(can);
        AddAction();
    }

    public void OnGui(Candidat can) throws IOException {

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
        add(c1);

    }

    public void AddAction() {

    }

}
