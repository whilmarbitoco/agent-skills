# MVVM with JavaFX — Checklist

## Implementation
- [ ] ViewModel exposes properties (Simple*Property), not raw fields
- [ ] ViewModel has zero imports from `javafx.scene.*`
- [ ] Controller binds to ViewModel, never manipulates widgets directly
- [ ] Formatting logic (dates, currency) lives in ViewModel or converter, not FXML
- [ ] Background tasks use `Task<T>` — results applied on FX Application Thread
- [ ] ViewModel is testable with plain JUnit (no FX toolkit required)

## Review
- [ ] No static/singleton ViewModel instances
- [ ] No business logic in initialize() or FXML controller methods
- [ ] All observable mutations happen on FX Application Thread
- [ ] Injected dependencies are constructor-injected, set at construction time
