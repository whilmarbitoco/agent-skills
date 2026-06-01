# Skill: MVVM for JavaFX

Model-View-ViewModel pattern for JavaFX desktop apps.

## Core Concepts
- `ViewModel` — holds `ObservableList`, `Property`, commands; no View reference
- `View` (FXML) — declarative UI, binds to ViewModel properties via `fx:id` and `<fx:reference>`
- `Controller` — thin glue: instantiates ViewModel, binds it to FXML elements
- Data binding — `label.textProperty().bind(viewModel.titleProperty())`
- Commands — `Runnable` or `Consumer<T>` lambdas exposed as buttons' `onAction`

## Rules
1. ViewModel must have zero references to View types (no `ListView`, `Label` imports)
2. View binds to ViewModel properties — no `controller.getXxx()` calls
3. All business logic lives in ViewModel (or injected services), never in Controller
4. `ObservableList` exposed as `ReadOnlyList` or unmodifiable wrapper
5. FXML `fx:controller` points to thin controller; controller delegates to ViewModel
6. ViewModel implements `Initializable` or uses `@FXML` only for binding hooks

## Anti-patterns
- Controller with business logic (God Controller)
- ViewModel importing `javafx.scene.control.*`
- Direct `tableView.setItems(...)` from controller (should be bound)
- State asymmetry — ViewModel out of sync with View

## Relates to
- javafx-observable-state
- javafx-layouts
- form-validation
