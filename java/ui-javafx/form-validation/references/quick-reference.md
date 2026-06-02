# Quick Reference: JavaFX Form Validation APIs

| API | Purpose | Key Methods |
|-----|---------|-------------|
| `TextFormatter<V>` | Input filter + converter for TextField | constructor arg (filter), `valueProperty()`, `getValue()` |
| `TextFormatter.Change` | Mutation event on text change | `getControlNewText()`, `getRangeStart()`, `setText()` |
| `StringConverter<V>` | Bidirectional text ↔ value | `toString(V)`, `fromString(String)` |
| `UnaryOperator<Change>` | Filter function for TextFormatter | Return `null` to reject change |
| `ObservableList<ValidationError>` | Live error list for UI | `setAll()`, bound to error panel |
| `SimpleListProperty<E>` | Mutable property holding list | `get()`, `set()`, `addListener()` |
