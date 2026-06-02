# Virtual Threads — Checklist

## Implementation

- [ ] Use `Executors.newVirtualThreadPerTaskExecutor()` instead of fixed thread pools for I/O
- [ ] Replace `synchronized` blocks wrapping I/O with `ReentrantLock`
- [ ] Use `StructuredTaskScope` for structured fan-out/fan-in
- [ ] Keep virtual threads short-lived; no pooling or caching
- [ ] Use common ForkJoinPool / `parallelStream()` for CPU-bound work

## Review

- [ ] No `synchronized` on any path that involves I/O or `Thread.sleep`
- [ ] StructuredTaskScope used wherever multiple concurrent subtasks are spawned
- [ ] `try-with-resources` on all executor services
- [ ] No `newFixedThreadPool` or `newCachedThreadPool` calls in new code
