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

import java.io.IOException;


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

        String username = txtUsername.getText().trim();
        String email = txtEmail.getText().trim();
        String phone = txtPhone.getText().trim();
        String password = txtPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();

        // Champs obligatoires
        if (username.isEmpty()
                || email.isEmpty()
                || phone.isEmpty()
                || password.isEmpty()
                || confirmPassword.isEmpty()) {

            new Alert(Alert.AlertType.WARNING,
                    "Tous les champs sont obligatoires.")
                    .showAndWait();
            return;
        }

        // Vérification email
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            new Alert(Alert.AlertType.WARNING,
                    "Email invalide.")
                    .showAndWait();
            return;
        }

        // Vérification téléphone
        if (!phone.matches("\\d{10}")) {
            new Alert(Alert.AlertType.WARNING,
                    "Le numéro doit contenir 10 chiffres.")
                    .showAndWait();
            return;
        }

        // Vérification mot de passe
        if (!password.equals(confirmPassword)) {
            new Alert(Alert.AlertType.ERROR,
                    "Les mots de passe ne correspondent pas.")
                    .showAndWait();
            return;
        }

        UserDAO dao = new UserDAO();

        // Email déjà existant
        if (dao.emailExists(email)) {
            new Alert(Alert.AlertType.ERROR,
                    "Cet email existe déjà.")
                    .showAndWait();
            return;
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setNumPhone(phone);
        user.setPassword(password);

        if (dao.register(user)) {

            new Alert(Alert.AlertType.INFORMATION,
                    "Compte créé avec succès.")
                    .showAndWait();

            try {

                Parent root = FXMLLoader.load(
                        getClass().getResource("/pages/login.fxml")
                );

                Stage stage = (Stage) txtUsername.getScene().getWindow();

                stage.setScene(new Scene(root, 1200, 700));
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();

                new Alert(Alert.AlertType.ERROR,
                        "Impossible d'ouvrir la page de connexion.")
                        .showAndWait();
            }

        } else {

            new Alert(Alert.AlertType.ERROR,
                    "Erreur lors de l'inscription.")
                    .showAndWait();
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

    @FXML
    private void ouvrirLogin(MouseEvent event) {
        try {

            Parent root = FXMLLoader.load(
                    getClass().getResource("/pages/login.fxml")
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
