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
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.list.ContainerList;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.list.MultiList;
import com.esprit.entities.Candidat;
import com.esprit.entities.MailException;
import com.esprit.services.ServiceUser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Separator;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Anis
 */
//


public class Affichertouscandidat extends Menubar {
    ServiceUser su = new ServiceUser();
    Container ct = new Container(BoxLayout.x());
    TextField tf = new TextField(null, "Rechercher");
    Button bt = new Button("OK");
    Toolbar tb;
    List<Candidat> ls;
    public Affichertouscandidat() throws IOException, MailException {
        super("Liste des Candidats", BoxLayout.y());
        ct = new Container(new BorderLayout()); 
        tf = new TextField(null, "Rechercher"); 
        bt = new Button("OK");
        
        ct.addComponent(BorderLayout.CENTER, tf);
        ct.addComponent(BorderLayout.EAST, bt);
        tb = new Toolbar();
        setToolBar(tb);
        tb.setTitleComponent(ct);

        addActions();
        
        OnGui();
        bt.addActionListener((l) -> {
            if (!tf.getText().isEmpty()) {
                

                try {
                    ls = su.SearchCandidat(tf.getText());
                    this.removeAll();
                    

                    for (Candidat c : ls) {
                        try {
                            this.add(createContenaire(c));
                              
                        } catch (IOException ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                    
                    
                     this.revalidate();
                  
                    

                } catch (MailException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            else {
                try {
                    this.removeAll();
                    OnGui();
                    this.revalidate();
                } catch (MailException ex) {
                    
                } catch (IOException ex) {
                    
                }
            }
        });
    
        
    }
    
    public void OnGui() throws MailException, IOException{
        List<Candidat> lc = su.afficherCandidat();
        for (Candidat c : lc) {
            add(createContenaire(c));

        }
    }

    private Container createContenaire(Candidat c) throws IOException {
        ServiceUser su = new ServiceUser();
        Container container = new Container(BoxLayout.y());
        Button btn = new Button("details");
        btn.putClientProperty("mail", c.getMail());
        btn.addActionListener((l) -> {
            String mail = btn.getClientProperty("mail").toString();
            try {
                new AfficherCandidat(su.afficherseulCandidat(mail)).show();
                System.out.println(mail);
            } catch (MailException ex) {

            } catch (IOException ex) {

            }
        });
        Label lb = new Label("-----------------------");
        lb.getAllStyles().setAlignment(Component.CENTER);
        ImageViewer image = new ImageViewer(Image.createImage("/utilisateur.png").scaled(200, 200));
        SpanLabel nom = new SpanLabel("Nom :" + c.getNom());
        SpanLabel prenom = new SpanLabel("Prénom : " + c.getPrenom());
        SpanLabel telephone = new SpanLabel("Téléphone : " + String.valueOf(c.getNumero_telephone()));
        SpanLabel mail = new SpanLabel("Mail :" + c.getMail());
        SpanLabel diplome = new SpanLabel("Diplome :" + String.valueOf(c.getEducation()));
        SpanLabel experience = new SpanLabel("Experience :" + String.valueOf(c.getExperience()));
        Container cn = new Container(BoxLayout.y());
        cn.addAll(nom, prenom, telephone, mail, diplome, experience, lb);
        Container c1 = new Container(BoxLayout.x());
        c1.addAll(image, cn, btn);
        return container.add(c1);
    }

}
