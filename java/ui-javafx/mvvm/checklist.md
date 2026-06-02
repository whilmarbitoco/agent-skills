# Checklist: MVVM in JavaFX

## Implementation
- [ ] Model layer has zero JavaFX imports.
- [ ] ViewModel receives Model via constructor injection.
- [ ] ViewModel exposes `*Property` objects, never raw fields.
- [ ] View (controller) binds nodes to ViewModel properties — no manual reads.
- [ ] Commands / actions exposed as `Runnable` or `BooleanProperty` on ViewModel.

## Review
- [ ] No `field.getText()` / `field.setText()` in controller beyond initial bind setup.
- [ ] No `ServiceLocator`, `static getInstance()`, or static mutable state.
- [ ] Business logic testable without launching a JavaFX `Application`.
- [ ] All cross-layer DTOs are records or immutable.
- [ ] ViewModel has ≤ 1 responsibility (split if it owns unrelated properties).
