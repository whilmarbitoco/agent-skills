# Quick Reference: MVVM Layer Boundaries

| Layer | Knows About | Exports | Must NOT |
|-------|-------------|---------|----------|
| Model | Domain rules, repositories | Classes, records, Java-only API | Import any `javafx.*` package |
| ViewModel | Model (via constructor), JavaFX *Property | `*Property()`, validation flags | Instantiate services or UI controls |
| View | ViewModel (via constructor) | Scene/Stage wiring, FXML binding | Contain business logic or data access |
| DTO  | Cross-layer transport | Records / immutable values | Mutable shared state between VMs |
