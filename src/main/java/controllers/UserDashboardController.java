package controllers;

import com.example.location.Main;
import dao.VoitureDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.Voiture;

import java.io.IOException;

public class UserDashboardController {




    @FXML
    private StackPane content;

    @FXML
    private void ouvrirVoitures() {
        navigateTo("/pages/user/voitures.fxml");
    }

    @FXML
    private void ouvrirMesReservations() {
        navigateTo("/pages/user/mes-reservations.fxml");
    }

    @FXML
    public void logout(ActionEvent event) {

        try {

            FXMLLoader loader = new FXMLLoader(
                    Main.class.getResource("/pages/login.fxml")
            );

            Parent root = loader.load();

            Node source = (Node) event.getSource();

            source.getScene().setRoot(root);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    private void navigateTo(String fxml) {

        try {

            Parent page = FXMLLoader.load(
                    getClass().getResource(fxml)
            );

            content.getChildren().setAll(page);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}