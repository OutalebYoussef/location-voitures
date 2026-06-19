package controllers.guest;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Voiture;

import java.io.File;

public class CarCardController {

    @FXML
    private ImageView imgCar;

    @FXML
    private Label lblMarque;

    @FXML
    private Label lblAnnee;

    @FXML
    private Label lblPrix;

    @FXML
    private Label lblStatus;

    @FXML
    private Button btnReserve;

    public void setData(Voiture voiture) {

        lblMarque.setText(voiture.getMarque() + " " + voiture.getModele());
        lblPrix.setText(voiture.getPrixJour() + " DH / Jour");

        if (voiture.getImagePath() != null
                && !voiture.getImagePath().isEmpty()
                && new File(voiture.getImagePath()).exists()) {

            imgCar.setImage(new Image(new File(voiture.getImagePath()).toURI().toString()));

        } else {
            imgCar.setImage(new Image(getClass().getResource("/images/no-image.jpg").toExternalForm()));
        }

        btnReserve.setOnAction(e -> {
            try {

                Parent root = FXMLLoader.load(
                        getClass().getResource("/pages/login.fxml")
                );

                Stage stage = (Stage) btnReserve.getScene().getWindow();

                stage.setScene(new Scene(root, 1200, 700));

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}