/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.gui;

import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.esprit.entites.Offre;
import com.esprit.services.ServiceDomaine;
import com.esprit.services.ServiceOffre;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class AfficherOffreForm extends Menubar{
    static List<Offre> offres = new ArrayList<>();
    private Form previousForm;
    ServiceDomaine sd = new ServiceDomaine();
    
    
    private ComboBox<String> domaines = new ComboBox<>();
    List<String> listDomaine = sd.afficherNomDomaine();
    
    String domaineChercher = "ALL";
    
    public AfficherOffreForm(Form f) {
        super("Affichage", BoxLayout.y());
        offres = new ServiceOffre().afficher();
        
        
        domaines.addItem("ALL");
        for (String item : listDomaine) {
            domaines.addItem(item);
        }
        
        domaines.addActionListener(evt -> {
            domaineChercher = domaines.getSelectedItem();
            System.out.println("----------"+domaineChercher);
            removeAll();
            
            if (domaineChercher.equals("ALL")) {
                offres = new ServiceOffre().afficher();
            } else {
                offres = new ServiceOffre().afficherByDomaine(domaineChercher);
            }
            this.add(domaines);
            for (Offre off : offres) {
                try {
                    add(addOffre(off));
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            
            revalidate();
            repaint();
        });
        
        
        
        previousForm = f;
        this.add(domaines);
        
        for (Offre off : offres) {
            try {
                add(addOffre(off));
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        
        AddActions();
    }
 
    
   
    public Container addOffre(Offre f) throws IOException {
        
        Container c = new Container(BoxLayout.x());
        Container c1 = new Container(BoxLayout.y());
        Label titrelb = new Label("Titre :"+f.getTitre());
        Label datelb = new Label("Date :"+f.getDate_offre());
        Label line = new Label("----------------------------------------------");
        Button voirbtn = new Button("voir");
        voirbtn.putClientProperty("offreid",String.valueOf(f.getId_offre()));
        
        voirbtn.addActionListener(e -> {

            int  id = Integer.parseInt(voirbtn.getClientProperty("offreid").toString());

            voirOffreDetails(id);
                System.out.println(id);
        });
        
        
        c1.addAll(titrelb, datelb,line);
        c.addAll(c1 ,voirbtn);
        
        
        return c;
    }
    
     private void AddActions() {
        this.getToolbar().addCommandToLeftBar("Return", null, (evt) -> {
            previousForm.showBack();
        });
        this.getToolbar().addCommandToRightBar("Ajouter", null, (evt) -> {
            new AjouterOffreForm(this).show();
        });
        
    }

    private void voirOffreDetails(int id) {
        new AfficherDetailsOffre(this,id).show();
    }
    
}
