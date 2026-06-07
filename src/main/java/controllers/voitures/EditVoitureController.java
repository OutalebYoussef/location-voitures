package controllers.voitures;

import dao.VoitureDAO;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import model.Voiture;

import javafx.scene.control.*;


public class EditVoitureController {

    @FXML
    private TextField marqueField;
    @FXML
    private TextField modeleField;
    @FXML
    private TextField matriculeField;
    @FXML
    private TextField prixField;
    @FXML
    private TextField statutField;

    private Voiture voiture;
    private VoitureController parent;

    public void setVoiture(Voiture v) {
        this.voiture = v;

        marqueField.setText(v.getMarque());
        modeleField.setText(v.getModele());
        matriculeField.setText(v.getMatricule());
        prixField.setText(String.valueOf(v.getPrixJour()));
        statutField.setText(v.getStatut());
    }

    public void setParentController(VoitureController parent) {
        this.parent = parent;
    }

    @FXML
    public void save() {

        voiture.setMarque(marqueField.getText());
        voiture.setModele(modeleField.getText());
        voiture.setMatricule(matriculeField.getText());
        voiture.setPrixJour(Double.parseDouble(prixField.getText()));
        voiture.setStatut(statutField.getText());

        VoitureDAO dao = new VoitureDAO();
        dao.update(voiture);

        parent.refreshTable();

        ((Stage) marqueField.getScene().getWindow()).close();
    }
}
