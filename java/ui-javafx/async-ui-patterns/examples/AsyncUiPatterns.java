package com.pos.ui.async;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.Service;

/**
 * Async UI patterns: keep JavaFX UI responsive during I/O.
 * Never block the FX Application Thread.
 */
public class AsyncUiPatterns {

    // Pattern 1: Task for one-shot async work
    <T> Task<T> createTask(Supplier<T> work) {
        return new Task<>() {
            @Override
            protected T call() throws Exception {
                updateMessage("Loading...");
                T result = work.get();
                updateMessage("Done");
                return result;
            }
        };
    }

    // Pattern 2: Service for repeatable async work
    class ProductLoaderService extends Service<java.util.List<String>> {
        @Override
        protected Task<java.util.List<String>> createTask() {
            return new Task<>() {
                @Override
                protected java.util.List<String> call() throws Exception {
                    // Simulate DB query
                    Thread.sleep(1000);
                    return java.util.List.of("Product A", "Product B");
                }
            };
        }
    }

    // Pattern 3: Platform.runLater for UI updates from background thread
    void loadDataAsync() {
        new Thread(() -> {
            // Background work
            String result = fetchFromDatabase();
            // Update UI on FX thread
            Platform.runLater(() -> {
                // label.setText(result);
            });
        }).start();
    }

    // Pattern 4: CompletableFuture + Platform.runLater
    void loadWithFuture() {
        CompletableFuture.supplyAsync(this::fetchFromDatabase)
            .thenAcceptAsync(result -> {
                // label.setText(result);
            }, Platform::runLater);
    }

    String fetchFromDatabase() { return "data"; }
}
