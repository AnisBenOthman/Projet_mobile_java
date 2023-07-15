package com.esprit.gui;

import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.esprit.entities.Document;
import com.esprit.services.ServiceGererDocument;
import com.esprit.services.ServiceMail;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.mail.MessagingException;

public class AjoutDocumentForm extends Form {

    private TextField tfTitre;
    private TextField tfDescription;
    private TextField tfType;
    private TextField tfLien;
    private TextField tfUserId;

    private Form previousForm;

    public AjoutDocumentForm(Form f) {
        super("Ajout d'un Document", BoxLayout.y());
        previousForm = f;
        onGui();
        addActions();
    }

    private void onGui() {
        tfTitre = new TextField(null, "Titre");
        tfDescription = new TextField(null, "Description");
        tfType = new TextField(null, "Type");
        tfLien = new TextField(null, "Lien");
        tfUserId = new TextField(null, "ID de l'utilisateur");
        this.addAll(tfTitre, tfDescription, tfType, tfLien, tfUserId);
        
        // Create the image label
        try {
            InputStream is = Display.getInstance().getResourceAsStream(getClass(), "/addfile.png");
            Image image = Image.createImage(is);
            Label imageLabel = new Label(image);
            imageLabel.setTextPosition(Label.BOTTOM);
            imageLabel.setAlignment(CENTER);
            this.add(imageLabel);
            
            // Add click listener to the image label
            imageLabel.addPointerReleasedListener((evt) -> {
                try {
                    ajouterDocument();
                } catch (MessagingException ex) {
                    System.out.println(ex.getMessage());
                }
            });
        } catch (IOException e) {
        }
    }

    private void addActions() {
        this.getToolbar().addCommandToLeftBar("Retour", null, (evt) -> {
            previousForm.showBack();
        });
    }
    
    private void ajouterDocument() throws MessagingException {
        String documentName = tfTitre.getText().trim();
        if (documentName.isEmpty() || tfDescription.getText().isEmpty() || tfType.getText().isEmpty()
                || tfLien.getText().isEmpty() || tfUserId.getText().isEmpty()) {
            Dialog.show("Alerte", "Veuillez remplir tous les champs", "OK", null);
        } else if (isDocumentNameExists(documentName)) {
            Dialog.show("Erreur", "Le nom de document existe déjà", "OK", null);
        } else {
            ServiceGererDocument service = new ServiceGererDocument();
            int userId = Integer.parseInt(tfUserId.getText());
            Document document = new Document(0, documentName, tfDescription.getText(), tfType.getText(),
                    tfLien.getText(), userId);

            if (service.ajouter(document)) {
                ServiceMail.sendMail("azizmanai072@gmail.com", tfTitre.getText(), tfDescription.getText());
                Dialog.show("SUCCÈS", "Document ajouté !", "OK", null);
                clearFields();
            } else {
                Dialog.show("ERREUR", "Erreur serveur", "OK", null);
            }
        }
    }

    private boolean isDocumentNameExists(String name) {
        ServiceGererDocument service = new ServiceGererDocument();
        List<Document> documents = service.afficher();

        for (Document document : documents) {
            if (document.getTitre_document().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
    
    private void clearFields() {
        tfTitre.setText("");
        tfDescription.setText("");
        tfType.setText("");
        tfLien.setText("");
        tfUserId.setText("");
    }
}
