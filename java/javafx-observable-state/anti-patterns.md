# JavaFX Observable State Anti-Patterns

## Modifying ObservableList from background thread

```java
// WRONG — throws IllegalStateException or corrupts UI
Thread.startVirtualThread(() -> {
    products.clear(); // WRONG — background thread
    products.addAll(repository.findAll());
});
```

**Build list in background, set on FX thread: `Platform.runLater(() -> products.setAll(results))`.**

## Creating new ObservableList instead of updating existing

```java
// WRONG — breaks all existing bindings and listeners
products = FXCollections.observableArrayList(newData);
```

**Modify the existing list: `products.setAll(newData)` — preserves bindings and listeners.**

## Using ChangeListener for derived values

```java
// WRONG — fires for every change, hard to reason about
price.addListener((obs, was, newVal) -> totalLabel.setText(calculate()));
quantity.addListener((obs, was, newVal) -> totalLabel.setText(calculate()));
```

**Use InvalidationListener: `totalLabel.textProperty().bind(Bindings.createStringBinding(() -> calculate(), price, quantity))`.**

## Registering strong listener references — memory leak

```java
// WRONG — listener holds reference to scene, never GC'd
textField.textProperty().addListener((obs, was, newVal) -> doSomething());
```

**Use WeakChangeListener when the listener outlives the observable: `textField.textProperty().addListener(new WeakChangeListener<>(listener))`.**

## Manually keeping two properties in sync

```java
// WRONG — manual sync, easy to get out of sync
nameField.textProperty().addListener((obs, was, new) -> viewModel.setName(new));
viewModel.nameProperty().addListener((obs, was, new) -> nameField.setText(new));
```

**Bind once, bi-directionally: `nameField.textProperty().bindBidirectional(viewModel.nameProperty())`.**
