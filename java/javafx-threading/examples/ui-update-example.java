import javafx.application.Platform;

/**
 * Three ways to get results back to the FX thread.
 * Use the one that fits your situation.
 */

// === Option 1: Task.onSucceeded (preferred) ===
Task<List<Product>> task = new Task<>() {
    @Override
    protected List<Product> call() {
        return repository.findAll();
    }
};
task.setOnSucceeded(event -> table.setItems(
    FXCollections.observableArrayList(task.getValue())
));
new Thread(task).start();

// === Option 2: CompletableFuture ===
CompletableFuture.supplyAsync(() -> repository.findAll())
    .thenAcceptAsync(products ->
        table.setItems(FXCollections.observableArrayList(products)),
        Platform::runLater
    );

// === Option 3: Virtual thread + runLater (Java 21) ===
Thread.startVirtualThread(() -> {
    List<Product> products = repository.findAll();
    Platform.runLater(() -> {
        table.setItems(FXCollections.observableArrayList(products));
        statusLabel.setText("Loaded " + products.size() + " products");
    });
});

// === NEVER do this — crashes or corrupts UI ===
// Thread.startVirtualThread(() -> {
//     table.setItems(...)  // WRONG — not on FX thread
// });
