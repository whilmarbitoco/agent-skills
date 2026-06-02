package com.example.app;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Minimal JavaFX Application class.
 * Run with: mvn -pl desktop-app javafx:run
 * Build image with: mvn -pl desktop-app javafx:jlink
 */
public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        Label title = new Label("Product Manager");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label status = new Label("Ready");
        status.setStyle("-fx-text-fill: green;");

        VBox root = new VBox(16, title, status);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(32));

        Scene scene = new Scene(root, 640, 480);
        primaryStage.setTitle("Product Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
