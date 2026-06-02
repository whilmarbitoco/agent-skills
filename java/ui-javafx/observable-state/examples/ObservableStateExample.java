package com.example.observable;

import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.StringConverter;
import javafx.util.converter.BigDecimalStringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

/**
 * Demonstrates observable properties, bindings, and list patterns.
 */
public class ObservableStateExample {

    private static final Logger log = LoggerFactory.getLogger(ObservableStateExample.class);
    private static final Currency PHP = Currency.getInstance("PHP");

    // ---- View-model with observable properties ----
    static class InvoiceViewModel {
        private final StringProperty customerName = new SimpleStringProperty("");
        private final ObjectProperty<BigDecimal> unitPrice =
            new SimpleObjectProperty<>(BigDecimal.ZERO);
        private final IntegerProperty quantity = new SimpleIntegerProperty(1);
        private final ObservableList<LineItem> items =
            FXCollections.observableArrayList();

        // Derived: total = sum of line-item totals
        private final ObjectBinding<BigDecimal> total =
            Bindings.createObjectBinding(() ->
                items.stream()
                    .map(LineItem::lineTotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add),
                items
            );

        StringProperty customerNameProperty() { return customerName; }
        ObjectProperty<BigDecimal> unitPriceProperty() { return unitPrice; }
        IntegerProperty quantityProperty() { return quantity; }
        ObservableList<LineItem> itemsProperty() { return items; }
        ObjectBinding<BigDecimal> totalProperty() { return total; }

        void addItem(String description) {
            BigDecimal price = unitPrice.get();
            int qty = quantity.get();
            if (price == null || price.signum() <= 0 || qty <= 0) {
                log.warn("Invalid line item: price={}, qty={}", price, qty);
                return;
            }
            items.add(new LineItem(description, price, qty));
            log.info("Added line item: {} x {} = {}", qty, description, price);
        }

        void clearItems() {
            items.clear();
        }
    }

    // ---- Value record for line items ----
    record LineItem(String description, BigDecimal unitPrice, int quantity) {
        BigDecimal lineTotal() {
            return unitPrice.multiply(BigDecimal.valueOf(quantity))
                .setScale(2, RoundingMode.HALF_UP);
        }
    }

    /**
     * Custom StringConverter for BigDecimal <-> text field binding.
     */
    static class CurrencyStringConverter extends StringConverter<BigDecimal> {
        @Override
        public String toString(BigDecimal value) {
            return value == null ? "0.00" : value.setScale(2, RoundingMode.HALF_UP).toPlainString();
        }

        @Override
        public BigDecimal fromString(String text) {
            try {
                return new BigDecimal(text).setScale(2, RoundingMode.HALF_UP);
            } catch (NumberFormatException e) {
                return BigDecimal.ZERO.setScale(2);
            }
        }
    }
}
