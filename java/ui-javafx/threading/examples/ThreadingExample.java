package com.example.threading;

import javafx.concurrent.Task;
import javafx.concurrent.Service;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

/**
 * Demonstrates Task and Service patterns for JavaFX threading.
 */
public class ThreadingExample {

    private static final Logger log = LoggerFactory.getLogger(ThreadingExample.class);
    private static final Currency PHP = Currency.getInstance("PHP");

    // ---- Value record (Java 21) ----
    record Order(String id, BigDecimal amount, String status) {}

    /**
     * Simulates an I/O-bound repository.
     */
    static class OrderRepository {
        List<Order> fetchPending() {
            try {
                Thread.sleep(500); // simulate latency
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Fetch interrupted", e);
            }
            return List.of(
                new Order("ORD-001", new BigDecimal("1500.00"), "PENDING"),
                new Order("ORD-002", new BigDecimal("2300.50"), "PENDING")
            );
        }

        void approve(String id) {
            log.info("Approved order {}", id);
        }
    }

    /**
     * Task that loads orders off the FX thread.
     */
    static class LoadOrdersTask extends Task<ObservableList<Order>> {
        private final OrderRepository repo;

        LoadOrdersTask(OrderRepository repo) {
            this.repo = repo;
        }

        @Override
        protected ObservableList<Order> call() throws Exception {
            updateMessage("Loading orders…");
            updateProgress(0, 1);
            List<Order> orders = repo.fetchPending();
            updateProgress(1, 1);
            updateMessage("Done");
            return FXCollections.observableArrayList(orders);
        }
    }

    /**
     * Service that wraps the load task for restartable use.
     */
    static class OrderService extends Service<ObservableList<Order>> {
        private final OrderRepository repo;

        OrderService(OrderRepository repo) {
            this.repo = repo;
        }

        @Override
        protected Task<ObservableList<Order>> createTask() {
            return new LoadOrdersTask(repo);
        }
    }

    /**
     * Approve action that runs DB write off the FX thread and updates UI via properties.
     */
    static classApproveViewModel {
        private final OrderRepository repo;
        private final StringProperty statusMessage = new SimpleStringProperty("Ready");

        ApproveViewModel(OrderRepository repo) {
            this.repo = repo;
        }

        StringProperty statusMessageProperty() { return statusMessage; }

        void approveOrder(Order order) {
            if (isCancelled(order)) return;
            Task<Void> task = new Task<>() {
                @Override
                protected Void call() {
                    repo.approve(order.id());
                    return null;
                }
            };
            task.setOnSucceeded(e ->
                statusMessage.set("Approved " + order.id()));
            task.setOnFailed(e ->
                statusMessage.set("Failed: " + e.getSource().getException().getMessage()));
            Thread.ofVirtual().start(task);
        }

        private boolean isCancelled(Order order) {
            return !"PENDING".equals(order.status());
        }
    }
}
