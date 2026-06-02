# Quick Reference: JavaFX Threading APIs

| API | Purpose | Key Methods |
|-----|---------|-------------|
| `Task<V>` | One-shot background work | `call()`, `updateProgress()`, `updateMessage()`, `setOnSucceeded()` |
| `Service<V>` | Restartable background worker | `createTask()`, `start()`, `restart()`, `cancel()` |
| `Platform` | Marshal to FX thread | `runLater(Runnable)` |
| `Thread.ofVirtual()` | Java 21 virtual thread | `start(Runnable)`, `unstarted(Runnable)` |
| `AtomicReference<V>` | Lock-free shared state | `get()`, `set()`, `compareAndSet()` |
| `synchronized` | Intrinsic lock | `synchronized(obj) { … }` |
