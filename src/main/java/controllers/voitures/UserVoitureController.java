package controllers.voitures;

import controllers.CarCardController;
import dao.VoitureDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import model.Voiture;

public class UserVoitureController {
    @FXML private FlowPane carsContainer;

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
