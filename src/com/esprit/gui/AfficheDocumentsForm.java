package com.esprit.gui;

import com.codename1.io.FileSystemStorage;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
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

public class AfficheDocumentsForm extends Form {

    private final Form previousForm;
    private Container tableContainer;
    private TextField searchField;

    public AfficheDocumentsForm(Form f) {
        super("Affichage des Documents", BoxLayout.y());
        previousForm = f;
        onGui();
        addActions();
    }

    private void onGui() {
        // Create the search field
        searchField = new TextField("", "Search by Document Name");
        this.add(searchField);

        tableContainer = new Container();
        tableContainer.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        this.add(tableContainer);

        // Add table title
        addTableTitle();

        // Populate the table with data
        populateTable();
    }

    private void addActions() {
        this.getToolbar().addCommandToLeftBar("Retour", null, (evt) -> {
            previousForm.showBack();
        });

        searchField.addDataChangeListener((type, index) -> {
            // Filter the table based on the search field value
            filterTable(searchField.getText());
        });
    }

    private void addTableTitle() {
        Container titleContainer = new Container();
        titleContainer.setLayout(new BoxLayout(BoxLayout.X_AXIS));

        // ID Title
        Label idTitle = new Label("ID");
        titleContainer.add(idTitle);

        // Titre Title
        Label titreTitle = new Label("Titre");
        titleContainer.add(titreTitle);

        // Description Title
        Label descriptionTitle = new Label("Description");
        titleContainer.add(descriptionTitle);

        // Type Title
        Label typeTitle = new Label("Type");
        titleContainer.add(typeTitle);

        // Lien Title
        Label lienTitle = new Label("Lien");
        titleContainer.add(lienTitle);

        tableContainer.add(titleContainer);
    }

    private void populateTable() {
        ServiceGererDocument service = new ServiceGererDocument();
        // Get the data from the service
        List<Document> documents = service.afficher();

        // Add data to the table container
        for (Document document : documents) {
            Container rowContainer = new Container();
            rowContainer.setLayout(new BoxLayout(BoxLayout.X_AXIS));

            // ID
            Label idLabel = new Label(String.valueOf(document.getId_document()));
            rowContainer.add(idLabel);

            // Titre
            Label titreLabel = new Label(document.getTitre_document());
            rowContainer.add(titreLabel);

            // Description
            Label descriptionLabel = new Label(document.getDescription_document());
            rowContainer.add(descriptionLabel);

            // Type
            Label typeLabel = new Label(document.getType());
            rowContainer.add(typeLabel);

            // Lien
            Label lienLabel = new Label(document.getLien());
            rowContainer.add(lienLabel);

            // Modify Button
            Button modifyButton = new Button("Modifier");
            modifyButton.addActionListener((evt) -> {
                ModifierDocumentForm modifierForm = new ModifierDocumentForm(this, document);
                modifierForm.setModificationListener((modifiedDocument) -> {
                    updateModifiedDocument(modifiedDocument);
                });
                modifierForm.show();
            });
            rowContainer.add(modifyButton);

            // Load and add the image label
            try {
                InputStream is = Display.getInstance().getResourceAsStream(getClass(), "/library.png");
                Image image = Image.createImage(is);
                Label imageLabel = new Label(image);
                rowContainer.add(imageLabel);
            } catch (IOException e) {
            }

            tableContainer.add(rowContainer);
        }
    }

    private void filterTable(String searchValue) {
        boolean isSearchEmpty = searchValue.isEmpty();
        for (Component component : tableContainer) {
            if (component instanceof Container) {
                Container rowContainer = (Container) component;
                Label titreLabel = (Label) rowContainer.getComponentAt(1); // Get the titre label

                // Check if the titre contains the search value or if the search field is empty
                boolean matches = isSearchEmpty || titreLabel.getText().toLowerCase().contains(searchValue.toLowerCase());
                rowContainer.setVisible(matches);
            }
        }
    }

    private void updateModifiedDocument(Document modifiedDocument) {
        for (Component component : tableContainer) {
            if (component instanceof Container) {
                Container rowContainer = (Container) component;
                Label idLabel = (Label) rowContainer.getComponentAt(0);
                int documentId = Integer.parseInt(idLabel.getText());
                if (documentId == modifiedDocument.getId_document()) {
                    // Update the document data in the row container
                    Label titreLabel = (Label) rowContainer.getComponentAt(1);
                    Label descriptionLabel = (Label) rowContainer.getComponentAt(2);
                    Label typeLabel = (Label) rowContainer.getComponentAt(3);
                    Label lienLabel = (Label) rowContainer.getComponentAt(4);
                    titreLabel.setText(modifiedDocument.getTitre_document());
                    descriptionLabel.setText(modifiedDocument.getDescription_document());
                    typeLabel.setText(modifiedDocument.getType());
                    lienLabel.setText(modifiedDocument.getLien());
                    break;
                }
            }
        }
    }
}
