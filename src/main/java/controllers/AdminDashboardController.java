package controllers;

import com.example.location.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public class AdminDashboardController {

    @FXML
    private VBox contentPane;

    @FXML
    public void showDashboard() {
        loadPage("/pages/admin/admin-dashboard.fxml");
    }

    @FXML
    public void showVoitures() {
        loadPage("/pages/admin/voitures/voitures.fxml");
    }

    @FXML
    public void showUsers() {
        loadPage("/pages/admin/users/users.fxml");
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

    @FXML
    public void logout(ActionEvent event) {

        try {

            FXMLLoader loader = new FXMLLoader(
                    Main.class.getResource("/pages/login.fxml")
            );

            Parent root = loader.load();

            Node source = (Node) event.getSource();

            source.getScene().setRoot(root);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
