package controllers.admin.users;

import dao.UserDAO;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

public class EditUserController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private ComboBox<String> roleField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    private User user;
    private UserController parent;

    @FXML
    public void initialize() {

        roleField.getItems().addAll(
                "ADMIN",
                "USER"
        );
    }

    public void setUser(User user) {

        this.user = user;

        usernameField.setText(user.getUsername());
        emailField.setText(user.getEmail());
        phoneField.setText(user.getNumPhone());
        passwordField.setText(user.getPassword());
        roleField.setValue(user.getRole());
    }

    public void setParentController(UserController parent) {
        this.parent = parent;
    }

    @FXML
    public void save() {

        if (usernameField.getText().trim().isEmpty()
                || emailField.getText().trim().isEmpty()
                || phoneField.getText().trim().isEmpty()
                || passwordField.getText().trim().isEmpty()) {

            new javafx.scene.control.Alert(
                    javafx.scene.control.Alert.AlertType.WARNING,
                    "Tous les champs sont obligatoires"
            ).showAndWait();

            return;
        }

        user.setUsername(usernameField.getText().trim());
        user.setEmail(emailField.getText().trim());
        user.setNumPhone(phoneField.getText().trim());
        user.setPassword(passwordField.getText().trim());
        user.setRole(roleField.getValue());

        UserDAO dao = new UserDAO();

        dao.update(user);

        parent.refreshTable();

        ((Stage) usernameField.getScene()
                .getWindow())
                .close();
    }
}