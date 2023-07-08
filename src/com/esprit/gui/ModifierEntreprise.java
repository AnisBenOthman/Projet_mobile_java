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
import com.esprit.entities.Candidat;
import com.esprit.entities.Entreprise;
import com.esprit.entities.MailException;
import com.esprit.entities.Taille;
import com.esprit.entities.User;
import com.esprit.services.ServiceUser;
import java.io.IOException;
import java.util.List;
import java.util.Vector;



/**
 *
 * @author Anis
 */
public class ModifierEntreprise extends Form{
    private Button modifier;
    private TextField NomEntreprise;
    private TextField mail;
    private TextField motdepasse;
    private TextField motdepasse2;
    private ComboBox<Taille> taille;
    private ComboBox<String> domaine;
    private Vector<String> list;
    public ModifierEntreprise(Entreprise e) {

        super(" Modification des information Entreprise", BoxLayout.y());
        OnGui(e);
        AddAction(e);
    
    }

    private void OnGui(Entreprise e) {
      ServiceUser su = new ServiceUser();
        list = new Vector<>(su.afficherDomainebynom());
        NomEntreprise = new TextField(e.getNomEntreprise().toString(),"Nom de l'entreprise");
        mail = new TextField(e.getMail().toString(), "mail");
        taille = new ComboBox();
        domaine = new ComboBox<>(list);
        taille.addItem(Taille.DE_1_A_10_EMPLOYES);
        taille.addItem(Taille.DE_11_A_50_EMPLOYES);
        taille.addItem(Taille.PLUS_DE_50_EMPLOYES);
        modifier = new Button("Modifier");
        motdepasse = new TextField(null, "Password", CENTER, TextField.PASSWORD);
        motdepasse2 = new TextField(null, "Confirmed Password", CENTER, TextField.PASSWORD);
        addAll(NomEntreprise,mail,taille,domaine,motdepasse,motdepasse2,modifier);  
    }

    private void AddAction(Entreprise e) {
        
        ServiceUser su = new ServiceUser();
        modifier.addActionListener((l) -> {
            try {
                if (NomEntreprise.getText().isEmpty() || mail.getText().isEmpty() || motdepasse.getText().isEmpty() || taille.getSelectedItem() == null || domaine.getSelectedItem() == null) {
                    Dialog.show("Champs vide", "Champs obligatoire à remplir!", "OK", null);
                    
                    return;
                }
                
                List<ServiceUser.Entreprisedomaine> list = su.afficherentreprise();

                Boolean entreprisexiste = false;
                for (ServiceUser.Entreprisedomaine u : list) {
                    if (u.getMail().equals(mail.getText())) {
                        entreprisexiste = true;
                        break;
                    }
                

                }
                if (entreprisexiste == true) {
                    Dialog.show("Alerte", "Entreprise existe déja!", "OK", null);
                    return;
                }  else if (motdepasse.getText().length() < 8) {
                    Dialog.show("Alerte", "mot de passe invalide, il doit contenir au moins 8 caractéres", "OK", null);
                    return;
                } else if (!motdepasse.getText().equals(motdepasse2.getText())) {
                    Dialog.show("Alerte", "Mot de passe non conforme!", "OK", null);
                    return;
                }else if(!User.emailvalidator(mail.getText())){
                    Dialog.show("Alerte", "Adresse mail invalide!", "OK", null);
                    return; 
                }
                else {
                    
                    su.modifier(new Entreprise(e.getId(),"Foulen", "ben Foulen", mail.getText(), 71122546, motdepasse.getText(), "description", NomEntreprise.getText(), taille.getSelectedItem(), "SiteWeb", "Linkedin",su.getIdDomaineByName(domaine.getSelectedItem())));
                    if(su.modifier(new Entreprise(e.getId(),"Foulen", "ben Foulen", mail.getText(), 71122546, motdepasse.getText(), "description", NomEntreprise.getText(), taille.getSelectedItem(), "SiteWeb", "Linkedin",su.getIdDomaineByName(domaine.getSelectedItem())))){
                      Dialog.show("Confirmation", "Modification réussie", "OK", null);  
                    }
                    
                    new AfficherEntreprise(su.Seulentreprise(mail.getText())).showBack();
                }
            } catch (MailException ex) {
                System.err.println(ex.getMessage());
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        });
        
    }
    
    
    
}
