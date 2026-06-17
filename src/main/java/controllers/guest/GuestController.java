package controllers.guest;

import dao.VoitureDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Voiture;

public class GuestController {

    @FXML
    private FlowPane carsContainer;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnRegister;

    private final VoitureDAO voitureDAO = new VoitureDAO();

    @FXML
    public void initialize() {

        loadCars();

        btnLogin.setOnAction(e -> openPage("/pages/login.fxml"));

        // ila mazal ma3ndkch register
        btnRegister.setOnAction(e -> openPage("/pages/register.fxml"));
    }

    private void loadCars() {

        for (Voiture voiture : voitureDAO.getAvailableCars()) {

            VBox card = new VBox(10);

            card.setPrefWidth(220);

            card.setStyle("""
                    -fx-background-color:white;
                    -fx-padding:15;
                    -fx-background-radius:10;
                    -fx-border-color:#ddd;
                    -fx-border-radius:10;
                    """);

            Label marque = new Label(
                    voiture.getMarque() + " " + voiture.getModele()
            );

            marque.setStyle("-fx-font-size:16px;-fx-font-weight:bold;");

            Label prix = new Label(
                    voiture.getPrixJour() + " DH / jour"
            );

            Button reserve = new Button("Réserver");

            reserve.setStyle("""
                    -fx-background-color:#3498db;
                    -fx-text-fill:white;
                    """);

            reserve.setOnAction(e -> openPage("/pages/login.fxml"));

            card.getChildren().addAll(
                    marque,
                    prix,
                    reserve
            );

            carsContainer.getChildren().add(card);
        }
    }

    private void openPage(String fxml) {

        try {

            Parent root = FXMLLoader.load(
                    getClass().getResource(fxml)
            );

            Stage stage =
                    (Stage) carsContainer.getScene().getWindow();

            stage.setScene(
                    new Scene(root, 1200, 700)
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}