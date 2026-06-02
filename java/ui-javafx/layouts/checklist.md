# Checklist: Layouts in JavaFX

## Implementation
- [ ] Use `BorderPane` for application shell (header / center / footer).
- [ ] Use `GridPane` for two-column form layouts.
- [ ] Set `Priority.ALWAYS` hgrow/vgrow on the stretchable region.
- [ ] Apply `setHgap` / `setVgap` / `setPadding` — no zero-gap layouts.
- [ ] Use `ColumnConstraints` with percentage widths for split panes.

## Review
- [ ] No hardcoded `setPrefWidth` / `setMinWidth` / `setMaxWidth` on inner nodes.
- [ ] No `AnchorPane` used for responsive content.
- [ ] No layout deeper than 3 levels of nesting.
- [ ] Window can be resized and all content scales fluidly.
- [ ] `CONSTRAINED_RESIZE_POLICY` set on TableViews inside layouts.
