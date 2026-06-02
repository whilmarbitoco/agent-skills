# Anti-Patterns: MVVM in JavaFX

## Pattern 1 — Controller Reads Fields Manually

```java
// WRONG: Manual parsing in controller
saveButton.setOnAction(e -> {
    String name = nameField.getText();
    BigDecimal price = new BigDecimal(priceField.getText());
    Product p = new Product(name, price);
    repo.save(p);
});
```

```java
// FIX: ViewModel binds properties; commands handle actions
// In ViewModel:
public class ProductVM {
    private final StringProperty name = new SimpleStringProperty("");
    private final ObjectProperty<BigDecimal> price = new SimpleObjectProperty<>(BigDecimal.ZERO);
    Property<String> nameProperty() { return name; }
    Property<BigDecimal> priceProperty() { return price; }
    final BooleanProperty isValid = Bindings.createBooleanBinding(
        () -> !name.get().isBlank() && price.get().signum() > 0, name, price
    );
}
// In View: field.textProperty().bindBidirectional(vm.nameProperty());
//          saveButton.disableProperty().bind(vm.isValid.not());
```

## Pattern 2 — ViewModel Imports JavaFX Controls

```java
// WRONG: ViewModel knows about UI toolkit
import javafx.scene.control.TextField; // leak!
public class OrderVM {
    private final TextField statusField; // wrong layer
}
```

```java
// FIX: ViewModel owns properties only, zero JavaFX control imports
import javafx.beans.property.SimpleStringProperty;
public class OrderVM {
    private final StringProperty status = new SimpleStringProperty("");
    public StringProperty statusProperty() { return status; }
}
```

## Pattern 3 — Service Locator Instead of Constructor Injection

```java
// WRONG: Static access from ViewModel
public class CustomerVM {
    CustomerRepository repo = ServiceLocator.get(CustomerRepository.class);
}
```

```java
// FIX: Constructor injection — testable, explicit
public class CustomerVM {
    private final CustomerRepository repo;
    public CustomerVM(CustomerRepository repo) {
        this.repo = repo;
    }
}
```

## Pattern 4 — Business Logic in the View

```java
// WRONG: Controller contains pricing rules
if (price.doubleValue() > 1000) {
    discountField.setText("10%");
}
```

```java
// FIX: Pricing rule in Model, exposed via ViewModel property
// In Model:
public BigDecimal discountRate() {
    return price.compareTo(new BigDecimal("1000")) > 0
        ? new BigDecimal("0.10") : BigDecimal.ZERO;
}
// In ViewModel:
ObjectProperty<BigDecimal> discountRate = Bindings.createObjectBinding(
    () -> model.discountRate(), model.priceProperty()
);
```

## Pattern 5 — Mutable Shared Model Between ViewModels

```java
// WRONG: Two view-models mutating the same object
productVM.getSelected().setName(newName); // other VM has stale state
```

```java
// FIX: Immutable records crossing boundaries
record ProductDTO(String id, String name, BigDecimal price) {}
// Each ViewModel works on its own copy; Model merges on save.
```
