package com.pos.performance;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Optimized startup pattern: show UI immediately, load heavy resources in background.
 * Compare with eager-startup pattern that blocks the FX Application Thread.
 */
public class StartupOptimizationDemo extends Application {

    private Label statusLabel;
    private ProgressBar progressBar;

    @Override
    public void start(Stage primaryStage) {
        // Phase 1: Show splash immediately — no heavy init
        VBox splash = new VBox(10);
        splash.setStyle("-fx-padding: 40; -fx-alignment: center;");
        Label title = new Label("POS System");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        statusLabel = new Label("Loading...");
        progressBar = new ProgressBar(0);
        progressBar.setPrefWidth(300);
        splash.getChildren().addAll(title, statusLabel, progressBar);

        primaryStage.setScene(new Scene(splash, 400, 200));
        primaryStage.show(); // UI is up — user sees something NOW

        // Phase 2: Load resources asynchronously
        loadResourcesAsync(primaryStage);
    }

    private void loadResourcesAsync(Stage primaryStage) {
        CompletableFuture.runAsync(() -> {
            // Simulate heavy initialization steps
            String[] modules = {"Database", "Config", "Cache", "Inventory", "Reports"};
            for (int i = 0; i < modules.length; i++) {
                final int step = i;
                final String module = modules[i];
                try {
                    Thread.sleep(200); // Simulate work
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                Platform.runLater(() -> {
                    statusLabel.setText("Loading " + module + "...");
                    progressBar.setProgress((step + 1.0) / modules.length);
                });
            }
            Platform.runLater(() -> {
                statusLabel.setText("Ready!");
                progressBar.setProgress(1.0);
                // Transition to main UI
                showMainStage(primaryStage);
            });
        });
    }

    private void showMainStage(Stage primaryStage) {
        VBox mainView = new VBox(10);
        mainView.setStyle("-fx-padding: 20;");
        mainView.getChildren().addAll(
            new Label("POS Main Dashboard"),
            new Label("All modules loaded successfully.")
        );
        primaryStage.setScene(new Scene(mainView, 800, 600));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
