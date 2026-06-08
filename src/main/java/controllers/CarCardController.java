package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Voiture;

import java.io.File;

public class CarCardController {

    @FXML
    private ImageView imgCar;
    @FXML
    private Label lblTitle;
    @FXML
    private Label lblPrix;

    private Voiture voiture;

    public void setVoiture(Voiture voiture) {
        this.voiture = voiture;
        lblTitle.setText(voiture.getMarque() + " " + voiture.getModele());
        lblPrix.setText(voiture.getPrixJour() + " DH / jour");

        if (voiture.getImagePath() != null
                && !voiture.getImagePath().isEmpty()
                && new File(voiture.getImagePath()).exists()) {

            imgCar.setImage(new Image(new File(voiture.getImagePath()).toURI().toString()));

        } else {
            imgCar.setImage(new Image(getClass().getResource("/images/no-image.jpg").toExternalForm()));
        }
    }

    @FXML
    private void reserver() {
        System.out.println("Réservation: " + voiture.getId());
    }
}