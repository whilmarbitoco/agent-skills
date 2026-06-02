# Anti-Patterns: Form Validation in JavaFX

## Pattern 1 — Only Validating on Submit

```java
// WRONG: Errors only appear after button click
saveButton.setOnAction(e -> {
    if (nameField.getText().isBlank()) {
        errorLabel.setText("Name is required");
    }
});
```

```java
// FIX: Real-time validation via TextFormatter + error binding
StringProperty name = new SimpleStringProperty("");
var errors = new SimpleListProperty<ValidationError>(FXCollections.observableArrayList());
name.addListener((obs, o, n) ->
    errors.setAll(validateName(n)));
errorList.setItems(errors);
```

## Pattern 2 — Throwing from StringConverter

```java
// WRONG: Bad parse crashes silently or shows stack trace
new StringConverter<BigDecimal>() {
    public BigDecimal fromString(String s) {
        return new BigDecimal(s); // NumberFormatException on "abc"
    }
    public String toString(BigDecimal v) { return v.toPlainString(); }
}
```

```java
// FIX: Return a safe default and let validator handle the error
new StringConverter<BigDecimal>() {
    public BigDecimal fromString(String s) {
        try { return new BigDecimal(s).setScale(2, RoundingMode.HALF_UP); }
        catch (NumberFormatException e) { return null; }
    }
    public String toString(BigDecimal v) {
        return v == null ? "" : v.toPlainString();
    }
}
// Then validate: if value is null → "Must be a valid number"
```

## Pattern 3 — Hardcoded Error Labels in View

```java
// WRONG: Error text embedded in View
Label error = new Label("Price must be greater than zero");
error.setStyle("-fx-text-fill: red;");
```

```java
// FIX: Error messages come from ViewModel
record ValidationError(String field, String message, Severity severity) {
    enum Severity { ERROR, WARNING }
}
// ViewModel produces errors; View renders them from ObservableList.
```

## Pattern 4 — Accepting Junk in Decimal Fields

```java
// WRONG: No TextFormatter — user types "1.2.3.4"
TextField priceField = new TextField();
// Only fails later when parsed.
```

```java
// FX: Filter rejects multiple decimal points
UnaryOperator<TextFormatter.Change> decimalFilter = change -> {
    String newText = change.getControlNewText();
    if (newText.matches("\\d*\\.?\\d*")) return change;
    return change; // no — return null to reject
    // Correction: return null to reject
};
priceField.setTextFormatter(
    new TextFormatter<>(decimalFilter)
);
```

```java
// FIX (corrected): Return null to reject
UnaryOperator<TextFormatter.Change> decimalFilter = change -> {
    String newText = change.getControlNewText();
    if (newText.matches("\\d*\\.?\\d*")) return change;
    return null; // reject the change
};
```

## Pattern 5 — Duplicate Validation in Controller and Model

```java
// WRONG: Same rule coded twice
// Controller: if (price >= 0) ...
// Model: if (price < 0) throw ...
```

```java
// FIX: Single validation method in Model/ViewModel
// Model.signalError() or ViewModel.validate() returns List<ValidationError>
// Controller only triggers display — never duplicates logic.
```
