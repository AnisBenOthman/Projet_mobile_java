/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.Layout;
import com.esprit.entities.Candidat;
import com.esprit.entities.MailException;
import com.esprit.services.ServiceUser;
import com.esprit.services.ServiceUser.Entreprisedomaine;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Anis
 */
public class Affichertoutesentreprise extends Menubar{

    public Affichertoutesentreprise() throws MailException, IOException {
        super("Liste des entreprise", BoxLayout.y());
        ServiceUser su = new ServiceUser();
        List<ServiceUser.Entreprisedomaine> lc = su.afficherentreprise();
        for (Entreprisedomaine c : lc) {
            add(createContenaire(c));

        }
    }

    public Container createContenaire(Entreprisedomaine c) throws IOException {
        ServiceUser su = new ServiceUser();
        Container container = new Container(BoxLayout.y());
        ImageViewer image = new ImageViewer(Image.createImage("/utilisateur.png").scaled(200, 200));
        Button btn = new Button("details");
        btn.putClientProperty("mail", c.getMail());
        btn.addActionListener((l) -> {
            String mail = btn.getClientProperty("mail").toString();
            try {
                new AfficherEntreprise(su.Seulentreprise(mail)).show();
                System.out.println(mail);
            } catch (MailException ex) {
                System.out.println(ex.getMessage());
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        });
        Label lb = new Label("--------");
        lb.getAllStyles().setAlignment(Component.CENTER);
        SpanLabel nomentreprise = new SpanLabel("Nom de l'entreprise :" + c.getNomEntreprise());
        SpanLabel mail = new SpanLabel("mail :" + c.getMail());
        SpanLabel taille = new SpanLabel("Taille de l'entreprise :" + String.valueOf(c.getTailleEntreprise()));
        SpanLabel Secteur = new SpanLabel("Secteur de l'entreprise :" + String.valueOf(c.getNom_domaine()));
        Container cn = new Container(BoxLayout.y());
        cn.addAll(nomentreprise, mail, taille, Secteur,btn,lb);
        Container c1 = new Container(BoxLayout.x());
        c1.addAll(image,cn);
        
        return container.add(c1);
    }
    
    
    
    
}
