# Anti-Patterns: Observable State in JavaFX

## Pattern 1 — Manual setText Instead of Binding

```java
// WRONG: Stale field, manual sync
String userName = user.getName();
label.setText(userName);
// If user.name changes later, label is wrong.
```

```java
// FIX: Bind label to the property
label.textProperty().bind(user.nameProperty());
// Label updates automatically whenever nameProperty changes.
```

## Pattern 2 — New ObservableList on Every Refresh

```java
// WRONG: Replacing the list breaks TableView selection
tableView.setItems(FXCollections.observableArrayList(repo.findAll()));
// Every call above creates a new list — selection resets, scroll jumps.
```

```java
// FIX: Mutate the existing ObservableList in place
var items = FXCollections.observableArrayList();
tableView.setItems(items);
// … later …
items.setAll(repo.findAll()); // preserves list identity
```

## Pattern 3 — Mirroring Observable with a Plain Field

```java
// WRONG: Stale copy
StringProperty src = viewModel.amountProperty();
String mirror = src.get(); // captured once
src.addListener((obs, o, n) -> mirror = n); // must remember to update manually
```

```java
// FIX: Bind derived value, never mirror
var mirror = new SimpleStringProperty();
mirror.bind(viewModel.amountProperty());
```

## Pattern 4 — Forgetting to Unbind

```java
// WRONG: Binding keeps invisible node alive
label.textProperty().bind(heavyViewModel.dataProperty());
// label is removed from scene but binding prevents GC.
```

```java
// FIX: Unbind when view is disposed
label.textProperty().unbind();
// Or use弱 listener pattern for automatic cleanup.
```

## Pattern 5 — Raw ChangeListener Instead of Bindings API

```java
// WRONG: Verbose manual listener
a.addListener((obs, o, n) -> update());
b.addListener((obs, o, n) -> update());
// Duplicated listener, hard to reason about dependency order.
```

```java
// FIX: Derived binding
Label totalLabel = new Label();
totalLabel.textProperty().bind(
    Bindings.createStringBinding(
        () -> a.get().add(b.get()).toPlainString(),
        a, b
    )
);
```
