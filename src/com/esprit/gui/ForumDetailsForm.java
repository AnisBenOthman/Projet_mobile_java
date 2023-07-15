package com.esprit.gui;

import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.esprit.entities.Commentaire;
import com.esprit.entities.Forum;
import com.esprit.services.ServiceCommentaire;
import com.esprit.services.ServiceForum;
import java.util.List;

public class ForumDetailsForm extends Form {

    private AfficherForumsForm previousForm;
    private Forum forum;

    public ForumDetailsForm(Forum forum) {
        this.forum = forum;
        setTitle(forum.getSujet());
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        OnGui();
        AddActions();
    }

    private void OnGui() {
        setTitle(forum.getSujet());
        SpanLabel contentLabel = new SpanLabel(forum.getContenu());
        add(contentLabel);
        // Display commentaires
        showCommentaires();
    }

    public void showBack() {
        removeAll();
        OnGui();
        super.showBack();
    }

    public Forum getForum() {
        return forum;
    }

    public void setForum(Forum forum) {
        this.forum = forum;
    }

    private void AddActions() {

    }

    private void showUpdateForm() {
        UpdateForumForm updateForm = new UpdateForumForm(forum);
        updateForm.setPreviousForm(this);
        updateForm.show();
    }

    private void deleteForum() {
        boolean confirmed = Dialog.show("Delete Forum", "Are you sure you want to delete this forum?", "Yes", "No");
        if (confirmed) {
            boolean deleted = new ServiceForum().supprimer(forum);
            if (deleted) {
                Dialog.show("Delete Forum", "Forum deleted successfully", "OK", null);
                previousForm.showBack();
            } else {
                Dialog.show("Delete Forum", "Failed to delete the forum", "OK", null);
            }
        }
    }

    public void setPreviousForm(AfficherForumsForm previousForm) {
        this.previousForm = previousForm;
    }

    public Form getPreviousForm() {
        return previousForm;
    }

    private void showCommentaires() {
        // Get the commentaires for the forum from the service
        List<Commentaire> commentaires = new ServiceCommentaire().afficherCommentaireByForum(forum.getId_forum());
        // Clear existing components
        removeAll();
        SpanLabel contentLabel = new SpanLabel(forum.getContenu());
        add(contentLabel);

        // Update button
        Button updateButton = new Button("Modifier");
        updateButton.addActionListener((evt) -> {
            showUpdateForm();
        });
        add(updateButton);

        // Delete button
        Button deleteButtonForum = new Button("Supprimer");
        deleteButtonForum.addActionListener((evt) -> {
            deleteForum();
        });
        add(deleteButtonForum);

        // Add commentaire button
        Button addCommentButton = new Button("Ajouter Commentaire");
        addCommentButton.addActionListener((evt) -> {
            showAddCommentaireForm();
        });
        add(addCommentButton);

        this.getToolbar().addCommandToLeftBar("Retour", null, (evt) -> {
            previousForm.showBack();
        });

        for (Commentaire commentaire : commentaires) {
            // Create a container for each commentaire
            Container commentaireContainer = new Container(new BorderLayout());
            commentaireContainer.setUIID("CommentaireContainer"); // Set a custom UIID for styling if desired

            // Create a MultiButton to display the commentaire content
            MultiButton mb = new MultiButton(commentaire.getContenu());
            mb.setTextLine2("Posted by User " + commentaire.getId_user()); // Example secondary text
            commentaireContainer.add(BorderLayout.CENTER, mb);

            // Create a container for the buttons
            Container buttonsContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            buttonsContainer.setUIID("CommentButtonsContainer"); // Set a custom UIID for styling if desired

            // Create buttons for editing and deleting
            Button editButton = new Button("Modifier");
            Button deleteButton = new Button("Supprimer");

            // Handle edit button action
            editButton.addActionListener((evt) -> {
                // Show edit form or perform edit operation
                showEditCommentaireForm(commentaire);
            });

            // Handle delete button action
            deleteButton.addActionListener((evt) -> {
                // Perform delete operation
                deleteCommentaire(commentaire);
            });

            // Add buttons to the buttons container
            buttonsContainer.add(editButton);
            buttonsContainer.add(deleteButton);

            // Add the buttons container to the commentaire container
            commentaireContainer.add(BorderLayout.EAST, buttonsContainer);

            // Add the commentaire container to the form
            add(commentaireContainer);
        }

        // Revalidate the form to update the layout
        revalidate();
    }

    private void showEditCommentaireForm(Commentaire commentaire) {
        // Create a new Form for editing the commentaire
        Form editForm = new Form("Modifier Commentaire", BoxLayout.y());
        editForm.getToolbar().addCommandToLeftBar("Retour", null, (evt) -> {
            this.showBack();
        });

        // Create input components for editing the contenu
        TextField contenuField = new TextField(commentaire.getContenu());
        Button updateButton = new Button("Modifier");

        // Add the input components to the form
        editForm.add(new Label("Contenu:"));
        editForm.add(contenuField);
        editForm.add(updateButton);

        // Add an action listener to the update button
        updateButton.addActionListener(evt -> {
            // Get the updated contenu from the TextField
            String updatedContenu = contenuField.getText();

            // Update the commentaire object
            commentaire.setContenu(updatedContenu);

            // Call the service method to update the commentaire in the database
            boolean updated = new ServiceCommentaire().modifier(commentaire);

            if (updated) {
                // Display a success message
                Dialog.show("Edit Commentaire", "Commentaire updated successfully", "OK", null);

                // Refresh the commentaires in the ForumDetailsForm
                showCommentaires();

                // Go back to the previous form
                showBack();
            } else {
                // Display an error message
                Dialog.show("Edit Commentaire", "Failed to update the commentaire", "OK", null);
            }
        });

        // Show the edit form
        editForm.show();
    }

    private void deleteCommentaire(Commentaire commentaire) {
        // Display a confirmation dialog to confirm the deletion
        boolean confirmed = Dialog.show("Delete Commentaire", "Are you sure you want to delete this commentaire?", "Yes", "No");

        if (confirmed) {
            // Call the service method to delete the commentaire from the database
            boolean deleted = new ServiceCommentaire().supprimer(commentaire);

            if (deleted) {
                // Display a success message
                Dialog.show("Delete Commentaire", "Commentaire deleted successfully", "OK", null);

                // Refresh the commentaires in the ForumDetailsForm
                showCommentaires();
            } else {
                // Display an error message
                Dialog.show("Delete Commentaire", "Failed to delete the commentaire", "OK", null);
            }
        }
    }

    private void showAddCommentaireForm() {
        // Create a new Form for adding a commentaire
        Form addForm = new Form("Ajouter Commentaire", BoxLayout.y());
        addForm.getToolbar().addCommandToLeftBar("Retour", null, (evt) -> {
            this.showBack();
        });
        // Create input components for adding a new commentaire
        TextField contenuField = new TextField();
        Button addButton = new Button("Ajouter");

        // Add the input components to the form
        addForm.add(new Label("Contenu:"));
        addForm.add(contenuField);
        addForm.add(addButton);

        // Add an action listener to the add button
        addButton.addActionListener(evt -> {
            // Get the contenu from the TextField
            String contenu = contenuField.getText();

            // Create a new Commentaire object
            Commentaire newCommentaire = new Commentaire(contenu, forum.getId_forum(), previousForm.getIduser());
            // Call the service method to add the commentaire to the database
            boolean added = new ServiceCommentaire().ajouter(newCommentaire);

            if (added) {
                // Display a success message
                Dialog.show("Add Commentaire", "Commentaire added successfully", "OK", null);

                // Refresh the commentaires in the ForumDetailsForm
                showCommentaires();

                // Go back to the previous form
                showBack();
            } else {
                // Display an error message
                Dialog.show("Add Commentaire", "Failed to add the commentaire", "OK", null);
            }
        });

        // Show the add form
        addForm.show();
    }
}
