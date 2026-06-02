# Responsive Layouts — Quick Reference

| Concept | Convention |
|---|---|
| Breakpoints | Compact (<1024), Medium (<1440), Expanded (≥1440) |
| Sizing | `prefWidthProperty().bind(scene.widthProperty().multiply(0.3))` |
| Grids | `FlowPane` / `TilePane` for auto-wrapping cards |
| Sidebar mode | Docked (medium+), Overlay (compact) |
| Min sizes | `stage.setMinWidth(960)`, `setMinHeight(600)` |
