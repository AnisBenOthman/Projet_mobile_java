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
public class AjouterOffreForm extends Form{
    private TextField tftitre;
    private TextField tfdescription;
    
    private Picker dateExp;
    Label lbDomaine;
    private ComboBox<String> domaine = new ComboBox<>();
    private Button btnAjout;
    
    private int iddomaine = 0;
    private int identre = 2;
    
    
    ServiceDomaine sd = new ServiceDomaine();
    List<String> listDomaine = sd.afficherNomDomaine();
    private Form previousForm;

    public AjouterOffreForm(Form f) {
        super("Ajout", BoxLayout.y());
        previousForm = f;
        OnGui();
        addActions();
        System.out.println(listDomaine.toString());
    }
    
    private void OnGui() {
        tftitre = new TextField(null, "titre");
        tfdescription = new TextField(null, "description");
        lbDomaine = new Label("Domaine");
        dateExp = new Picker();
        
        for (String item : listDomaine) {
            domaine.addItem(item);
        }
        
        domaine.addActionListener(evt -> {
            String selectedValue = domaine.getSelectedItem();
            System.out.println("Selected Value: " + selectedValue);
            iddomaine = sd.getDomaineByNom(selectedValue).getId_domaine();
            System.out.println(iddomaine);
        });
        
        btnAjout = new Button("Ajouter");
        this.addAll(tftitre, tfdescription,lbDomaine,domaine ,dateExp, btnAjout);
    }
    
    private void addActions() {
        btnAjout.addActionListener((evt) -> {
            if (tftitre.getText().isEmpty() || tfdescription.getText().isEmpty()) {
                Dialog.show("Alerte", "Veillez remplir tous les champs", "OK", null);
            } else {
                ServiceOffre sp = new ServiceOffre();
                
                if (sp.ajouter(new Offre(tftitre.getText(), tfdescription.getText(), iddomaine,identre,dateExp.getDate()))) {

                    Dialog.show("SUCCESS", "Personne ajoutée  !", "OK", null);
                    previousForm.showBack();
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
