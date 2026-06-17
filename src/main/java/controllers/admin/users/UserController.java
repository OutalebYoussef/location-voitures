package controllers.admin.users;

import dao.UserDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.User;

public class UserController {

    @FXML
    private TableView<User> tableUsers;

    @FXML
    private TableColumn<User, Integer> colId;

    @FXML
    private TableColumn<User, String> colUsername;

    @FXML
    private TableColumn<User, String> colRole;

    @FXML
    private TableColumn<User, Void> editAction;

    @FXML
    private TableColumn<User, Void> delAction;

    @FXML
    private VBox contentPane;

    @FXML
    public void initialize() {

        tableUsers.setEditable(true);

        colId.setCellValueFactory(
                new PropertyValueFactory<>("id"));

        colUsername.setCellValueFactory(
                new PropertyValueFactory<>("username"));

        colRole.setCellValueFactory(
                new PropertyValueFactory<>("role"));

        refreshTable();

        delAction.setCellFactory(param -> new TableCell<>() {

            private final Button deleteBtn = new Button("Supprimer");

            {
                deleteBtn.setOnAction(event -> {
                    User user = getTableView().getItems().get(getIndex());
                    deleteUser(user);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                setGraphic(empty ? null : deleteBtn);
            }
        });

        editAction.setCellFactory(param -> new TableCell<>() {

            private final Button editBtn = new Button("Modifier");

            {
                editBtn.setOnAction(event -> {
                    User user = getTableView().getItems().get(getIndex());
                    openEditModal(user);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                setGraphic(empty ? null : editBtn);
            }
        });
    }

    public void refreshTable() {
        tableUsers.getItems().setAll(
                new UserDAO().getUsers()
        );
    }

    private void openEditModal(User user) {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/pages/admin/users/edit-user.fxml")
            );

            VBox root = loader.load();

            EditUserController controller = loader.getController();
            controller.setUser(user);
            controller.setParentController(this);

            Stage stage = new Stage();
            stage.setTitle("Modifier Utilisateur");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteUser(User user) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Voulez-vous supprimer cet utilisateur ?");

        ButtonType yes = new ButtonType("Oui");
        ButtonType no = new ButtonType("Non",
                ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(yes, no);

        alert.showAndWait().ifPresent(response -> {

            if (response == yes) {

                boolean deleted =
                        new UserDAO().delete(user.getId());

                if (deleted) {

                    tableUsers.getItems().remove(user);

                    Alert ok = new Alert(Alert.AlertType.INFORMATION);
                    ok.setContentText("Utilisateur supprimé avec succès !");
                    ok.show();

                } else {

                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setContentText("Erreur lors de la suppression !");
                    error.show();
                }
            }
        });
    }

    public void addUser() {
        loadPage("/pages/admin/users/add-user.fxml");
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