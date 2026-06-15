package controllers;

import controllers.reservations.ReservationController;
import dao.ReservationDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Reservation;
import model.Voiture;
import utils.Session;

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

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource(
                                    "/pages/user/reservation-modal.fxml"
                            )
                    );

            Parent root = loader.load();

            ReservationController controller =
                    loader.getController();

            controller.setVoiture(voiture);

            Stage stage = new Stage();

            stage.setTitle("Réservation");

            stage.setScene(
                    new Scene(root)
            );

            stage.initModality(
                    Modality.APPLICATION_MODAL
            );

            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}