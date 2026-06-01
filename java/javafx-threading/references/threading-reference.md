# Quick Reference: JavaFX Threading

| Question | Answer |
|----------|--------|
| How to run work in background? | Create `Task` or `Service`, start with `new Thread(task).start()` or `service.restart()` |
| How to update UI from result? | `task.setOnSucceeded()` or `Platform.runLater()` |
| How to cancel running work? | `task.cancel()` or `service.cancel()` — check `isCancelled()` in loops |
| How to show progress? | `task.updateProgress(work, total)` and `updateMessage("text")` |
| How to show errors? | `task.setOnFailed()` — always set this |
| Data binding alternative? | Bind UI directly to `ObjectProperty`/`ObservableList` — auto-updates on FX thread |

## Key APIs

```
Task<V>           — one-shot background work
Service<V>        — restartable background work
Worker<V>         — interface both implement
Platform.runLater(Runnable)  — schedule on FX thread
```

## JavaFX Thread Safety Rule

The FX Application Thread owns all `SceneGraph` state. Reading is safe from
any thread. Writing (modifying `ObservableList`, `Property`, `Node` state)
is only safe from the FX thread.
