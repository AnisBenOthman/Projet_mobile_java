package com.esprit.gui;

import com.codename1.components.MultiButton;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.esprit.entities.Forum;
import com.esprit.services.ServiceForum;
import java.util.List;

public class AfficherForumsForm extends Menubar {

    private Form previousForm;
    private int iduser;

    public AfficherForumsForm() {
       super("Afficher Forum", BoxLayout.y());
        OnGui();
        AddActions();
    }

    @Override
    public void showBack() {
        removeAll();
        OnGui();
        super.showBack();
    }

    private void OnGui() {
        List<Forum> forums = new ServiceForum().afficher();
        for (Forum forum : forums) {
            MultiButton mb = new MultiButton(forum.getSujet());
            mb.setTextLine2(forum.getContenu());
            mb.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    showForumDetails(forum);
                }
            });
            add(mb);
        }
    }

    private void showForumDetails(Forum forum) {
        // Create a new instance of the form to display forum details
        ForumDetailsForm forumDetailsForm = new ForumDetailsForm(forum);
        forumDetailsForm.setPreviousForm(this);
        forumDetailsForm.show();
    }

    private void AddActions() {
        this.getToolbar().addCommandToRightBar("Ajouter", null, (evt) -> {
            showAddForumForm();
        });
    }

    private void showAddForumForm() {
        AddForumForm addForumForm = new AddForumForm(iduser);
        addForumForm.setPreviousForm(this);
        addForumForm.show();
    }

    public void setPreviousForm(Form previousForm) {
        this.previousForm = previousForm;
    }

    public Form getPreviousForm() {
        return previousForm;
    }

    public void setIdUser(int iduser) {
        this.iduser = iduser;
    }

    public int getIduser() {
        return iduser;
    }
    
}
