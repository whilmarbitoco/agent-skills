---
name: component-composition
description: >
  Extends agent's knowledge of composing reusable JavaFX controls from smaller
  pieces. Use when building custom panes, creating composite form controls, or
  designing a widget library for a POS interface.
compatibility: Java 21+
metadata:
  domain: ui-kickstartfx
  level: intermediate
  stack: [java-21, javafx-21]
  version: "1.0.0"
---

# Component Composition

Build complex UI from small, focused components. A component is a `Region`
subclass with its own FXML, controller, and CSS file — self-contained and
reusable across screens.

## Concepts

- **Custom control trio**: `MyCard.java` (Region subclass) + `MyCard.fxml` + `MyCard.css`
- **fx:include**: embed a custom control inside another FXML with `<fx:include source="...">`
- **Custom properties**: register `ObjectProperty<T>` or `StringProperty` on the control skin
- **Pseudo-class states**: `PseudoClass.getPseudoClass("error")` — toggles CSS rules without code

## Rules

1. Each custom control gets its own package: `component.card`, `component.search-bar`.
2. Expose bindable JavaFX properties for all inputs/outputs — no direct child node access.
3. Load the control's own FXML inside `MyControl()` constructor via `FXMLLoader`.
4. Use pseudo-classes (not removed/added style classes) for state changes like `:error`, `:readonly`.
5. Keep stateless logic in the controller; state that persists across re-creates goes on the model.

## Anti-patterns

See [anti-patterns.md](./anti-patterns.md).

## Related

- navigation-patterns — compose routes from reusable components
- sidebar-shell-architecture — shell hosts composed component panes
