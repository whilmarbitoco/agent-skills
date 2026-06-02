# Quick Reference: JavaFX Layout Panes

| Pane | Use Case | Key Constraints |
|------|----------|-----------------|
| `BorderPane` | Header-body-footer shell | `setTop/Left/Center/Right/Bottom()` |
| `GridPane` | Two-column forms | `ColumnConstraints`, `RowConstraints`, `Hgrow/Vgrow` |
| `HBox` | Horizontal toolbar / row | `setSpacing()`, `setAlignment()` |
| `VBox` | Vertical stack / card | `setSpacing()`, `Vgrow(Priority)` |
| `TilePane` | Equal-width card grid | `setPrefColumns()`, `setHgap()` |
| `FlowPane` | Wrapping content | `setOrientation()`, `setColumnHalignment()` |
| `Priority` | Grow policy | `ALWAYS`, `SOMETIMES`, `NEVER` |
| `ColumnConstraints` | Column sizing | `setPercentWidth()`, `setHgrow()`, `setFillWidth()` |
