/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.gui;

import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.esprit.entites.Offre;
import com.esprit.services.ServiceDomaine;
import com.esprit.services.ServiceOffre;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class ModifierOffeForm extends Form{
    
    private TextField tftitre;
    private TextField tfdescription;
    
    private Picker dateExp;
    private Picker datepub;
    Label lbDomaine;
    private ComboBox<String> domaine = new ComboBox<>();
    private Button btnAjout;
    
    private int iddomaine = 0;
    private int identre = 2;
    
    
    ServiceDomaine sd = new ServiceDomaine();
    List<String> listDomaine = sd.afficherNomDomaine();
    private Form previousForm;
    
    public ModifierOffeForm(Form f,Offre O) {
        super("Ajout", BoxLayout.y());
        previousForm = f;
        OnGui(O);
        addActions(O);
        System.out.println(listDomaine.toString());
        iddomaine = O.getId_domaine();
    }
    
    private void OnGui(Offre o) {
        tftitre = new TextField(o.getTitre());
        tfdescription = new TextField(o .getDescription());
        
        datepub = new Picker();
        datepub.setDate(o.getDate_offre()); 
        
        dateExp = new Picker();
        dateExp.setDate(o.getDate_Expiration());
        
        lbDomaine = new Label("Domaine");
        for (String item : listDomaine) {
            domaine.addItem(item);
        }
        domaine.setSelectedItem(sd.getDomaineById(o.getId_domaine()).getNom_domaine());
        domaine.addActionListener(evt -> {
            String selectedValue = domaine.getSelectedItem();
            System.out.println("Selected Value: " + selectedValue);
            iddomaine = sd.getDomaineByNom(selectedValue).getId_domaine();
            System.out.println(iddomaine);
        });
        
        btnAjout = new Button("Enregister ");
        this.addAll(tftitre, tfdescription,lbDomaine,domaine ,datepub,dateExp, btnAjout);
    }
    
    private void addActions(Offre o) {
        btnAjout.addActionListener((evt) -> {
            if (tftitre.getText().isEmpty() || tfdescription.getText().isEmpty()) {
                Dialog.show("Alerte", "Veillez remplir tous les champs", "OK", null);
            } else {
                ServiceOffre sp = new ServiceOffre();
                
                if (sp.modifier(new Offre(o.getId_offre(), tftitre.getText(), tfdescription.getText(), iddomaine,identre,datepub.getDate(),dateExp.getDate()))) {
                    Dialog.show("SUCCESS", "Offre modifier !", "OK", null);
                   new AfficherDetailsOffre(this, o.getId_offre()).show();
                } else {
                    Dialog.show("ERROR", "Erreur serveur", "OK", null);
                }

            }
        });

        this.getToolbar().addCommandToLeftBar("Return", null, (evt) -> {
            new AfficherOffreForm(this).show();
        });
    }       
    
    
}
