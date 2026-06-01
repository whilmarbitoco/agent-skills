# Virtual Thread Quick Reference

| Question | Answer |
|----------|--------|
| Create VT executor | `Executors.newVirtualThreadPerTaskExecutor()` |
| Run single VT | `Thread.startVirtualThread(runnable)` |
| Parallel tasks | `StructuredTaskScope.ShutdownOnFailure` |
| Compose async | `CompletableFuture.supplyAsync(fn, vtExecutor)` |
| Avoid pinning | Use `ReentrantLock`, not `synchronized` |
| Detect pinning | `-Djdk.tracePinnedThreads=short` |
| CPU-bound work | Use `ForkJoinPool`, not VTs |

## Key APIs

```
Executors.newVirtualThreadPerTaskExecutor()  — per-task VT factory
Thread.ofVirtual().start(runnable)           — single VT
StructuredTaskScope<V>                       — parent-child lifecycle
StructuredTaskScope.ShutdownOnFailure        — fail-fast parallel
StructuredTaskScope.ShutdownOnSuccess        — first-wins parallel
```

## Pinning Causes

- `synchronized` blocks/methods → use `ReentrantLock`
- JNI native calls → unavoidable, minimize
- FFM API (preview) → unavoidable
