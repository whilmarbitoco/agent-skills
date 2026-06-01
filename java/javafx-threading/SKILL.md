# Skill: JavaFX Threading

Teach safe background processing in JavaFX applications.

## Core Concepts
- FX Application Thread — single thread owns all UI state
- `Task<V>` — one-shot background operation with progress
- `Service<V>` — restartable background operation
- `Platform.runLater()` — schedule UI update from any thread
- `Bindings`/`Properties` — reactive UI without manual updates

## Rules
1. Never block FX thread with I/O, DB, or computation
2. Use `Task` for one-shot work, `Service` for restartable work
3. UI updates only via `Platform.runLater`, `Task.onSucceeded`, or bindings
4. All `Task`/`Service` work runs on its own thread — safe to do I/O
5. Always handle failure — don't let exceptions die silently

## Anti-patterns
- Running DB queries in button handlers
- `Thread.sleep()` on FX thread
- Updating `ObservableList` from background thread
- Using `CompletableFuture` without `Platform.runLater` for UI updates

## Relates to
- virtual-thread-service
- ebean-setup
- javafx-layouts
