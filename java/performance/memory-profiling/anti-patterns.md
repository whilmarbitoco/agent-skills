# Memory Profiling Anti-Patterns

## Anonymous listener capturing Node reference — memory leak

```java
// WRONG — lambda captures tableView, prevents GC
someProperty.addListener((obs, was, newVal) -> {
    tableView.refresh(); // holds entire Scene reference
});
```

**Use WeakChangeListener. Or remove listener on dispose.**

## Not unbinding on screen close

```java
// WRONG — binding keeps ViewModel alive after screen closes
label.textProperty().bind(viewModel.statusProperty());
```

**Unbind on close: `label.textProperty().unbind()`.**

## Platform.runLater stored in list — grows forever

```java
// WRONG — pending runLater tasks accumulate
pending.add(() -> updateUI());
```

**Don't queue runLater. Use Task/Service for background work with completion handlers.**
