package com.simplepos.ui.events;

import javafx.application.Platform;
import java.util.*;

/**
 * Lightweight event bus for UI components.
 * No global singleton — inject via constructor.
 */
public class UiEventBus {

    private final Map<Class<?>, List<java.util.function.Consumer<Object>>> listeners = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <T> void subscribe(Class<T> eventType, java.util.function.Consumer<T> handler) {
        listeners.computeIfAbsent(eventType, k -> new ArrayList<>())
                 .add((java.util.function.Consumer<Object>) handler);
    }

    public void publish(Object event) {
        List<java.util.function.Consumer<Object>> handlers = listeners.get(event.getClass());
        if (handlers == null) return;
        for (var handler : handlers) {
            if (Platform.isFxApplicationThread()) {
                handler.accept(event);
            } else {
                Platform.runLater(() -> handler.accept(event));
            }
        }
    }
}

// Domain events — use records
public record CartUpdatedEvent(List<CartLine> items) {}
public record SaleCompletedEvent(long saleId, Money total) {}

// In ViewModel:
// eventBus.subscribe(CartUpdatedEvent.class, event -> recalculateTotal(event.items()));
// eventBus.publish(new SaleCompletedEvent(sale.getId(), sale.getTotal()));
