package com.pos.ui.components;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.*;

/**
 * Reusable JavaFX components: custom controls for POS.
 * Extend Region or Control for full reusability.
 */
public class ReusableComponents {

    // Custom search bar component
    public static class SearchBar extends HBox {
        private final TextField searchField = new TextField();
        private final Button searchButton = new Button("Search");

        public SearchBar() {
            setSpacing(8);
            setAlignment(Pos.CENTER_LEFT);
            searchField.setPromptText("Search products...");
            searchField.setPrefWidth(300);
            HBox.setHgrow(searchField, Priority.ALWAYS);
            getChildren().addAll(searchField, searchButton);
        }

        public void setOnSearch(Runnable action) {
            searchButton.setOnAction(e -> action.run());
            searchField.setOnAction(e -> action.run());
        }

        public String getText() { return searchField.getText(); }
    }

    // Custom status badge
    public static class StatusBadge extends Label {
        public StatusBadge(String text, String color) {
            setText(text);
            setStyle(String.format(
                "-fx-background-color: %s; -fx-text-fill: white; " +
                "-fx-padding: 2 8; -fx-background-radius: 4;", color
            ));
        }
    }

    // Custom card container
    public static class Card extends VBox {
        public Card() {
            setStyle("-fx-background-color: white; -fx-padding: 16; " +
                     "-fx-background-radius: 8; " +
                     "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 4, 0, 0, 2);");
            setSpacing(8);
        }
    }
}
