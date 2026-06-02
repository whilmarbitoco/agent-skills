---
name: mvvm
description: >
  Separate JavaFX UI into Model, View, and ViewModel layers using
  constructor injection and observable bindings. Use when building any
  non-trivial screen that must be testable and maintainable.
compatibility: Java 21+
metadata:
  domain: ui-javafx
  level: intermediate
  stack: [java-21, javafx-21]
  version: "1.0.0"
---

# MVVM in JavaFX

The ViewModel owns observable properties; the View binds its nodes to
them. The Model holds domain logic and knows nothing about JavaFX.

## Core rules
- ViewModel takes Model via constructor injection — never `new Model()`
  inside a ViewModel.
- View (FXML controller or programmatic pane) takes ViewModel via
  constructor injection.
- ViewModel exposes *Property objects (not plain values) for binding.
- Keep JavaFX imports out of the Model layer entirely.
- Use records for immutable DTOs / value objects crossing layer boundaries.

## Anti-patterns
- Controller class that parses text fields manually instead of binding.
- ViewModel that imports `javafx.scene.control.*`.
- Service locator / static singleton access from ViewModel.
- Business logic living in the View layer.

## Related
observable-state • form-validation • layouts • fxml-patterns
