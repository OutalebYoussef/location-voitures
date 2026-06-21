package controllers.admin.voitures;

import dao.VoitureDAO;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import model.Voiture;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;

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
    @FXML
    private ImageView imagePreview;

    private String selectedImage;

    private Voiture voiture;
    private VoitureController parent;

    @FXML
    private void chooseImage() {

        FileChooser chooser = new FileChooser();

        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                        "Images",
                        "*.png",
                        "*.jpg",
                        "*.jpeg"
                )
        );

        File file = chooser.showOpenDialog(
                marqueField.getScene().getWindow()
        );

        if (file != null) {

            selectedImage = file.getAbsolutePath();

            imagePreview.setImage(
                    new Image(file.toURI().toString())
            );
        }
    }

    public void setVoiture(Voiture v) {

        this.voiture = v;

        marqueField.setText(v.getMarque());
        modeleField.setText(v.getModele());
        matriculeField.setText(v.getMatricule());
        prixField.setText(String.valueOf(v.getPrixJour()));
        statutField.setText(v.getStatut());

        selectedImage = v.getImagePath();

        if (selectedImage != null && !selectedImage.isEmpty()) {

            File file = new File(selectedImage);

            if (file.exists()) {
                imagePreview.setImage(
                        new Image(file.toURI().toString())
                );
            }
        }
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

        voiture.setImagePath(selectedImage);

        VoitureDAO dao = new VoitureDAO();
        dao.update(voiture);

        parent.refreshTable();

        ((Stage) marqueField.getScene().getWindow()).close();
    }
}
