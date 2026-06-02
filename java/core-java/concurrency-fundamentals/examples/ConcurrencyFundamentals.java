package com.pos.core.concurrency;

import java.util.concurrent.*;

/**
 * Java 21 concurrency: virtual threads, structured concurrency,
 * CompletableFuture for async work.
 */
public class ConcurrencyFundamentals {

    // Java 21: Virtual threads — lightweight, millions possible
    public void virtualThreadPerTask() {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 10_000; i++) {
                executor.submit(() -> {
                    Thread.sleep(Duration.ofMillis(100));
                    return "done";
                });
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Structured concurrency (preview in Java 21)
    public String fetchAll() throws Exception {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            Subtask<String> user = scope.fork(() -> fetchUser());
            Subtask<String> orders = scope.fork(() -> fetchOrders());
            scope.join();
            scope.throwIfFailed();
            return user.get() + " | " + orders.get();
        }
    }

    // CompletableFuture composition
    public CompletableFuture<Sale> processSale(Sale sale) {
        return validateAsync(sale)
            .thenCompose(this::calculateTotal)
            .thenCompose(this::applyDiscounts)
            .thenCompose(this::persist);
    }

    private String fetchUser() { return "user"; }
    private String fetchOrders() { return "orders"; }
    private CompletableFuture<Sale> validateAsync(Sale s) { return CompletableFuture.completedFuture(s); }
    private CompletableFuture<Sale> calculateTotal(Sale s) { return CompletableFuture.completedFuture(s); }
    private CompletableFuture<Sale> applyDiscounts(Sale s) { return CompletableFuture.completedFuture(s); }
    private CompletableFuture<Sale> persist(Sale s) { return CompletableFuture.completedFuture(s); }
    record Sale() {}
}
