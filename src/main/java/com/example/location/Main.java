package com.example.location;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

public class Main extends Application {

    public static void applyStyles(Scene scene) {
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
    }

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader =
                new FXMLLoader(
                        getClass().getResource("/pages/guest/index.fxml")
                );

        Parent root = loader.load();

        Scene scene = new Scene(root, 1200, 700);

        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

        String css = getClass().getResource("/css/cards.css").toExternalForm();
        scene.getStylesheets().add(css);

        stage.setTitle("Location de Voitures");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}