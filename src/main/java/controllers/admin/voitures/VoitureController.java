package controllers.admin.voitures;

import dao.VoitureDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Voiture;

import java.io.File;

public class VoitureController {

    @FXML
    private TableView<Voiture> tableVoitures;

    @FXML
    private TableColumn<Voiture, Integer> colId;

    @FXML
    private TableColumn<Voiture, String> colMarque;

    @FXML
    private TableColumn<Voiture, String> colModele;

    @FXML
    private TableColumn<Voiture, String> colMatricule;

    @FXML
    private TableColumn<Voiture, Double> colPrix;

    @FXML
    private TableColumn<Voiture, String> colStatut;

    @FXML
    private TableColumn<Voiture, String> colImage;

    @FXML
    private TableColumn<Voiture, String> addBy;

    @FXML
    private TableColumn<Voiture, Void> editAction;

    @FXML
    private TableColumn<Voiture, Void> delAction;

    @FXML
    public void initialize() {
        tableVoitures.setEditable(true);

        colId.setCellValueFactory(
                new PropertyValueFactory<>("id"));

        colMarque.setCellValueFactory(
                new PropertyValueFactory<>("marque"));

        colModele.setCellValueFactory(
                new PropertyValueFactory<>("modele"));

        colMatricule.setCellValueFactory(
                new PropertyValueFactory<>("matricule"));

        colPrix.setCellValueFactory(
                new PropertyValueFactory<>("prixJour"));

        colStatut.setCellValueFactory(
                new PropertyValueFactory<>("statut"));

        addBy.setCellValueFactory(
                new PropertyValueFactory<>("username"));

        VoitureDAO dao = new VoitureDAO();

        tableVoitures.getItems().setAll(dao.getVoitures());

        colImage.setCellValueFactory(
                new PropertyValueFactory<>("imagePath")
        );
        colImage.setCellFactory(column -> new TableCell<>() {

            private final ImageView imageView =
                    new ImageView();

            {
                imageView.setFitWidth(80);
                imageView.setFitHeight(50);
                imageView.setPreserveRatio(true);
            }

            @Override
            protected void updateItem(String item, boolean empty) {

                super.updateItem(item, empty);

                if (empty || item == null) {

                    setGraphic(null);

                } else {

                    try {

                        imageView.setImage(
                                new Image(
                                        new File(item)
                                                .toURI()
                                                .toString()
                                )
                        );

                        setGraphic(imageView);

                    } catch (Exception e) {

                        setGraphic(null);
                    }
                }
            }
        });

        delAction.setCellFactory(param -> new TableCell<>() {

            private final Button deleteBtn = new Button("Supprimer");

            {
                deleteBtn.setOnAction(event -> {
                    Voiture voiture = getTableView().getItems().get(getIndex());
                    deleteVoiture(voiture);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteBtn);
                }
            }
        });

        editAction.setCellFactory(param -> new TableCell<>() {

            private final Button editBtn = new Button("Modifier");

            {
                editBtn.setOnAction(event -> {
                    Voiture voiture = getTableView().getItems().get(getIndex());
                    openEditModal(voiture);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(editBtn);
                }
            }
        });
    }

    public void refreshTable() {
        tableVoitures.getItems().setAll(new VoitureDAO().getVoitures());
    }

    private void openEditModal(Voiture voiture) {

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/pages/admin/voitures/edit-voiture.fxml")
            );

            VBox root = loader.load();

            EditVoitureController controller = loader.getController();
            controller.setVoiture(voiture);
            controller.setParentController(this);

            Stage stage = new Stage();
            stage.setTitle("Modifier Voiture");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteVoiture(Voiture voiture) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Voulez-vous supprimer cette voiture ?");

        ButtonType yes = new ButtonType("Oui");
        ButtonType no = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(yes, no);

        alert.showAndWait().ifPresent(response -> {

            if (response == yes) {

                VoitureDAO dao = new VoitureDAO();
                boolean deleted = dao.delete(voiture.getId());

                if (deleted) {
                    tableVoitures.getItems().remove(voiture);

                    // optional success message
                    Alert ok = new Alert(Alert.AlertType.INFORMATION);
                    ok.setContentText("Voiture supprimée avec succès !");
                    ok.show();
                } else {
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setContentText("Erreur lors de la suppression !");
                    error.show();
                }
            }
        });
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

}
