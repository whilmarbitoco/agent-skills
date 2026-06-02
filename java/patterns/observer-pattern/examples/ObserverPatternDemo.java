package com.pos.patterns.observer;

import java.beans.*;
import java.util.*;

/**
 * Observer pattern: notify listeners of state changes.
 * JavaFX properties are built-in observers.
 */
public class ObserverPatternDemo {

    // JavaFX property-based observer (preferred)
    static class CartModel {
        private final javafx.beans.property.DoubleProperty total =
            new javafx.beans.property.SimpleDoubleProperty(0.0);

        public javafx.beans.property.DoubleProperty totalProperty() { return total; }

        public void addItem(double price) {
            total.set(total.get() + price);
            // All listeners notified automatically
        }
    }

    // Custom observer for non-UI events
    interface StockListener {
        void onStockChanged(String productId, int newQty);
    }

    static class StockService {
        private final List<StockListener> listeners = new ArrayList<>();

        public void addListener(StockListener l) { listeners.add(l); }

        public void updateStock(String productId, int qty) {
            // ... update DB ...
            listeners.forEach(l -> l.onStockChanged(productId, qty));
        }
    }
}
