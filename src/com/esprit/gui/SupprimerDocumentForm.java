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
import java.io.IOException;
import java.io.InputStream;

public class SupprimerDocumentForm extends Form {

    private TextField tfDocumentId;
    private final Form previousForm;

    public SupprimerDocumentForm(Form f) {
        super("Supprimer Document", BoxLayout.y());
        previousForm = f;
        onGui();
        addActions();
    }

    private void onGui() {
        tfDocumentId = new TextField(null, "ID du document");
        this.addAll(tfDocumentId);
        // Create the image label
        try {
            InputStream is = Display.getInstance().getResourceAsStream(getClass(), "/deletefile.png");
            Image image = Image.createImage(is);
            Label imageLabel = new Label(image);
            add(imageLabel);
            imageLabel.setTextPosition(Label.BOTTOM);
            imageLabel.setAlignment(CENTER);

            // Add click listener to the image label
            imageLabel.addPointerReleasedListener((evt) -> {
                deleteDocument();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addActions() {
        this.getToolbar().addCommandToLeftBar("Retour", null, (evt) -> {
            previousForm.showBack();
        });
    }

    private void deleteDocument() {
        if (tfDocumentId.getText().isEmpty()) {
            Dialog.show("Erreur", "Veuillez entrer l'ID du document", "OK", null);
        } else {
            int documentId = Integer.parseInt(tfDocumentId.getText());
            ServiceGererDocument service = new ServiceGererDocument();
            Document document = new Document();
            document.setId_document(documentId);

            boolean confirm = Dialog.show("Confirmation", "Êtes-vous sûr de vouloir supprimer ce document ?", "Oui", "Non");
            if (confirm) {
                if (service.supprimer(document)) {
                    Dialog.show("Succès", "Le document a été supprimé avec succès", "OK", null);
                } else {
                    Dialog.show("Erreur", "Erreur lors de la suppression du document", "OK", null);
                }
            }
        }
    }
}
