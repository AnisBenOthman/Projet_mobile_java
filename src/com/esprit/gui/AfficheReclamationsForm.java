package com.esprit.gui;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BoxLayout;
import com.esprit.entities.Reclamation;
import com.esprit.services.ServiceReclamation;

public class AfficheReclamationsForm extends Form {
    private Form previousForm;

    public AfficheReclamationsForm(Form previousForm) {
        super("Affichage Reclamations", BoxLayout.y());
        this.previousForm = previousForm;
        setupUI();
        addActions();
    }

    private void setupUI() {
        ServiceReclamation service = new ServiceReclamation();
        for (Reclamation reclamation : service.afficher()) {
            SpanLabel lblReclamation = new SpanLabel(reclamation.getReclamation());
            add(lblReclamation);
        }
    }

    private void addActions() {
        this.getToolbar().addCommandToLeftBar("Return", null, (evt) -> {
            previousForm.showBack();
        });
    }
}
