package controllers;

import dao.UserDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.User;
import javafx.scene.control.*;


public class RegisterController {
    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPhone;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private void register() {

        if (!txtPassword.getText()
                .equals(txtConfirmPassword.getText())) {

            new Alert(
                    Alert.AlertType.ERROR,
                    "Les mots de passe ne correspondent pas"
            ).show();

            return;
        }

        User user = new User();

        user.setUsername(txtUsername.getText());
        user.setEmail(txtEmail.getText());
        user.setNumPhone(txtPhone.getText());
        user.setPassword(txtPassword.getText());

        if (new UserDAO().register(user)) {

            new Alert(
                    Alert.AlertType.INFORMATION,
                    "Compte créé avec succès"
            ).show();

        } else {

            new Alert(
                    Alert.AlertType.ERROR,
                    "Erreur lors de l'inscription"
            ).show();
        }
    }

    @FXML
    private void goToHome(MouseEvent event) {
        try {

            Parent root = FXMLLoader.load(
                    getClass().getResource("/pages/guest/index.fxml")
            );

            Stage stage = (Stage) ((Node) event.getSource())
                    .getScene()
                    .getWindow();

            Scene scene = new Scene(root, 1200, 700);

            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
