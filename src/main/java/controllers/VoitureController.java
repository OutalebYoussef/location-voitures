package controllers;

import dao.VoitureDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Voiture;

public class VoitureController {
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

        cmbStatut.getItems().addAll(
                "Disponible",
                "Louée",
                "Maintenance"
        );

        cmbStatut.setValue("Disponible");
    }

    @FXML
    public void ajouterVoiture() {

        try {

            Voiture voiture = new Voiture(
                    txtMarque.getText(),
                    txtModele.getText(),
                    txtMatricule.getText(),
                    Double.parseDouble(txtPrix.getText()),
                    cmbStatut.getValue()
            );

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
