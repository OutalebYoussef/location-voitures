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

        carsContainer.getChildren().clear();

        for (Voiture voiture : voitureDAO.getAvailableCars()) {

            try {

                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/pages/guest/cars-card.fxml")
                );

                VBox card = loader.load();

                CarCardController controller = loader.getController();

                controller.setData(voiture);

                carsContainer.getChildren().add(card);

            } catch (Exception e) {
                e.printStackTrace();
            }
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