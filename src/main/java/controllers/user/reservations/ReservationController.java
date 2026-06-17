package controllers.user.reservations;

import dao.ReservationDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Reservation;
import model.Voiture;
import utils.Session;

public class ReservationController {

    @FXML
    private DatePicker dateDebut;

    @FXML
    private TextField nbJourField;

    @FXML
    private Label prixJourLabel;

    @FXML
    private Label prixTotalLabel;

    private Voiture voiture;

    public void setVoiture(Voiture voiture) {

        this.voiture = voiture;

        prixJourLabel.setText("Prix/Jour : " + voiture.getPrixJour() + " DH");

        nbJourField.textProperty().addListener(
                (obs, oldValue, newValue) -> calculateTotal()
        );
    }

    private void calculateTotal() {

        try {

            int jours = Integer.parseInt(
                    nbJourField.getText()
            );

            double total =
                    jours * voiture.getPrixJour();

            prixTotalLabel.setText("Total : "
                    + total
                    + " DH"
            );

        } catch (Exception e) {

            prixTotalLabel.setText("Total : 0 DH");
        }
    }

    @FXML
    private void confirmer() {

        try {
            int nbJours = Integer.parseInt(nbJourField.getText());

            double montantTotal = nbJours * voiture.getPrixJour();

            Reservation reservation = new Reservation();

            reservation.setUserId(Session.getUserId());

            reservation.setVoitureId(voiture.getId());

            reservation.setNbJr(Integer.parseInt(nbJourField.getText()));

            reservation.setStatus("En attente");

            reservation.setMontantTotal(montantTotal);

            reservation.setDateDebut(dateDebut.getValue());

            reservation.setStatus(
                    "En attente"
            );

            boolean success = new ReservationDAO().ajouter(reservation);

            if (success) {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setContentText("Réservation enregistrée");

                alert.showAndWait();

                ((Stage)
                        nbJourField
                                .getScene()
                                .getWindow())
                        .close();
            }

        } catch (Exception e) {

            new Alert(
                    Alert.AlertType.ERROR,
                    "Données invalides"
            ).show();
        }
    }
}