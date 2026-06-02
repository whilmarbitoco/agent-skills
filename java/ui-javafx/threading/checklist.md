# Checklist: Threading in JavaFX

## Implementation
- [ ] Identify every I/O or CPU-bound operation; move it off the FX thread.
- [ ] Wrap one-shot work in `Task<V>`, bind its `valueProperty()` / `messageProperty()` to UI.
- [ ] Wrap repeatable work in `Service<V>`; call `restart()` never raw `new Thread()`.
- [ ] Use `Thread.ofVirtual().start(task)` for I/O-bound tasks (Java 21).
- [ ] Use `synchronized` / `AtomicReference` for any shared mutable state.

## Review
- [ ] No `Thread.sleep`, file read, HTTP call, or DB query on the FX thread.
- [ ] No `Platform.runLater` when a property binding suffices.
- [ ] Every `InterruptedException` restores the interrupt flag.
- [ ] `ObservableList` / `ObservableValue` only mutated on FX thread.
- [ ] Cancelled tasks check `isCancelled()` inside their loop.
