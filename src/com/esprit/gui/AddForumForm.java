package com.esprit.gui;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.esprit.entities.Forum;
import com.esprit.services.ServiceForum;

public class AddForumForm extends Form {

    private Form previousForm;
    private int iduser;
    private TextField subjectField;
    private TextField contentField;
    private TextField domainField;

    public AddForumForm(int iduser) {
        this.iduser = iduser;
        setTitle("Ajouter Forum");
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        OnGui();
        addActions();
    }

    private void OnGui() {
        Label subjectLabel = new Label("Sujet:");
        subjectField = new TextField();

        Label contentLabel = new Label("Contenu:");
        contentField = new TextField();



        Button addButton = new Button("Ajouter");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                addForum();
            }
        });

        add(subjectLabel);
        add(subjectField);
        add(contentLabel);
        add(contentField);
        add(addButton);
    }

    private void addForum() {
        String subject = subjectField.getText();
        String content = contentField.getText();

   
        Forum forum = new Forum(subject, content, iduser, 1);

        ServiceForum serviceForum = new ServiceForum();
        boolean success = serviceForum.ajouter(forum);

        if (success) {
            Dialog.show("Success", "Forum added successfully", "OK", null);
            clearFields();
        } else {
            Dialog.show("Error", "Failed to add the forum", "OK", null);
        }
    }

    private void addActions() {
        this.getToolbar().addCommandToLeftBar("Retour", null, (evt) -> {
            previousForm.showBack();
        });
    }

    private void clearFields() {
        subjectField.clear();
        contentField.clear();
    }

    public void setPreviousForm(Form previousForm) {
        this.previousForm = previousForm;
    }

    public Form getPreviousForm() {
        return previousForm;
    }
}
