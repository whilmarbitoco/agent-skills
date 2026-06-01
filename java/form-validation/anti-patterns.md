# Form Validation Anti-Patterns

## No validation on submit — silently accepts garbage

```java
// WRONG — any text accepted, including negative prices
saveButton.setOnAction(e -> {
    String name = nameField.getText();
    double price = Double.parseDouble(priceField.getText());
    repository.save(new Product(name, price)); // blows up on empty/negative
});
```

**Validate first: if (name.isBlank() || price <= 0) { showError(); return; }**

## Error message nowhere near the field

```java
// WRONG — error at top of form, user doesn't know which field failed
errorLabel.setText("Price must be positive");
```

**Show error next to the field: `errorLabel.setFor(priceField);` or use an error Label below the control.**

## Only validating on submit — user fills entire form before seeing errors

```java
// WRONG — all validation happens at once on save
private void onSubmit() {
    validateName(); validatePrice(); validateStock(); // everything fails at once
}
```

**Also validate on focus lost: `priceField.focusedProperty().addListener((obs, was, is) -> { if (!is) validatePrice(); });`**

## Accepting whitespace as valid input

```java
// WRONG — "   " is accepted as a product name
if (name.isEmpty()) error("Name required");
```

**Use `name.isBlank()` not `name.isEmpty()`.**

## No numeric bounds checking

```java
// WRONG — allows negative stock or 0 price
if (price > 0) return;
```

**Use domain rules: price must be `BigDecimal.ONE` or above, stock must be >= 0, name must be 1-200 chars.**
