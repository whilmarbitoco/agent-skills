# MVVM with JavaFX — Quick Reference

## Property types
| Property | Type | Use for |
|----------|------|---------|
| Text label | `StringProperty` | Display strings |
| Selected item | `ObjectProperty<T>` | Current selection |
| List | `ObservableList<T>` | Table/list contents |
| State flag | `BooleanProperty` | Loading, valid, dirty |

## Thread rule
All property mutations → FX Application Thread only.
Use `Platform.runLater(() -> prop.set(value))` from background threads.

## Binding patterns
```java
// One-way
label.textProperty().bind(vm.invoiceCount());

// Computed
total.bind(Bindings.createObjectBinding(() -> calc(), subtotal, taxRate));

// Format
label.textProperty().bind(Bindings.format("Total: ₱%.2f", vm.totalAmount()));
```
