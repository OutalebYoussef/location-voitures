package controllers.voitures;

import dao.VoitureDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.Voiture;
import utils.Session;

public class AddVoitureController {

    @FXML
    private TextField txtMarque;

    @FXML
    private TextField txtModele;

    @FXML
    private TextField txtMatricule;

    @FXML
    private TextField txtPrix;

    @FXML
    private ComboBox<String> cmbStatut;

    @FXML
    public void initialize() {

        if (cmbStatut != null) {
            cmbStatut.getItems().addAll(
                    "Disponible",
                    "Louée",
                    "Maintenance"
            );
            cmbStatut.setValue("Disponible");
        }
    }

    @FXML
    private VBox contentPane;

    public void addVoiture() {
        loadPage("/pages/admin/voitures/add-voiture.fxml");
    }

    private void loadPage(String path) {

        try {

            Node node = FXMLLoader.load(
                    getClass().getResource(path)
            );

            contentPane.getChildren().clear();
            contentPane.getChildren().add(node);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //    add voiture
    @FXML
    public void ajouterVoiture() {

        try {

            Voiture voiture = new Voiture();

            voiture.setMarque(txtMarque.getText());
            voiture.setModele(txtModele.getText());
            voiture.setMatricule(txtMatricule.getText());
            voiture.setPrixJour(
                    Double.parseDouble(txtPrix.getText())
            );
            voiture.setStatut(cmbStatut.getValue());
            voiture.setAddBy(Session.getUserId());

            VoitureDAO dao = new VoitureDAO();

            if (dao.ajouter(voiture)) {

                new Alert(Alert.AlertType.INFORMATION,
                        "Voiture ajoutée avec succès")
                        .showAndWait();

                txtMarque.clear();
                txtModele.clear();
                txtMatricule.clear();
                txtPrix.clear();

            }

        } catch (Exception e) {

            new Alert(Alert.AlertType.ERROR,
                    "Vérifiez les données")
                    .showAndWait();
        }
    }


}
