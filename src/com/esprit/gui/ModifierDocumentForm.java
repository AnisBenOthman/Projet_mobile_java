package com.esprit.gui;

import com.codename1.ui.Button;
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
import java.util.List;

public class ModifierDocumentForm extends Form {

    private TextField tfDocumentId;
    private TextField tfTitre;
    private TextField tfDescription;
    private TextField tfType;
    private TextField tfLien;
    private TextField tfUserId;
    private Button saveButton;
    private ModificationListener modificationListener;

    private final Form previousForm;

    public ModifierDocumentForm(Form f, Document document) {
        super("Modifier Document", BoxLayout.y());
        previousForm = f;
        onGui(document);
        addActions(document);
    }

    private void onGui(Document document) {
        tfDocumentId = new TextField(String.valueOf(document.getId_document()), "ID du document", 4, TextField.NUMERIC);
        tfTitre = new TextField(document.getTitre_document(), "Titre");
        tfDescription = new TextField(document.getDescription_document(), "Description");
        tfType = new TextField(document.getType(), "Type");
        tfLien = new TextField(document.getLien(), "Lien");
        tfUserId = new TextField(String.valueOf(document.getId_user()), "ID de l'utilisateur", 4, TextField.NUMERIC);
        saveButton = new Button("Enregistrer");
        this.addAll(tfDocumentId, tfTitre, tfDescription, tfType, tfLien, tfUserId, saveButton);

        // Create the image label
        try {
            InputStream is = Display.getInstance().getResourceAsStream(getClass(), "/modifyfile.png");
            Image image = Image.createImage(is);
            Label imageLabel = new Label(image);
            imageLabel.setTextPosition(Label.BOTTOM);
            imageLabel.setAlignment(CENTER);
            this.add(imageLabel);

            // Add click listener to the image label
            imageLabel.addPointerReleasedListener((evt) -> {
                modifierDocument();
                clearFields();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addActions(Document document) {
        this.getToolbar().addCommandToLeftBar("Retour", null, (evt) -> {
            previousForm.showBack();
        });

        saveButton.addActionListener((evt) -> {
            modifierDocument();
        });
    }

    private void modifierDocument() {
    String documentIdText = tfDocumentId.getText();
    int documentId;

    try {
        documentId = Integer.parseInt(documentIdText);
    } catch (NumberFormatException e) {
        Dialog.show("Erreur", "ID du document invalide", "OK", null);
        return;
    }

        String documentName = tfTitre.getText().trim();
        String documentDescription = tfDescription.getText().trim();
        String documentType = tfType.getText().trim();
        String documentLien = tfLien.getText().trim();
        int documentUserId;

        try {
            documentUserId = Integer.parseInt(tfUserId.getText());
        } catch (NumberFormatException e) {
            Dialog.show("Erreur", "ID de l'utilisateur invalide", "OK", null);
            return;
        }

        if (documentName.isEmpty() && documentDescription.isEmpty() && documentType.isEmpty()
                && documentLien.isEmpty()) {
            Dialog.show("Alerte", "Veuillez remplir au moins un champ", "OK", null);
            return;
        }

        ServiceGererDocument service = new ServiceGererDocument();
        Document modifiedDocument = new Document(documentId, documentName, documentDescription,
                documentType, documentLien, documentUserId);

        if (service.modifier(modifiedDocument)) {
            Dialog.show("SUCCÈS", "Document modifié !", "OK", null);
            new AfficheDocumentsForm(this).show();
            if (modificationListener != null) {
                modificationListener.onDocumentModified(modifiedDocument);
            }
        } 
        
        else {
            Dialog.show("ERREUR", "Erreur serveur", "OK", null);
        }
    }

    private void clearFields() {
        tfDocumentId.setText("");
        tfTitre.setText("");
        tfDescription.setText("");
        tfType.setText("");
        tfLien.setText("");
        tfUserId.setText("");
    }

    public void setModificationListener(ModificationListener listener) {
        this.modificationListener = listener;
    }

    public interface ModificationListener {
        void onDocumentModified(Document modifiedDocument);
    }
}
