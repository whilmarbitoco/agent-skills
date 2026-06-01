# Skill: JavaFX Layouts

Layout system for JavaFX desktop applications.

## Core Concepts
- `BorderPane` — top/center/bottom/left/right regions (app shell)
- `GridPane` — row/column grid with `ColumnConstraints` (forms/dialogs)
- `VBox` / `HBox` — linear stacking with spacing and grow priorities
- `StackPane` — z-stacked overlays (loading spinners, badges)
- `ScrollPane` — wraps large content with scrollbars
- `AnchorPane` — avoid; absolute positioning breaks resizing

## Rules
1. `BorderPane` for top-level application shell only
2. `GridPane` for all forms and input dialogs
3. Never use `AnchorPane` — it breaks responsive resize behavior
4. Use `VBox.setVgrow(child, Priority.ALWAYS)` for flexible children
5. Use `Margin` via `GridPane.setMargin()` / `Insets` — not absolute `layoutX/Y`
6. Scrollable content → wrap in `ScrollPane`, not a raw `ListView`
7. `ColumnConstraints` for percentage-based form columns

## Anti-patterns
- `AnchorPane` with fixed anchors (breaks on resize)
- `layoutX/Y` absolute positioning
- Deeply nested `BorderPane` in `BorderPane` (use single root)
- Forgetting `fitToWidth`/`fitToHeight` on `GridPane`

## Relates to
- mvvm-javafx
- form-validation
- javafx-observable-state
