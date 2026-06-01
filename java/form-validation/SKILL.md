# Skill: JavaFX Form Validation

Input validation for JavaFX forms.

## Core Concepts
- `TextInputControl` — `TextField`, `TextArea`, `TextInputControl` base
- CSS `:invalid` pseudo-class — visual feedback on invalid fields
- Validation chain — validate each field, collect errors, block submit
- `Label` error nodes — placed near fields, visible only on error
- `disableProperty().bind(valid.not())` — disable submit until all valid

## Rules
1. Validate on focus lost (`focusedProperty` listener) AND on submit attempt
2. Show error message next to the field (not just in an alert)
3. Disable submit button until all fields are valid via binding
4. Use CSS `:invalid` pseudo-class for red-border visual feedback
5. Never block the UI thread — instant validation only (no I/O)
6. Clear errors when user starts typing again

## Anti-patterns
- Alert dialogs for every field error (noisy, blocks flow)
- Validating only on submit (late feedback, frustrating)
- Using `TextFormatter` with no visual feedback
- Forgetting to re-validate after programmatic `setText()`

## Relates to
- javafx-observable-state
- mvvm-javafx
- javafx-layouts
