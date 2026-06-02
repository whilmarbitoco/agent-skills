package com.example.mvvm;

import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

/**
 * MVVM demonstration: Model (pure domain), ViewModel (observable facade),
 * and a pseudo-View that binds to ViewModel properties.
 *
 * All money uses BigDecimal + PHP currency. No JavaFX UI imports in Model.
 */
public class MvvmExample {

    private static final Logger log = LoggerFactory.getLogger(MvvmExample.class);
    private static final Currency PHP = Currency.getInstance("PHP");

    // ===================== MODEL (zero JavaFX imports) =====================

    /**
     * Pure domain entity — no JavaFX, no UI knowledge.
     */
    static class Product {
        private final String sku;
        private String name;
        private BigDecimal unitPrice;
        private int stock;

        Product(String sku, String name, BigDecimal unitPrice, int stock) {
            this.sku = sku;
            this.name = name;
            this.unitPrice = unitPrice.setScale(2, RoundingMode.HALF_UP);
            this.stock = stock;
        }

        String sku() { return sku; }
        String name() { return name; }
        BigDecimal unitPrice() { return unitPrice; }
        int stock() { return stock; }

        void rename(String newName) { this.name = newName; }
        void reprice(BigDecimal newPrice) { this.unitPrice = newPrice.setScale(2, RoundingMode.HALF_UP); }
        void restock(int qty) { this.stock += qty; }

        /** Domain business rule: 10% discount if price > 1000. */
        BigDecimal discountRate() {
            return unitPrice.compareTo(new BigDecimal("1000")) > 0
                ? new BigDecimal("0.10") : BigDecimal.ZERO;
        }

        BigDecimal discountedPrice() {
            return unitPrice.subtract(unitPrice.multiply(discountRate()))
                .setScale(2, RoundingMode.HALF_UP);
        }
    }

    // ===================== VIEWMODEL =====================

    /**
     * Wraps Model in observable properties. JavaFX-aware but logic-free.
     */
    static class ProductViewModel {
        private final Product model;

        private final StringProperty sku = new SimpleStringProperty();
        private final StringProperty name = new SimpleStringProperty();
        private final ObjectProperty<BigDecimal> unitPrice = new SimpleObjectProperty<>();
        private final IntegerProperty stock = new SimpleIntegerProperty();
        private final ObjectProperty<BigDecimal> discountRate = new SimpleObjectProperty<>();
        private final ObjectProperty<BigDecimal> discountedPrice = new SimpleObjectProperty<>();
        private final BooleanProperty valid = new SimpleBooleanProperty();

        ProductViewModel(Product model) {
            this.model = model;
            syncFromModel();
            // Recompute derived fields when name or price changes
            name.addListener((obs, o, n) -> syncBackAndRefresh());
            unitPrice.addListener((obs, o, n) -> syncBackAndRefresh());
            // Validity: name not blank, price > 0, stock >= 0
            valid.bind(Bindings.createBooleanBinding(
                () -> !name.get().isBlank()
                    && unitPrice.get() != null && unitPrice.get().signum() > 0
                    && stock.get() >= 0,
                name, unitPrice, stock
            ));
        }

        void syncFromModel() {
            sku.set(model.sku());
            name.set(model.name());
            unitPrice.set(model.unitPrice());
            stock.set(model.stock());
            discountRate.set(model.discountRate());
            discountedPrice.set(model.discountedPrice());
        }

        void syncBackAndRefresh() {
            model.rename(name.get());
            if (unitPrice.get() != null) model.reprice(unitPrice.get());
            syncFromModel(); // recompute derived
            log.info("Product {} synced: price={}, discount={}", sku.get(), unitPrice.get(), discountRate.get());
        }

        // --- Exposed properties for View binding ---
        StringProperty skuProperty() { return sku; }
        StringProperty nameProperty() { return name; }
        ObjectProperty<BigDecimal> unitPriceProperty() { return unitPrice; }
        IntegerProperty stockProperty() { return stock; }
        ObservableValue<BigDecimal> discountRateProperty() { return discountRate; }
        ObservableValue<BigDecimal> discountedPriceProperty() { return discountedPrice; }
        ObservableValue<Boolean> validProperty() { return valid; }
    }

    // ===================== VIEW (pseudo — would be FXML controller) =====================

    /**
     * Simulates a view class that binds to the ViewModel.
     * In real code this would be an FXML controller.
     */
    static class ProductView {
        private final ProductViewModel vm;

        ProductView(ProductViewModel vm) {
            this.vm = vm;
            // In real code:
            // nameField.textProperty().bindBidirectional(vm.nameProperty());
            // priceField.textProperty().bindBidirectional(vm.unitPriceProperty(), new CurrencyStringConverter());
            // saveButton.disableProperty().bind(vm.validProperty().not());
        }
    }
}
