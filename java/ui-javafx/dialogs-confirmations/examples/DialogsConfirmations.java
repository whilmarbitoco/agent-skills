package com.pos.ui.dialogs;

import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import java.util.Optional;

/**
 * Dialog patterns: confirmations, alerts, custom dialogs.
 * Always use JavaFX dialogs — never Swing.
 */
public class DialogsConfirmations {

    // Confirmation dialog
    boolean confirmDelete(String itemName) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Delete " + itemName + "?");
        alert.setContentText("This action cannot be undone.");
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    // Error dialog
    void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Input dialog
    Optional<String> askForName() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("New Product");
        dialog.setHeaderText("Enter product name");
        dialog.setContentText("Name:");
        return dialog.showAndWait();
    }

    // Custom dialog with form
    Optional<ProductFormData> showProductForm() {
        Dialog<ProductFormData> dialog = new Dialog<>();
        dialog.setTitle("Add Product");
        dialog.setHeaderText("Enter product details");

        // Build form grid
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField nameField = new TextField();
        TextField priceField = new TextField();
        grid.addRow(0, new Label("Name:"), nameField);
        grid.addRow(1, new Label("Price:"), priceField);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(btn -> {
            if (btn == ButtonType.OK) {
                return new ProductFormData(nameField.getText(), priceField.getText());
            }
            return null;
        });

        return dialog.showAndWait();
    }

    record ProductFormData(String name, String price) {}
}
