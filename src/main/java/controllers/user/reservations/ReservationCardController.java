package controllers.user.reservations;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Reservation;

import java.io.File;

public class ReservationCardController {

    @FXML
    private ImageView image;

    @FXML
    private Label nomVoiture;

    @FXML
    private Label jours;

    @FXML
    private Label montant;

    @FXML
    private Label status;

    public void setReservation(Reservation r) {

        nomVoiture.setText(r.getMarque() + " " + r.getModele());
        jours.setText(r.getNbJr() + " jours");
        montant.setText(r.getMontantTotal() + " DH");
        status.setText(r.getStatus());

        String path = r.getImagePath();

        if (path != null && new File(path).exists()) {
            image.setImage(new Image(new File(path).toURI().toString()));
        } else {
            image.setImage(new Image(
                    getClass().getResource("/images/no-image.jpg").toExternalForm()
            ));
        }
    }

    public void setStatus(String value) {
        status.setText(value);
        switch (value) {
            case "Acceptée" ->
                    status.setStyle("-fx-background-color:#ECFDF5; -fx-text-fill:#047857; -fx-background-radius:20; -fx-padding:4 10; -fx-font-size:10.5px; -fx-font-weight:bold;");
            case "En attente" ->
                    status.setStyle("-fx-background-color:#FFFBEB; -fx-text-fill:#B45309; -fx-background-radius:20; -fx-padding:4 10; -fx-font-size:10.5px; -fx-font-weight:bold;");
            case "Refusée" ->
                    status.setStyle("-fx-background-color:#FEF2F2; -fx-text-fill:#DC2626; -fx-background-radius:20; -fx-padding:4 10; -fx-font-size:10.5px; -fx-font-weight:bold;");
        }
    }
}