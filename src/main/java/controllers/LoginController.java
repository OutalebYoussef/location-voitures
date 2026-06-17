package controllers;

import com.example.location.Main;
import dao.UserDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class LoginController {

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;


    @FXML
    public void login() {

        String username = txtUsername.getText();
        String password = txtPassword.getText();

        UserDAO userDAO = new UserDAO();

        String role = userDAO.login(username, password);

        try {

            Stage stage =
                    (Stage) txtUsername.getScene().getWindow();

            if ("admin".equalsIgnoreCase(role)) {

                FXMLLoader loader = new FXMLLoader(
                        Main.class.getResource(
                                "/pages/admin/admin-layout.fxml"
                        )
                );

                  txtUsername.getScene().setRoot(loader.load());

            } else if ("user".equalsIgnoreCase(role)) {

                FXMLLoader loader = new FXMLLoader(
                        Main.class.getResource(
                                "/pages/user/user-dashboard.fxml"
                        )
                );

                  txtUsername.getScene().setRoot(loader.load());

            } else {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Username ou Password incorrect !");
                alert.showAndWait();
            }

        } catch (Exception e) {
            e.printStackTrace();
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