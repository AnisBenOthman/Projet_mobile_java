/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.layouts.BoxLayout;
import com.esprit.services.ServiceUser;
import com.esprit.services.ServiceUser.Entreprisedomaine;
import java.io.IOException;

/**
 *
 * @author Anis
 */
public class AfficherEntreprise extends Menubar{

    public AfficherEntreprise(Entreprisedomaine e) throws IOException {
        super("Entreprise" + " " + e.getNomEntreprise(), BoxLayout.y());
        OnGui(e);
        AddAction();
        
    }

    public void OnGui(Entreprisedomaine e) throws IOException {
        ImageViewer image = new ImageViewer(Image.createImage("/utilisateur.png"));
        SpanLabel nomentreprise = new SpanLabel("Nom de l'entreprise :" + e.getNomEntreprise());
        SpanLabel mail = new SpanLabel("mail :" + e.getMail());
        SpanLabel taille = new SpanLabel("Taille de l'entreprise :" + String.valueOf(e.getTailleEntreprise()));
        SpanLabel Secteur = new SpanLabel("Secteur de l'entreprise :"  + String.valueOf(e.getNom_domaine()));
        Container c = new Container(BoxLayout.y());
        c.addAll(nomentreprise,mail,taille,Secteur);
        Container c1 = new Container(BoxLayout.x());
        c1.addAll(image,c);
        add(c1);
        
    }

    public void AddAction() {
        
    }
    
}
