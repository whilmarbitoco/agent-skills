---
name: form-validation
description: >
  Validate user input in JavaFX forms with real-time feedback using
  TextFormatter, validators, and error binding. Use when building any
  form that requires typed, constrained, or business-rule validation.
compatibility: Java 21+
metadata:
  domain: ui-javafx
  level: intermediate
  stack: [java-21, javafx-21]
  version: "1.0.0"
---

# Form Validation in JavaFX

Validate on every keystroke, not just on submit. Use `TextFormatter`
with a custom `Filter` to reject invalid keystroke-level input, and
`StringConverter` + validator to produce typed errors.

## Core rules
- Attach a `TextFormatter` to every `TextField` / `TextArea`.
- Use `TextFormatter.Filter` to reject invalid characters at keystroke
  level (e.g., allow digits only for quantity fields).
- Push validation errors into an `ObservableList<ValidationError>` on the
  ViewModel; bind a `VBox` error panel to it.
- Disable the submit button via `validProperty()` — never check `getText()
  .isBlank()` in the controller.
- All error messages belong to the ViewModel (testable without UI).

## Anti-patterns
- Validating only on submit button click.
- Throwing `RuntimeException` from `StringConverter.fromString()`.
- Hardcoding error message strings inside the View (`new Label("Required")`).
- Allowing "1.2.3" in a decimal field because `new BigDecimal(text)`
  throws at submit time.

## Related
observable-state • mvvm • css-theming • reusable-components
