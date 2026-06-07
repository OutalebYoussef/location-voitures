module com.example.location {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.sql;
    requires mysql.connector.j;

    opens com.example.location to javafx.fxml;
    opens com.example.location.controllers to javafx.fxml;

    exports com.example.location;
    exports com.example.location.controllers;
}