/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.gui;

import com.codename1.ui.Command;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.Layout;
import com.esprit.entities.MailException;
import java.io.IOException;


/**
 *
 * @author Anis
 */
public class Menubar extends Form {

    public Menubar(String title, Layout l) {
        super(title, l);
        addActions();
    }

    public void addActions() {
        getToolbar().addMaterialCommandToSideMenu("Home", FontImage.MATERIAL_HOME, e -> {
            new Home().show();
        });
        getToolbar().addMaterialCommandToSideMenu("Quiz", FontImage.MATERIAL_QUIZ, e -> {
            new HomeForm().show();
        });
        
        getToolbar().addMaterialCommandToSideMenu("Document", FontImage.MATERIAL_DOCUMENT_SCANNER, e -> {
            new DocumentHome().show();
        });
        
        getToolbar().addMaterialCommandToSideMenu("Offre", FontImage.MATERIAL_BOOKMARK, e -> {
            new Offrehome().show();
        });
        
         getToolbar().addMaterialCommandToSideMenu("Review", FontImage.MATERIAL_CABLE, e -> {
            new Reviewhome().show();
        });
         
         getToolbar().addMaterialCommandToSideMenu("Forum", FontImage.MATERIAL_FORUM, e -> {
            new AfficherForumsForm().show();
        });
         
          getToolbar().addMaterialCommandToSideMenu("Candidat", FontImage.MATERIAL_PAGES, e -> {
            try {
                new Affichertouscandidat().show();
            } catch (IOException ex) {
                
            } catch (MailException ex) {
               
            }
        });
          
          getToolbar().addMaterialCommandToSideMenu("Entreprise", FontImage.MATERIAL_ANCHOR, e -> {
            try {
                new Affichertoutesentreprise().show();
            } catch (IOException ex) {
                
            } catch (MailException ex) {
               
            }
        });
          
          

    }

    
}
