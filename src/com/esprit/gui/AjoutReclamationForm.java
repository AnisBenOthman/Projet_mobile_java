package com.esprit.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.TextArea;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.esprit.entities.Email;
import com.esprit.entities.Reclamation;
import com.esprit.entities.EtatReclamation;
import com.esprit.services.ServiceReclamation;

public class AjoutReclamationForm extends Form {
    private TextArea tfReclamation;
    private Button btnAjouter;

    public AjoutReclamationForm(Form previousForm, int id_user, int id_offre) {
        super("Ajout Reclamation");
        setupUI();
        addActions(previousForm, id_user, id_offre);
    }

    private void setupUI() {
        tfReclamation = new TextArea();
        tfReclamation.setRows(3); // Set the number of rows to display
        tfReclamation.setGrowByContent(true); // Enable automatic resizing based on content
        tfReclamation.setScrollVisible(false); // Hide the scroll bar

        btnAjouter = new Button("Ajouter");

        setLayout(BoxLayout.y());
        add(tfReclamation);
        add(btnAjouter);
    }

    private void addActions(Form previousForm, int id_user, int id_offre) {
        btnAjouter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                String reclamationText = tfReclamation.getText();
                if (reclamationText.isEmpty()) {
                    Dialog.show("Alerte", "Veuillez remplir le champ Reclamation", "OK", null);
                } else {
                    ServiceReclamation service = new ServiceReclamation();
                    Reclamation reclamation = new Reclamation(0, reclamationText, id_user, EtatReclamation.En_cours, id_offre);
                    if (service.ajouter(reclamation)) {
                        try {
                            Email.sendMail("nokopo@hotmail.fr", "test", "msg mon amour");
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        Dialog.show("SUCCESS", "Reclamation ajoutée !", "OK", null);
                        tfReclamation.setText(""); // Clear the text area
                    } else {
                        Dialog.show("ERROR", "Erreur serveur", "OK", null);
                    }
                }
            }
        });

        this.getToolbar().addCommandToLeftBar("Retour", null, (evt) -> {
            previousForm.showBack();
        });
    }
}
