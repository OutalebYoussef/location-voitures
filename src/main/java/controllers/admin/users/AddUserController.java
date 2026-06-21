package controllers.admin.users;

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
    private TextField txtEmail;

    @FXML
    private TextField txtPhone;

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

            if (txtUsername.getText().trim().isEmpty()
                    || txtEmail.getText().trim().isEmpty()
                    || txtPhone.getText().trim().isEmpty()
                    || txtPassword.getText().trim().isEmpty()
                    || cmbRole.getValue() == null) {

                new Alert(
                        Alert.AlertType.WARNING,
                        "Tous les champs sont obligatoires"
                ).showAndWait();

                return;
            }

            User user = new User();

            user.setUsername(txtUsername.getText().trim());
            user.setEmail(txtEmail.getText().trim());
            user.setNumPhone(txtPhone.getText().trim());
            user.setPassword(txtPassword.getText().trim());
            user.setRole(cmbRole.getValue());

            UserDAO dao = new UserDAO();

            if (dao.ajouter(user)) {

                new Alert(
                        Alert.AlertType.INFORMATION,
                        "Utilisateur ajouté avec succès"
                ).showAndWait();

                txtUsername.clear();
                txtEmail.clear();
                txtPhone.clear();
                txtPassword.clear();
                cmbRole.setValue("USER");
            }

        } catch (Exception e) {

            new Alert(
                    Alert.AlertType.ERROR,
                    "Vérifiez les données"
            ).showAndWait();

            e.printStackTrace();
        }
    }
}