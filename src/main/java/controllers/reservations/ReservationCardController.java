package controllers.reservations;

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
}