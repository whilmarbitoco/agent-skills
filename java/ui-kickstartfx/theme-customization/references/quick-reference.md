# Theme Customization — Quick Reference

| Concept | Convention |
|---|---|
| Color tokens | Looked-up colors: `-fx-brand-primary`, `-fx-surface` |
| Spacing tokens | CSS variables: `--spacing-xs` through `--spacing-lg` |
| Theme layers | `brand.css` (structure) + `palette-{mode}.css` (colors) |
| Apply theme | `scene.getStylesheets().setAll(...)` — on Scene, not nodes |
| State changes | Pseudo-classes, never `setStyle()` in code |
