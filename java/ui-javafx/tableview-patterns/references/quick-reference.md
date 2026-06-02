# Quick Reference: JavaFX TableView APIs

| API | Purpose | Key Methods |
|-----|---------|-------------|
| `TableView<T>` | Tabular display | `setItems()`, `getColumns()`, `getSelectionModel()` |
| `TableColumn<T, V>` | Column definition | `setCellValueFactory()`, `setCellFactory()`, `setOnEditCommit()` |
| `FilteredList<E>` | Live search wrapper | `setPredicate(predicate)`, wraps `ObservableList` |
| `SortedList<E>` | Live sort wrapper | `comparatorProperty().bind(table.comparatorProperty()) |
| `TextFieldTableCell` | Inline text editing | `forTableColumn(converter)` |
| `TableCell<T,V>` | Custom cell rendering | `updateItem(T, boolean)` — override for styling |
| TableView.`CONSTRAINED_RESIZE_POLICY` | Fill available width | Call once on TableView |
