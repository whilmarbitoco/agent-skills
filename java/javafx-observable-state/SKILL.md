# Skill: JavaFX Observable State

Reactive state with JavaFX properties and bindings.

## Core Concepts
- `SimpleObjectProperty<T>` — mutable reactive holder
- `ObservableList<T>` / `FXCollections.observableArrayList()` — mutable list
- `ChangeListener` — fires on every change (new + old value)
- `InvalidationListener` — fires on invalidate, lazy re-read (cheaper)
- `Bindings` API — `Bindings.createXxxBinding()`, `bind()`, `bindBidirectional()`

## Rules
1. Use the most specific Property type (`SimpleStringProperty`, `SimpleDoubleProperty`)
2. Prefer `InvalidationListener` over `ChangeListener` for derived/computed values
3. Use `Bindings` API for totals, concatenations, and derived properties
4. Never hold strong references to listeners — use `WeakInvalidationListener` if needed
5. Package-level or `ReadOnlyProperty` for ViewModel → View exposure
6. Initialize `ObservableList` with `FXCollections.observableArrayList(extractors...)`

## Anti-patterns
- Manual `set()` calls scattered across code instead of bindings
- `ChangeListener` where `InvalidationListener` suffices
- Polling property values instead of listening
- Holding listener references that prevent GC

## Relates to
- mvvm-javafx
- javafx-threading
- form-validation
