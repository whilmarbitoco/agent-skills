# Checklist: Form Validation in JavaFX

## Implementation
- [ ] Every editable input has a `TextFormatter`.
- [ ] `UnaryOperator<Change>` filter rejects structurally invalid input.
- [ ] `StringConverter.fromString` returns `null` (never throws) on bad input.
- [ ] ViewModel exposes `ObservableList<ValidationError>` for error panel binding.
- [ ] Submit button disabled when `!isValid.get()`.

## Review
- [ ] No error message strings hardcoded in View classes.
- [ ] No duplicate validation between ViewModel and Model.
- [ ] Invalid input is rejected at keystroke level (not deferred to submit).
- [ ] Currency / decimal fields use `BigDecimal` — never `Double.parseDouble()`.
- [ ] Validation rules are unit-testable without launching JavaFX.
