# Checklist: TableView in JavaFX

## Implementation
- [ ] Use lambda `CellValueFactory` — never `PropertyValueFactory`.
- [ ] Source list wrapped in `FilteredList` → `SortedList` → `TableView`.
- [ ] `CONSTRAINED_RESIZE_POLICY` set on TableView.
- [ ] Editable columns validate input in `onEditCommit`.
- [ ] Selection model bound to button disable/enable states.

## Review
- [ ] No string-based property references (`PropertyValueFactory("field")`).
- [ ] No business logic inside `CellValueFactory` / `CellFactory`.
- [ ] `setItems` called once; filtering done via `FilteredList.setPredicate()`.
- [ ] Empty state handled (placeholder label when list is empty).
- [ ] Column headers translated / externalized (no hardcoded English in View).
