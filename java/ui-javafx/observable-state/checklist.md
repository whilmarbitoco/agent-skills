# Checklist: Observable State in JavaFX

## Implementation
- [ ] Every mutable view-model field is a `*Property` (not a raw field).
- [ ] UI nodes bind to properties — no manual `setText()` / `setItems()`.
- [ ] Derived values use `Bindings.create*Binding()` not manual listeners.
- [ ] Lists mutated in-place with `setAll()` / `add()` / `remove()`.
- [ ] Custom `StringConverter` for non-string types (BigDecimal, LocalDate).

## Review
- [ ] No `setText`, `setItems`, `setValue` calls outside of bindings.
- [ ] No plain `String` / `int` fields shadowing a property.
- [ ] Bindings unbound in `dispose()` or `onHidden()` for long-lived views.
- [ ] `FilteredList` / `SortedList` used for dynamic table filtering.
- [ ] No `new ObservableList` on refresh — existing list mutated.
