package controllers.users;

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
        passwordField.setText(user.getPassword());
        roleField.setValue(user.getRole());
    }

    public void setParentController(UserController parent) {
        this.parent = parent;
    }

    @FXML
    public void save() {

        user.setUsername(usernameField.getText());
        user.setPassword(passwordField.getText());
        user.setRole(roleField.getValue());

        UserDAO dao = new UserDAO();
        dao.update(user);

        parent.refreshTable();

        ((Stage) usernameField.getScene()
                .getWindow())
                .close();
    }
}