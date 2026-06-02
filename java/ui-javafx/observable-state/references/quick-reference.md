# Quick Reference: JavaFX Observable APIs

| API | Purpose | Key Methods |
|-----|---------|-------------|
| `SimpleStringProperty` | Mutable string observable | `get()`, `set()`, `bind()`, `addListener()` |
| `SimpleObjectProperty<T>` | Mutable object observable | `get()`, `set()`, `bind()`, `bindBidirectional()` |
| `SimpleIntegerProperty` | Mutable int observable | `get()`, `set()`, `intValue()` |
| `Bindings` | Derived/composite bindings | `createObjectBinding()`, `createStringBinding()`, `concat()` |
| `ObservableList<E>` | Mutable list observable | `add()`, `remove()`, `setAll()`, `addListener(ListChangeListener)` |
| `FilteredList<E>` | Filtered view of ObservableList | `predicateProperty()` |
| `SortedList<E>` | Sorted view of ObservableList | `comparatorProperty()` |
| `StringConverter<T>` | Bidirectional text conversion | `toString(T)`, `fromString(String)` |
