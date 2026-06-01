# MVVM JavaFX Anti-Patterns

## Business logic in controller/FXML handler

```java
// WRONG — controller does calculations
@FXML
private void onCalculateTotal() {
    BigDecimal total = items.stream()
        .map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getQty())))
        .reduce(BigDecimal.ZERO, BigDecimal::add);
    totalLabel.setText(total.toString());
}
```

**Move to ViewModel: `totalLabel.textProperty().bind(viewModel.totalProperty())`. Controller only handles UI events.**

## ViewModel holding reference to View

```java
// WRONG — ViewModel depends on UI, can't test without JavaFX
public class InventoryViewModel {
    private TableView<ProductView> table; // WRONG
}
```

**ViewModel exposes `ObservableList<ProductView>`, View binds to it. ViewModel has zero UI references.**

## Manual event handlers instead of commands

```java
// WRONG — manual wiring
saveButton.setOnAction(e -> viewModel.save());
cancelButton.setOnAction(e -> viewModel.cancel());
```

**Use command binding: `saveButton.disableProperty().bind(viewModel.saveCommand.disabledProperty())`.**

## Model and ViewModel are the same class

```java
// WRONG — Ebean entity used directly in TableView
ObservableList<Product> products = FXCollections.observableArrayList();
```

**Create a view record: `ProductView.fromEntity(entity)`. Never expose Ebean entities to UI.**

## Putting converter logic in the view

```java
// WRONG — view does data formatting
priceLabel.setText("PHP " + product.getPrice().getAmount());
```

**Use formatting in ViewModel: `Bindings.format("PHP %,.2f", priceProperty)` or a StringConverter on the cell.**
