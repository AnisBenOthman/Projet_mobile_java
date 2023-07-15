package com.esprit.gui;

import com.codename1.io.FileSystemStorage;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.esprit.entities.Document;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DocumentHome extends Menubar {

    public Button btnAddDocument;
    public Button btnShowDocuments;
    public Button btnDeleteDocument;
    public Button btnChooseFile;
    public Label lblFilePath; // New label component

    public DocumentHome() {
        super("Home", BoxLayout.y());

        // Set background color
        getStyle().setBgColor(0xE5E5E5);

        onGui();
        AddActions();

    }

    public void onGui() {
        // Set font and color for labels
        Label titleLabel = new Label("Find Job");
        titleLabel.getUnselectedStyle().setFont(Font.createTrueTypeFont("native:MainLight", "native:MainLight").derive(60f, Font.STYLE_BOLD));
        titleLabel.getUnselectedStyle().setFgColor(0x333333);
        add(titleLabel);

        // Create buttons with custom styles
        btnAddDocument = new Button("Ajouter Document");
        btnAddDocument.setUIID("HomeButton");
        btnShowDocuments = new Button("Afficher Documents");
        btnShowDocuments.setUIID("HomeButton");
        btnDeleteDocument = new Button("Supprimer Document");
        btnDeleteDocument.setUIID("HomeButton");
        btnChooseFile = new Button("Choose File");
        btnChooseFile.setUIID("HomeButton");

        // Create the label for file path
        lblFilePath = new Label("");

        // Add buttons and label with spacing
        addAll(btnAddDocument, btnShowDocuments, btnDeleteDocument, btnChooseFile, lblFilePath); // Add the new button to the layout
        setLayout(BoxLayout.yCenter());
        setScrollableY(true);
        setScrollVisible(false);

        // Create the image label
        try {
            InputStream is = Display.getInstance().getResourceAsStream(getClass(), "/image4.png");
            Image image = Image.createImage(is);
            Label imageLabel = new Label(image);
            add(imageLabel);
            imageLabel.setTextPosition(Label.BOTTOM);
            imageLabel.setAlignment(CENTER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void AddActions() {
        btnAddDocument.addActionListener((evt) -> {
            new AjoutDocumentForm(this).show();
        });
        btnShowDocuments.addActionListener((evt) -> {
            new AfficheDocumentsForm(this).show();
        });
        btnDeleteDocument.addActionListener((evt) -> {
            new SupprimerDocumentForm(this).show();
        });
        btnChooseFile.addActionListener((evt) -> {
            showFileChooser();
        });
    }

    public void showFileChooser() {
        Display.getInstance().openGallery((filePath) -> {
            if (filePath != null) {
                lblFilePath.setText("Selected file: " + filePath);
               
            } else {
                lblFilePath.setText("File selection canceled");
            }
        }, Display.GALLERY_IMAGE);
    }

    public void uploadFile(String filePath) {
        String fileName = "C:/xampp/htdocs/mobile files/" + getFileName(filePath);
        try (InputStream is = FileSystemStorage.getInstance().openInputStream(filePath);
                OutputStream os = FileSystemStorage.getInstance().openOutputStream(fileName)) {
            byte[] buffer = new byte[4096];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            Dialog.show("Success", "File uploaded successfully. It will be reviewed by the administrator.", "OK", null);
        } catch (IOException e) {
            Dialog.show("Error", "An error occurred while uploading the file.", "OK", null);
        }
    }

    private String getFileName(String filePath) {
        int lastSeparatorIndex = filePath.lastIndexOf("/");
        if (lastSeparatorIndex != -1 && lastSeparatorIndex < filePath.length() - 1) {
            return filePath.substring(lastSeparatorIndex + 1);
        }
        return filePath;
    }

}
