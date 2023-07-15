package com.esprit.gui;

import static com.codename1.testing.TestUtils.findByName;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.esprit.entities.Forum;
import com.esprit.services.ServiceForum;

public class UpdateForumForm extends Form {

    private ForumDetailsForm previousForm;
    private Forum forum;

    public UpdateForumForm(Forum forum) {
        this.forum = forum;
        setTitle("Update Forum");
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        OnGui();
        addActions();
    }

    private void OnGui() {
        TextField sujetField = new TextField(forum.getSujet());
        TextField contenuField = new TextField(forum.getContenu());

        sujetField.setName("SujetField");
        contenuField.setName("ContenuField");

        add(new Label("Sujet:"));
        add(sujetField);
        add(new Label("Contenu:"));
        add(contenuField);
    }

    private void addActions() {
        Button updateButton = new Button("Update");
        updateButton.addActionListener((evt) -> {
            updateForum();
        });
        add(updateButton);

        this.getToolbar().addCommandToLeftBar("Retour", null, (evt) -> {
            previousForm.showBack();
        });
    }

    private void updateForum() {
        String sujet = ((TextField) findByName("SujetField")).getText();
        String contenu = ((TextField) findByName("ContenuField")).getText();

        Forum updatedForum = new Forum(sujet, contenu, forum.getId_user(), forum.getId_domaine());
        updatedForum.setId_forum(forum.getId_forum());

        boolean updated = new ServiceForum().modifier(updatedForum);
        if (updated) {
            Dialog.show("Update Forum", "Forum updated successfully", "OK", null);
            previousForm.setForum(updatedForum);
            previousForm.showBack();
        } else {
            Dialog.show("Update Forum", "Failed to update the forum", "OK", null);
        }
    }

    public void setPreviousForm(ForumDetailsForm previousForm) {
        this.previousForm = previousForm;
    }

    public ForumDetailsForm getPreviousForm() {
        return previousForm;
    }
}
