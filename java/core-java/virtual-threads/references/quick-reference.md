# Virtual Threads — Quick Reference

| API | Purpose |
|---|---|
| `Thread.ofVirtual().start(r)` | Create + start virtual thread |
| `Thread.startVirtualThread(r)` | Shorthand for above |
| `Thread.isVirtual()` | Check if current thread is virtual |
| `Executors.newVirtualThreadPerTaskExecutor()` | Executor: one VT per task |
| `new StructuredTaskScope.ShutdownOnFailure()` | Fail-fast structured scope |
| `scope.fork(callable)` | Submit subtask |
| `scope.join()` | Await all subtasks |
| `ReentrantLock` | Non-pinning mutual exclusion |

| DO | DON'T |
|---|---|
| Use VT for I/O (HTTP, DB, files) | Use VT for CPU-bound work |
| Replace synchronized → ReentrantLock | Pool or cache VTs |
| StructuredTaskScope for fan-out | Fire-and-forget threads |
