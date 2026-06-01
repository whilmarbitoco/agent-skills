import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Validation utility: validates a TextField against a predicate,
 * updates an error label, and returns a boolean property.
 * Compose multiple validators for a complete form.
 */
public class ValidationUtil {

    /**
     * Validates a TextField and wires error display + invalid styling.
     * Returns a read-only boolean property indicating validity.
     */
    public static javafx.beans.property.ReadOnlyBooleanProperty validate(
            TextField field,
            Label errorLabel,
            java.util.function.Predicate<String> validator,
            String errorMessage) {

        javafx.beans.property.SimpleBooleanProperty valid =
            new javafx.beans.property.SimpleBooleanProperty(false);

        Runnable check = () -> {
            String value = field.getText() == null ? "" : field.getText().trim();
            boolean isValid = validator.test(value);
            valid.set(isValid);
            errorLabel.setText(isValid ? "" : errorMessage);
            if (isValid) {
                errorLabel.getStyleClass().remove("error-visible");
            } else {
                errorLabel.getStyleClass().add("error-visible");
            }
        };

        // Validate on focus lost
        field.focusedProperty().addListener((obs, wasFocused, isFocused) -> {
            if (!isFocused) check.run();
        });

        // Clear error when user types
        field.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!errorLabel.getText().isEmpty()) check.run();
        });

        return valid;
    }
}

// ===== Usage example =====
class ProductFieldBinder {
    private final TextField nameField = new TextField();
    private final TextField priceField = new TextField();
    private final Label nameError = new Label();
    private final Label priceError = new Label();
    private final javafx.scene.control.Button submitButton = new javafx.scene.control.Button("Submit");

    public void bind() {
        var nameValid = ValidationUtil.validate(
            nameField, nameError,
            v -> !v.isBlank() && v.length() <= 200,
            "Name is required (max 200 chars)"
        );
        var priceValid = ValidationUtil.validate(
            priceField, priceError,
            v -> {
                try { return new java.math.BigDecimal(v).compareTo(java.math.BigDecimal.ZERO) > 0; }
                catch (Exception e) { return false; }
            },
            "Price must be greater than zero"
        );

        // ✅ Submit disabled unless ALL valid
        submitButton.disableProperty().bind(
            Bindings.createBooleanBinding(
                () -> !nameValid.get() || !priceValid.get(),
                nameValid, priceValid
            )
        );
    }
}
