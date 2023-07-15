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
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.esprit.entities.Review;
import com.esprit.services.ServiceReview;

import java.io.IOException;
import java.util.List;

public class AfficheReviewsForm extends Form {
    private Form previousForm;
    private long lastClickTime;
    private TextArea tfCommentaire;
    private Container starContainer;
    private Image starFillImage;
    private Image starEmptyImage;
    private ServiceReview sr = new ServiceReview();

    public AfficheReviewsForm(Form previousForm) {
        super("Affichage Reviews", BoxLayout.y());
        this.previousForm = previousForm;
        getImages(); // Call the getImages method to initialize the starFillImage and starEmptyImage
        setupUI();
        addActions();
    }

    private void setupUI() {
        ServiceReview service = new ServiceReview();
        for (Review review : service.afficher()) {
            Button btnReview = new Button(review.getCommentaire());
            btnReview.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    modifyReview(review, btnReview);
                }
            });

            add(btnReview);
        }
    }

    private void modifyReview(Review review, Button btnReview) {
        tfCommentaire = new TextArea(review.getCommentaire());
        tfCommentaire.setRows(4); // Adjust the number of rows as per your requirement
        starContainer = new Container();
        
        Button btnValider = new Button("Modifier");
        Button btnSupprimer = new Button("Supprimer");
        
         btnSupprimer.addActionListener((evt) -> {
            if (sr.supprimer(review)) {
                Dialog.show("Succès", "Review supprimée !", "OK", null);
            } else {
                Dialog.show("Erreur", "Erreur lors de la suppression de la review !", "OK", null);
            }
            if (previousForm != null) {
                removeAll();
                setupUI();
                showBack();
            }
         });
         
        createStarRatingComponent(starContainer, review.getNbr_etoile()-1);
        Container buttonsContainer = new Container(new FlowLayout(Component.CENTER));
        buttonsContainer.add(btnValider);
        buttonsContainer.add(btnSupprimer);

        Form modifyForm = new Form("Modifier Review", BoxLayout.y());
        modifyForm.add(starContainer);
        modifyForm.add(tfCommentaire);
        modifyForm.add(buttonsContainer);

        
        btnValider.addActionListener(evt -> {
            String commentaire = tfCommentaire.getText();
            int nbrEtoiles = getNbrEtoiles();
            review.setCommentaire(commentaire);
            review.setNbr_etoile(nbrEtoiles);

            ServiceReview service = new ServiceReview();
            if (service.modifier(review)) {
                Dialog.show("Succès", "Review modifiée !", "OK", null);
                btnReview.setText(commentaire);
                
                if (previousForm != null) {
                    removeAll();
                    setupUI();
                    showBack();
                }
            } else {
                Dialog.show("Erreur", "Erreur lors de la modification de la review !", "OK", null);
            }

            modifyForm.removeAll();
        });

        modifyForm.show();
    }

    private void createStarRatingComponent(Container starContainer, int nbrEtoiles) {
        for (int i = 1; i <= 5; i++) {
            ScaleImageLabel starEmpty = new ScaleImageLabel(starEmptyImage);
            addComponentToContainer(starContainer, starEmpty);
        }
        
        color(nbrEtoiles);
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

    private int getNbrEtoiles() {
        int res = 0;
        List<Component> components = starContainer.getChildrenAsList(false);
        for (int i = 0; i < components.size(); i++) {
            if (components.get(i).getStyle().getBgImage().equals(starFillImage)) {
                res++;
            }
        }
        return res;
    }

    private void addActions() {
        this.getToolbar().addCommandToLeftBar("Retour", null, (evt) -> {
            previousForm.showBack();
        });
    }

    private void getImages() {
        try {
            Resources theme = Resources.open("/theme.res");
            starFillImage = theme.getImage("star_filled.png");
            starEmptyImage = theme.getImage("star_empty.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
