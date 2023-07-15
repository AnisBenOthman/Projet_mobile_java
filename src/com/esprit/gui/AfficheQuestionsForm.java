package com.esprit.gui;

import com.codename1.components.SpanLabel;
import com.codename1.io.FileSystemStorage;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.io.Util;
import com.codename1.ui.layouts.BoxLayout;
import com.esprit.entities.Proposition;
import com.esprit.entities.Question;
import com.esprit.services.ServiceProposition;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.codename1.ui.RadioButton;
import java.io.IOException;
import java.io.OutputStream;
//import org.apache.commons.io.FileUtils;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;



public class AfficheQuestionsForm extends Form {

    private Form previousForm;
    private Button btnValider;
    private Map<Question, RadioButton> selectedPropositions;

    public AfficheQuestionsForm(List<Question> questions, Form f) {
        super("Questions");
        previousForm = f;
        selectedPropositions = new HashMap<>();

        // Use BoxLayout Y_AXIS for vertical layout
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));

        OnGui(questions);
        addActions();
    }

    private void OnGui(List<Question> questions) {
        ServiceProposition sp = new ServiceProposition();
        // Styling the label
        Label titleLabel = new Label("Sélectionnez la bonne réponse");
        titleLabel.setUIID("FormTitle");
        titleLabel.setAlignment(CENTER);
        add(titleLabel);

        
        Collections.shuffle(questions);
        List<Question> selectedQuestions = questions.subList(0, Math.min(4, questions.size()));

        for (Question question : selectedQuestions) {
            Container questionContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            // Styling the question label
            Label questionLabel = new Label("Question: " + question.getLibelle());
            questionLabel.setUIID("QuestionLabel");
            questionContainer.addComponent(questionLabel);

            ServiceProposition sep = new ServiceProposition();
            List<Proposition> propositions = sep.getPropositionsByIdQuestion(question.getId_question());

            for (Proposition proposition : propositions) {
                RadioButton radioButton = new RadioButton(proposition.getDescription());
                radioButton.addActionListener(evt -> {
                    // Désélectionner les autres propositions de la même question
                    for (Map.Entry<Question, RadioButton> entry : selectedPropositions.entrySet()) {
                        if (entry.getKey().equals(question) && !entry.getValue().equals(radioButton)) {
                            entry.getValue().setSelected(false);
                        }
                    }
                    selectedPropositions.put(question, radioButton);
                });
                questionContainer.addComponent(radioButton);
            }

            addComponent(questionContainer);
        }
        btnValider = new Button("Valider");
        addComponent(btnValider);
    }
   
    
   private void addActions() {
    getToolbar().addCommandToLeftBar("Retour", null, (evt) -> {
        previousForm.showBack();
    });

    btnValider.addActionListener(evt -> {
        if (selectedPropositions.size() < 4) {
            Dialog.show("Erreur", "Veuillez sélectionner une proposition pour chaque question.", "OK", null);
        } else {
            // Traitement de la validation des réponses
            int score = 0;
            for (Map.Entry<Question, RadioButton> entry : selectedPropositions.entrySet()) {
                Question question = entry.getKey();
                RadioButton selectedRadioButton = entry.getValue();
                ServiceProposition sp = new ServiceProposition();
                String trueProposition = sp.getTruePropositionByQuestionId(question.getId_question());
                if (trueProposition != null && trueProposition.equals(selectedRadioButton.getText())) {
                    // La proposition sélectionnée est correcte
                    score++;
                }
            }

            Dialog.show("Score", "Votre score est de " + score + "/4", "OK", null);
            sendEmail(selectedPropositions, score);
        }
    });
}
   private void sendEmail(Map<Question, RadioButton> selectedPropositions, int score) {
    String emailBody = buildEmailBody(selectedPropositions, score);

    // Configurez les propriétés de la session JavaMail
    Properties properties = new Properties();
    properties.put("mail.smtp.host", "smtp.gmail.com");
    properties.put("mail.smtp.port", "587");
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.starttls.enable", "true");

    // Configurez les informations d'authentification pour votre compte Gmail
    String username = "kacemtaieb@gmail.com";
    String password = "alqtmoozbqpbasvy";

    Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
        protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
            return new javax.mail.PasswordAuthentication(username, password);
        }
    });

    try {
        // Créez un message MimeMessage
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("kacem.taieb@esprit.tn"));
        message.setSubject("Résultats du Quiz");
        message.setText(emailBody);

        // Envoyez le message
        Transport.send(message);

        Dialog.show("Succès", "L'e-mail a été envoyé avec succès.", "OK", null);
    } catch (AddressException e) {
        e.printStackTrace();
        Dialog.show("Erreur", "Erreur lors de l'envoi de l'e-mail.", "OK", null);
    } catch (MessagingException e) {
        e.printStackTrace();
        Dialog.show("Erreur", "Erreur lors de l'envoi de l'e-mail.", "OK", null);
    }
}

  private String buildEmailBody(Map<Question, RadioButton> selectedPropositions, int score) {
    StringBuilder sb = new StringBuilder();

    for (Map.Entry<Question, RadioButton> entry : selectedPropositions.entrySet()) {
        Question question = entry.getKey();
        RadioButton selectedRadioButton = entry.getValue();
        ServiceProposition sp = new ServiceProposition();
        String trueProposition = sp.getTruePropositionByQuestionId(question.getId_question());

        sb.append("Question: ").append(question.getLibelle()).append("\n");
        sb.append("Réponse sélectionnée: ").append(selectedRadioButton.getText()).append("\n");
        sb.append("Réponse correcte: ").append(trueProposition).append("\n\n");
    }

    sb.append("Votre Score est : ").append(score).append("/4");

    return sb.toString();
}
 
}

