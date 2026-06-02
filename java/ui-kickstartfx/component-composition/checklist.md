# Component Composition — Checklist

## Implementation

- [ ] Each custom control has triple: `.java` + `.fxml` + `.css`
- [ ] Controls expose `ObjectProperty` / `StringProperty` for external binding
- [ ] Pseudo-classes used for state changes (not `getStyleClass().add/remove`)
- [ ] FXML loaded inside the control's constructor via FXMLLoader
- [ ] Shared state held in injected models, not static fields

## Review

- [ ] No external code reaches into component internals (no `getChildren().get(0)`)
- [ ] Component renders correctly without its parent scene
- [ ] No controller holds cross-view state in static fields
- [ ] FXML files live in same package path as their controller class
