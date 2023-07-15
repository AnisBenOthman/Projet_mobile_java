/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.esprit.entites.Candidature;
import com.esprit.entites.Offre;
import com.esprit.services.ServiceCandidature;
import com.esprit.services.ServiceDomaine;
import com.esprit.services.ServiceMail;
import com.esprit.services.ServiceMail0;
import com.esprit.services.ServiceOffre;
import javax.mail.MessagingException;

/**
 *
 * @author ASUS
 */
public class AfficherDetailsOffre extends Form {
    
    Label titre;
    Label desc;
    Label domaine;
    Label entreprise;
    Label dateOffre;
    Label dateExp;
    int id_user = 1;
    ServiceOffre serOff = new ServiceOffre();
     private Form previousForm;
     Offre offre = null;
    ServiceDomaine sd = new ServiceDomaine();
    ServiceCandidature sc = new ServiceCandidature();
    public AfficherDetailsOffre(Form f ,int id) {
        super("Affichage", BoxLayout.y());
        previousForm = f;
        Offre off = serOff.afficherOffreByID(id);
        
       
       titre = new Label("titre : "+off.getTitre());
       desc = new Label("description : "+off.getDescription());
       domaine = new Label("domaine : "+sd.getDomaineById(off.getId_domaine()).getNom_domaine());
       entreprise = new Label("entreprise : "+off.getId_entreprise());
       dateOffre = new Label("dateOffre :"+off.getDate_offre());
       dateExp = new Label("Date Expiration : "+off.getDate_Expiration());
//       offre = new Offre(idoffre, titre.getText(), desc.getText(), Integer.parseInt(domaine.getText()), Integer.parseInt(entreprise.getText()));
        Button postulerBtn = new Button("Postuler");
        postulerBtn.addActionListener(l->{
//            sc.ajouter(new Candidature(id_user, off.getId_offre()));
//           Dialog.show("SUCCESS", "Candidature Envoyer !", "OK", null);
//            new AfficherOffreForm(this).show();

        if (Dialog.show("Confirmation", "Êtes-vous sûr de vouloir postuler cet offre ?", "OK", "Annuler")) {
            sc.ajouter(new Candidature(id_user, off.getId_offre()));
            
//            String recepient =u.getMail();
            String object="Candidature "+off.getTitre();
            String message="Cher(e) ali ,Votre candidature pour le poste "+off.getTitre() +" a été enregistrée avec succès. Je vous remercie pour votre soutien tout au long du processus.";
            try {
                ServiceMail0.sendMail("samaousxx@gmail.com", object,message);
            } catch (MessagingException ex) {
                System.out.println(ex.getMessage());
            }
            
            Dialog.show("SUCCESS", "Candidature Envoyer !", "OK", null);
            new AfficherOffreForm(this).show();
        } else {
            System.out.println(" ");
        }
            
        });
        
        
        
        Button supprimerBtn = new Button("Supprimer");
        supprimerBtn.addActionListener(l->{
            serOff.supprimerParId(id);
           
            new AfficherOffreForm(this).show();
            Dialog.show("SUCCESS", "Offre Supprimer !", "OK", null);
        });
        Button modifierBtn = new Button("Modifier");
        
        modifierBtn.addActionListener(l->{
           new ModifierOffeForm(this, off).show();

        });
        
        Container cBtn = new Container(BoxLayout.x());
        cBtn.addAll(postulerBtn,supprimerBtn,modifierBtn);
        addAll(titre,desc,domaine,entreprise,dateOffre,dateExp,cBtn);
               
            addActions() ;    
    }

    

    private void addActions() {
        this.getToolbar().addCommandToLeftBar("Return", null, (evt) -> {
            new AfficherOffreForm(this).show(); 
        });
        
        
    }
}
