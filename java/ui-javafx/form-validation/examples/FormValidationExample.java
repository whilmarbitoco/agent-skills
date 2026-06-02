package com.example.validation;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

/**
 * Demonstrates real-time form validation with TextFormatter,
 * StringConverter, and observable error lists on the ViewModel.
 */
public class FormValidationExample {

    private static final Logger log = LoggerFactory.getLogger(FormValidationExample.class);

    // ---- Validation error record (Java 21) ----
    record ValidationError(String field, String message) {
        @Override
        public String toString() { return field + ": " + message; }
    }

    // ---- Model: product with invariants ----
    static class Product {
        private String name = "";
        private BigDecimal price = BigDecimal.ZERO;
        int quantity = 0;

        Optional<ValidationError> validateName(String name) {
            if (name == null || name.isBlank())
                return Optional.of(new ValidationError("name", "Name is required"));
            if (name.length() > 100)
                return Optional.of(new ValidationError("name", "Name must be ≤ 100 characters"));
            return Optional.empty();
        }

        Optional<ValidationError> validatePrice(BigDecimal price) {
            if (price == null || price.signum() <= 0)
                return Optional.of(new ValidationError("price", "Price must be greater than 0"));
            if (price.scale() > 2)
                return Optional.of(new ValidationError("price", "Price can have at most 2 decimal places"));
            return Optional.empty();
        }

        Optional<ValidationError> validateQuantity(int qty) {
            if (qty < 0)
                return Optional.of(new ValidationError("quantity", "Quantity cannot be negative"));
            if (qty > 10_000)
                return Optional.of(new ValidationError("quantity", "Quantity cannot exceed 10,000"));
            return Optional.empty();
        }
    }

    // ---- ViewModel: observable properties + validation ----
    static class ProductFormViewModel {
        private final Product model;
        private final StringProperty name = new SimpleStringProperty("");
        private final ObjectProperty<BigDecimal> price = new SimpleObjectProperty<>(null);
        private final IntegerProperty quantity = new SimpleIntegerProperty(0);
        private final ListProperty<ValidationError> errors =
            new SimpleListProperty<>(FXCollections.observableArrayList());
        private final BooleanProperty isValid = new SimpleBooleanProperty(false);

        ProductFormViewModel(Product model) {
            this.model = model;
            // Re-run all validation whenever any field changes
            name.addListener((obs, o, n) -> revalidate());
            price.addListener((obs, o, n) -> revalidate());
            quantity.addListener((obs, o, n) -> revalidate());
        }

        StringProperty nameProperty() { return name; }
        ObjectProperty<BigDecimal> priceProperty() { return price; }
        IntegerProperty quantityProperty() { return quantity; }
        ObservableList<ValidationError> getErrors() { return errors.get(); }
        ReadOnlyBooleanProperty validProperty() { return isValid; }

        void revalidate() {
            List<ValidationError> all = new ArrayList<>();
            model.validateName(name.get()).ifPresent(all::add);
            model.validatePrice(price.get()).ifPresent(all::add);
            model.validateQuantity(quantity.get()).ifPresent(all::add);
            errors.setAll(all);
            isValid.set(all.isEmpty());
            if (!all.isEmpty()) {
                log.warn("Validation errors: {}", all);
            }
        }
    }

    // ---- TextFormatter factories ----
    static class Formatters {

        /** Only digits and at most one decimal point. */
        static TextFormatter<BigDecimal> decimalFormatter() {
            UnaryOperator<TextFormatter.Change> filter = change -> {
                String text = change.getControlNewText();
                if (text.matches("\\d*\\.?\\d*")) return change;
                return null; // reject
            };

            StringConverter<BigDecimal> converter = new StringConverter<>() {
                @Override
                public String toString(BigDecimal value) {
                    return value == null ? "" : value.setScale(2, RoundingMode.HALF_UP).toPlainString();
                }

                @Override
                public BigDecimal fromString(String text) {
                    if (text == null || text.isBlank()) return null;
                    try {
                        return new BigDecimal(text).setScale(2, RoundingMode.HALF_UP);
                    } catch (NumberFormatException e) {
                        return null;
                    }
                }
            };

            return new TextFormatter<>(converter, null, filter);
        }

        /** Digits only. */
        static TextFormatter<Integer> integerFormatter() {
            UnaryOperator<TextFormatter.Change> filter = change -> {
                String text = change.getControlNewText();
                if (text.matches("\\d*")) return change;
                return null;
            };

            StringConverter<Integer> converter = new StringConverter<>() {
                @Override
                public String toString(Integer value) {
                    return value == null ? "" : value.toString();
                }

                @Override
                public Integer fromString(String text) {
                    if (text == null || text.isBlank()) return 0;
                    try { return Integer.parseInt(text); }
                    catch (NumberFormatException e) { return 0; }
                }
            };

            return new TextFormatter<>(converter, 0, filter);
        }

        /** Any text, max 100 chars. */
        static TextFormatter<String> maxLengthFormatter(int maxLen) {
            UnaryOperator<TextFormatter.Change> filter = change -> {
                String text = change.getControlNewText();
                if (text.length() <= maxLen) return change;
                return null;
            };
            return new TextFormatter<>(change -> change);
        }
    }
}
