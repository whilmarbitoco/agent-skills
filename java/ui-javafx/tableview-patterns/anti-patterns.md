# Anti-Patterns: TableView in JavaFX

## Pattern 1 — PropertyValueFactory with Magic Strings

```java
// WRONG: String-based property name breaks silently on rename
TableColumn<Order, String> col = new TableColumn<>("Status");
col.setCellValueFactory(new PropertyValueFactory<>("status"));
// If field renamed to "orderStatus", compiles fine, shows blank cells.
```

```java
// FIX: Lambda factory — type-safe, refactor-safe
col.setCellValueFactory(data -> {
    Order order = data.getValue();
    return new SimpleStringProperty(order.status());
});
```

## Pattern 2 — Replacing the Items List on Filter

```java
// WRONG: New list on every keystroke resets table state
searchField.textProperty().addListener((obs, o, n) -> {
    List<Product> filtered = allProducts.stream()
        .filter(p -> p.name().contains(n))
        .toList();
    table.setItems(FXCollections.observableArrayList(filtered));
    // Selection lost, scroll resets to top, sort order gone.
});
```

```java
// FIX: FilteredList wraps the source list
FilteredList<Product> filtered = new FilteredList<>(sourceItems, p -> true);
searchField.textProperty().addListener((obs, o, n) ->
    filtered.setPredicate(p ->
        n.isBlank() || p.name().toLowerCase().contains(n.toLowerCase())));
table.setItems(filtered);
```

## Pattern 3 — Business Logic Inside CellValueFactory

```java
// WRONG: Domain rule buried in UI lambda
col.setCellValueFactory(data -> {
    BigDecimal price = data.getValue().price();
    BigDecimal tax = price.multiply(new BigDecimal("0.12"));
    BigDecimal total = price.add(tax);
    return new SimpleStringProperty(total.toPlainString());
});
```

```java
// FIX: Domain rule in Model; ViewModel exposes derived property
// Model: BigDecimal total() { return price().add(tax()); }
// ViewModel: ObjectProperty<BigDecimal> total = new SimpleObjectProperty<>(model.total());
col.setCellValueFactory(data -> data.getValue().totalProperty());
```

## Pattern 4 — Editable Column Without Commit Validation

```java
// WRONG: Accepts any text in a price column
col.setCellFactory(TextFieldTableCell.forTableColumn());
col.setOnEditCommit(e -> {
    e.getRowValue().setPrice(new BigDecimal(e.getNewValue()));
    // NumberFormatException on "abc"
});
```

```java
// FIX: Validate in commit handler
col.setOnEditCommit(e -> {
    try {
        BigDecimal price = new BigDecimal(e.getNewValue()).setScale(2, RoundingMode.HALF_UP);
        if (price.signum() > 0) {
            e.getRowValue().setPrice(price);
        }
    } catch (NumberFormatException ex) {
        // Revert — do nothing or show error
    }
});
```

## Pattern 5 — No Selection Model Handling

```java
// WRONG: delete acts on nothing if row not selected
deleteButton.setOnAction(e -> {
    Order selected = table.getItems().get(0); // always first item!
    repo.delete(selected.id());
});
```

```java
// FIX: Bind to selection model
deleteButton.disableProperty().bind(
    Bindings.isEmpty(table.getSelectionModel().getSelectedItems()));
deleteButton.setOnAction(e -> {
    Order selected = table.getSelectionModel().getSelectedItem();
    if (selected != null) repo.delete(selected.id());
});
```
