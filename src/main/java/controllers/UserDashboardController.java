package controllers;

import dao.VoitureDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Voiture;

public class UserDashboardController {

    @FXML private FlowPane carsContainer;

    @FXML
    private HBox navbar; // add fx:id="navbar" to your HBox — always present in both pages

    @FXML
    private void ouvrirVoitures() {
        navigateTo("/pages/user/user-dashboard.fxml");
    }

    @FXML
    private void ouvrirMesReservations() {
        navigateTo("/pages/user/mes-reservations.fxml");
    }

    private void navigateTo(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(fxmlPath)
            );
            Parent root = loader.load();
            navbar.getScene().setRoot(root); // navbar exists in both pages
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        try {
            VoitureDAO dao = new VoitureDAO();

            for (Voiture voiture : dao.getVoituresDisponibles()) {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/pages/user/car-card.fxml")
                );
                VBox card = loader.load();
                CarCardController ctrl = loader.getController();
                ctrl.setVoiture(voiture);
                carsContainer.getChildren().add(card);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}