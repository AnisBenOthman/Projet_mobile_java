package com.esprit.gui;

import com.codename1.io.FileSystemStorage;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.esprit.entities.Competence;
import com.esprit.entities.Question;
import com.esprit.gui.AfficheQuestionsForm;
import com.esprit.services.ServiceCompetence;
import com.esprit.services.ServiceQuestion;
import java.util.List;
import com.codename1.ui.Image;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.URLImage;
import java.io.IOException;

public class HomeForm extends Menubar {
    private Button btnShowQuestion;
    private ComboBox<String> competenceComboBox;
    private static final String IMAGE_PATH = "/Esprit Logo.jpg";


    public HomeForm() {
        super("LISTE DES QUIZ", BoxLayout.y());
        onGui();
        AddActions();
    }

    private void onGui() {
    competenceComboBox = createComboBoxModel();
    btnShowQuestion = new Button("Afficher Quiz");

    // Créez un conteneur avec un BoxLayout centré
    Container centerContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
    centerContainer.setUIID("CenterContainer"); // Appliquez un style CSS pour centrer le contenu
    centerContainer.getAllStyles().setAlignment(Component.CENTER);

    this.addAll(new Label("Choisissez une compétence :"), competenceComboBox, btnShowQuestion, centerContainer);

    EncodedImage encodedImage = null;
    try {
        encodedImage = EncodedImage.create(IMAGE_PATH);
        Image img = encodedImage.scaled(getWidth() / 2, getHeight() / 2);
        Label imageLabel = new Label(img);
        centerContainer.add(imageLabel);
    } catch (IOException ex) {
        ex.printStackTrace();
    }
}


    private ComboBox<String> createComboBoxModel() {
        ServiceCompetence sc = new ServiceCompetence();
        List<Competence> competences = sc.afficher();

        ComboBox<String> comboBox = new ComboBox<>();
        for (Competence competence : competences) {
            String competenceNom = competence.getNom();
            comboBox.addItem(competenceNom);
        }

        return comboBox;
    }

    private void AddActions() {
        ServiceCompetence sc = new ServiceCompetence();
        ServiceQuestion sq = new ServiceQuestion();

        btnShowQuestion.addActionListener((evt) -> {
            String selectedCompetence = getSelectedCompetence();
            int id = sc.GetIdCompetenceByNom(selectedCompetence);

            if (selectedCompetence != null) {
                List<Question> questions = sq.getQuestionsByIdCompetence(id);
                AfficheQuestionsForm AQF = new AfficheQuestionsForm(questions, this);
                AQF.show();
            }
        });
    }

    private String getSelectedCompetence() {
        String selectedValue = competenceComboBox.getSelectedItem();
        if (selectedValue != null && !selectedValue.isEmpty()) {
            return selectedValue;
        }
        return null;
    }
}

