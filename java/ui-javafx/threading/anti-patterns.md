# Anti-Patterns: Threading in JavaFX

## Pattern 1 – Blocking the FX Thread

```java
// WRONG: Running I/O on the FX Application Thread
button.setOnAction(e -> {
    String data = httpClient.send(request, BodyHandlers.ofString()).body();
    label.setText(data); // UI freezes until HTTP completes
});
```

```java
// FIX: Use Task with virtual thread for I/O
button.setOnAction(e -> {
    Task<String> task = new Task<>() {
        @Override
        protected String call() throws Exception {
            return httpClient.send(request, BodyHandlers.ofString()).body();
        }
    };
    task.setOnSucceeded(evt -> label.setText(task.getValue()));
    task.setOnFailed(evt -> label.setText("Error: " + task.getException().getMessage()));
    Thread.ofVirtual().start(task);
});
```

## Pattern 2 – Updating ObservableList from Background Thread

```java
// WRONG: Modifying an ObservableList off the FX thread
var items = FXCollections.observableArrayList();
new Thread(() -> {
    List<String> results = database.fetchAll();
    items.addAll(results); // throws IllegalStateException
}).start();
```

```java
// FIX: Marshal to FX thread via Platform.runLater
var items = FXCollections.observableArrayList();
Task<List<String>> task = new Task<>() {
    @Override
    protected List<String> call() {
        return database.fetchAll();
    }
};
task.setOnSucceeded(evt -> items.addAll(task.getValue()));
Thread.ofVirtual().start(task);
```

## Pattern 3 – Swallowing InterruptedException

```java
// WRONG: Silent interrupt swallow destroys cancellation semantics
try {
    Thread.sleep(1000);
} catch (InterruptedException e) {
    // ignored
}
```

```java
// FIX: Restore the interrupt flag
try {
    Thread.sleep(1000);
} catch (InterruptedException e) {
    Thread.currentThread().interrupt();
    return; // exit the task
}
```

## Pattern 4 – Manual runLater Instead of Task Binding

```java
// WRONG: Manual Platform.runLater everywhere
new Thread(() -> {
    var data = loadData();
    Platform.runLater(() -> label.setText(data));
    var meta = loadMeta();
    Platform.runLater(() -> metaLabel.setText(meta));
}).start();
```

```java
// FIX: Bind Task properties to UI
Task<Data> task = new Task<>() {
    @Override
    protected Data call() {
        updateMessage("Loading…");
        return loadData();
    }
};
label.textProperty().bind(task.messageProperty());
Thread.ofVirtual().start(task);
```

## Pattern 5 – Creating Raw Threads Instead of Using Service

```java
// WRONG: Raw thread for a repeatable operation
new Thread(() -> report.generate()).start();
new Thread(() -> report.generate()).start(); // race: two runners at once
```

```java
// FIX: Service with built-in restart/cancel semantics
Service<Report> service = new Service<>() {
    @Override
    protected Task<Report> createTask() {
        return new Task<>() {
            @Override
            protected Report call() { return report.generate(); }
        };
    }
};
service.start();
// service.restart(); // safe: cancels previous, starts new
```
