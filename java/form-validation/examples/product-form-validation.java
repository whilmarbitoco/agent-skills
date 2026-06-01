import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.geometry.HPos;
import java.math.BigDecimal;
import java.util.Currency;

/**
 * Product form with real-time validation.
 * Validates on focus lost, shows errors near fields,
 * uses CSS :invalid for visual feedback,
 * disables submit until all fields are valid.
 */
public class ProductFormExample extends GridPane {

    static final Currency PHP = Currency.getInstance("PHP");

    private final TextField nameField = new TextField();
    private final TextField priceField = new TextField();
    private final TextField stockField = new TextField();
    private final Label nameError = new Label();
    private final Label priceError = new Label();
    private final Label stockError = new Label();
    private final javafx.scene.control.Button saveButton = new javafx.scene.control.Button("Save");

    public ProductFormExample() {
        setHgap(8);
        setVgap(4);
        setPadding(new Insets(24));

        javafx.scene.layout.ColumnConstraints col0 = new javafx.scene.layout.ColumnConstraints();
        col0.setPrefWidth(80);
        col0.setHalignment(HPos.RIGHT);
        javafx.scene.layout.ColumnConstraints col1 = new javafx.scene.layout.ColumnConstraints();
        col1.setHgrow(javafx.scene.layout.Priority.ALWAYS);
        getColumnConstraints().addAll(col0, col1);

        addFormRow(0, "Name *", nameField, nameError);
        addFormRow(1, "Price *", priceField, priceError);
        addFormRow(2, "Stock *", stockField, stockError);
        add(saveButton, 1, 3);
        setMargin(saveButton, new Insets(12, 0, 0, 0));

        setupValidation();
    }

    private void addFormRow(int row, String label, TextField field, Label error) {
        add(new Label(label), 0, row);
        add(field, 1, row);
        error.setStyle("-fx-text-fill: #d32f2f; -fx-font-size: 11px;");
        add(error, 1, row + 3); // error below field
    }

    private void setupValidation() {
        // ✅ Validate on focus lost
        nameField.focusedProperty().addListener((obs, wasFocused, isFocused) -> {
            if (!isFocused) validateName();
        });
        priceField.focusedProperty().addListener((obs, wasFocused, isFocused) -> {
            if (!isFocused) validatePrice();
        });
        stockField.focusedProperty().addListener((obs, wasFocused, isFocused) -> {
            if (!isFocused) validateStock();
        });

        // ✅ Clear errors on typing
        nameField.textProperty().addListener((obs, old, val) -> {
            if (!nameError.getText().isEmpty()) validateName();
        });
        priceField.textProperty().addListener((obs, old, val) -> {
            if (!priceError.getText().isEmpty()) validatePrice();
        });

        // ✅ Disable submit until valid
        saveButton.disableProperty().bind(
            javafx.beans.binding.Bindings.createBooleanBinding(
                () -> !isNameValid() || !isPriceValid() || !isStockValid(),
                nameField.textProperty(), priceField.textProperty(), stockField.textProperty()
            )
        );
    }

    boolean isNameValid() {
        String val = nameField.getText();
        return val != null && !val.isBlank() && val.length() <= 200;
    }

    boolean isPriceValid() {
        try {
            BigDecimal val = new BigDecimal(priceField.getText().trim());
            return val.compareTo(BigDecimal.ZERO) > 0;
        } catch (Exception e) {
            return false;
        }
    }

    boolean isStockValid() {
        try {
            int val = Integer.parseInt(stockField.getText().trim());
            return val >= 0;
        } catch (Exception e) {
            return false;
        }
    }

    boolean validateName() {
        if (!isNameValid()) {
            nameError.setText("Name is required (max 200 chars)");
            nameField.getStyleClass().add("field-error");
            return false;
        }
        nameError.setText("");
        return true;
    }

    boolean validatePrice() {
        if (!isPriceValid()) {
            priceError.setText("Price must be greater than 0");
            priceField.getStyleClass().add("field-error");
            return false;
        }
        priceError.setText("");
        return true;
    }

    boolean validateStock() {
        if (!isStockValid()) {
            stockError.setText("Stock must be 0 or more");
            stockField.getStyleClass().add("field-error");
            return false;
        }
        stockError.setText("");
        return true;
    }

    /** Call on submit attempt — validates all fields even if focus wasn't lost. */
    public boolean validateAll() {
        return validateName() && validatePrice() && validateStock();
    }
}
