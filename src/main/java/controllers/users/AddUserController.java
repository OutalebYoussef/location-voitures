package controllers.users;

import dao.UserDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.User;

public class AddUserController {

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtPassword;

    @FXML
    private ComboBox<String> cmbRole;

    @FXML
    public void initialize() {

        cmbRole.getItems().addAll(
                "ADMIN",
                "USER"
        );

        cmbRole.setValue("USER");
    }

    @FXML
    public void ajouterUser() {

        try {

            User user = new User();

            user.setUsername(txtUsername.getText());
            user.setPassword(txtPassword.getText());
            user.setRole(cmbRole.getValue());

            UserDAO dao = new UserDAO();

            if (dao.ajouter(user)) {

                new Alert(Alert.AlertType.INFORMATION,
                        "Utilisateur ajouté avec succès")
                        .showAndWait();

                txtUsername.clear();
                txtPassword.clear();
                cmbRole.setValue("USER");
            }

        } catch (Exception e) {

            new Alert(Alert.AlertType.ERROR,
                    "Vérifiez les données")
                    .showAndWait();

            e.printStackTrace();
        }
    }
}