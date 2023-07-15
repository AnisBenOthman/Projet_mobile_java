package com.esprit.gui;

import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.TextArea;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.esprit.entities.Review;
import com.esprit.services.ServiceReview;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;

import java.io.IOException;
import java.util.List;

public class AjoutReviewForm extends Form {
    private TextArea tfCommentaire;
    private Button btnAjouter;
    private int ratingValue;
    private Container starContainer = new Container();
    Image starFillImage = null;
    Image starEmptyImage = null;

    public AjoutReviewForm(Form previousForm) {
        super("Ajout Review");
        getImages();
        setupUI();
        
        btnAjouter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                String commentaire = tfCommentaire.getText();
                if (commentaire.isEmpty()) {
                    Dialog.show("Alerte", "Veuillez remplir le champ Commentaire", "OK", null);
                } else {
                    ServiceReview service = new ServiceReview();
                    int nbr_etoile = (int) getNbrEtoiles(); // Set the number of stars as per your requirement
                    int id_user = 1; // Set the user ID as per your requirement
                    int id_entreprise = 5;
                    Review review = new Review(nbr_etoile, id_user, commentaire, id_entreprise);

                    if (service.ajouter(review)) {
                        Dialog.show("SUCCESS", "Review ajoutée !", "OK", null);
                        tfCommentaire.setText(""); // Clear the text area
                        decolorise(starContainer.getChildrenAsList(false), -1);
                    } else {
                        Dialog.show("ERROR", "Erreur serveur", "OK", null);
                    }
                }
            }
        });
        
        getToolbar().addCommandToLeftBar("Retour", null, (evt) -> {
            previousForm.showBack();
        });
    }
private void setupUI() {
    tfCommentaire = new TextArea();
    tfCommentaire.setRows(3); // Set the number of rows to display
    tfCommentaire.setGrowByContent(true); // Enable automatic resizing based on content
    tfCommentaire.setScrollVisible(false); // Hide the scroll bar
    
    // Adjust the preferred size of the text area
    tfCommentaire.setPreferredH(100); // Set the desired height
    tfCommentaire.setPreferredW(Display.getInstance().getDisplayWidth() - 20); // Set the desired width
    
    btnAjouter = new Button("Ajouter");

    createStarRatingComponent(starContainer);

    setLayout(BoxLayout.y());
    add(starContainer);
    add(tfCommentaire);
    add(btnAjouter);
}


    private int getNbrEtoiles() {
        int res= 0;
        List<Component> components = starContainer.getChildrenAsList(false);
        for (int i = 0; i < components.size(); i++) {
            if(components.get(i).getStyle().getBgImage().equals(starFillImage)){
                res++;
            }
        }
        
        return res;
    }

    private void createStarRatingComponent(Container starContainer) {
        for (int i = 1; i <= 5; i++) {
            ScaleImageLabel starEmpty = new ScaleImageLabel(starEmptyImage);
            addComponentToContainer(starContainer, starEmpty);
        }
    }

    private void addComponentToContainer(Container starContainer, Component cmp) {
        cmp.setUIID("StarRating");
        cmp.setPreferredH(Display.getInstance().convertToPixels(4f));
        cmp.setPreferredW(Display.getInstance().convertToPixels(4f));
        cmp.getAllStyles().setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

        starContainer.addComponent(cmp);
        starContainer.setPreferredH(cmp.getPreferredH());
        starContainer.setPreferredW(cmp.getPreferredW());

        cmp.addPointerPressedListener(evt -> {
            color(starContainer.getChildrenAsList(false).indexOf(cmp));
            cmp.repaint();
            starContainer.repaint();
        });
    }

    private void color(int index) {
        List<Component> stars = starContainer.getChildrenAsList(false);
        Image currentImage = stars.get(index).getStyle().getBgImage();

        if (currentImage == null || !currentImage.equals(starFillImage)) {
            colorise(stars, index);
        } else {
            decolorise(stars, index);
        }
    }

    private void colorise(List<Component> stars, int number) {
        for (int i = 0; i <= number; i++) {
            stars.get(i).getAllStyles().setBgImage(starFillImage);
        }
    }

    private void decolorise(List<Component> stars, int number) {
        for (int i = number+1; i < stars.size(); i++) {
            stars.get(i).getAllStyles().setBgImage(starEmptyImage);
        }
    }

    private void getImages() {
        Resources theme = null;
        try {
            theme = Resources.open("/theme.res");
        } catch (IOException e) {
            e.printStackTrace();
        }

        starFillImage = theme.getImage("star_filled.png");
        starEmptyImage = theme.getImage("star_empty.png");
    }
}
