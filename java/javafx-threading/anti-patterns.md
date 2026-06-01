# JavaFX Threading Anti-Patterns

## Running DB queries in button handlers

```java
// ❌ WRONG — blocks FX thread, freezes UI for seconds
button.setOnAction(event -> {
    List<Product> products = DB.find(Product.class).findList(); // blocks!
    table.setItems(FXCollections.observableArrayList(products));
});
```

**Fix:** Wrap in `Task`, run on background thread, update UI in `onSucceeded`.

---

## Thread.sleep() on FX thread

```java
// ❌ WRONG — freezes entire UI
button.setOnAction(event -> {
    doWork();
    Thread.sleep(2000); // FX thread blocked for 2 seconds
    statusLabel.setText("Done");
});
```

**Fix:** Use `PauseTransition` for delays, or `Task` with `updateMessage()` for background waits.

---

## Updating ObservableList from background thread

```java
// ❌ WRONG — throws IllegalStateException or corrupts state
Task<Void> task = new Task<>() {
    @Override
    protected Void call() {
        products.clear(); // WRONG — called from background thread
        products.addAll(repository.findAll());
        return null;
    }
};
```

**Fix:** Build the list in `call()`, return it, set it in `onSucceeded`.

---

## Using CompletableFuture without Platform.runLater

```java
// ❌ WRONG — table update not on FX thread
CompletableFuture.supplyAsync(() -> repository.findAll())
    .thenAccept(products -> table.setItems(...)); // may crash
```

**Fix:** Use `thenAcceptAsync(..., Platform::runLater)` or bind to `Task`.

---

## Creating too many threads manually

```java
// ❌ WRONG — unbounded thread creation
for (String id : ids) {
    new Thread(() -> processItem(id)).start(); // could create 1000 threads
}
```

**Fix:** Use `Executors.newVirtualThreadPerTaskExecutor()` or `StructuredTaskScope`.

---

## Ignoring task failure

```java
// ❌ WRONG — exceptions silently lost
Task<List<Product>> task = new Task<>() {
    @Override
    protected List<Product> call() {
        return repository.findAll(); // throws? nobody knows
    }
};
new Thread(task).start();
```

**Fix:** Always set `setOnFailed()` to log and show error.
