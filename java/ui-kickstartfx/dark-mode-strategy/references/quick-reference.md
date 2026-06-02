# Dark Mode — Quick Reference

| Concept | Convention |
|---|---|
| CSS layers | `brand.css` (always) + `palette-{light,dark}.css` (swapped) |
| Swap method | `scene.getStylesheets().setAll(List.of("brand.css", palette))` |
| Persistence | `Preferences.userNodeForPackage(App.class).put("ui.theme", …)` |
| Load timing | Read in `init()`, apply before `stage.show()` |
| Palette content | Color tokens only — no layout/spacing rules |
