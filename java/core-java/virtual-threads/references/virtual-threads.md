# Virtual Threads — Full Reference

## JEP Index

| JEP | Title | Status |
|-----|-------|--------|
| 444 | Virtual Threads | Final (Java 21) |
| 453 | Structured Concurrency | Final (Java 21) |
| 446 | Scoped Values | Preview (Java 21) → Final (Java 22) |

## Creation APIs

```java
// Single task
Thread.startVirtualThread(() -> doWork());

// Factory for executors
var executor = Executors.newVirtualThreadPerTaskExecutor();

// Per-task with custom builder
Thread.ofVirtual().name("worker-", 0).start(() -> doWork());

// Structured concurrency
try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
    scope.fork(() -> task1());
    scope.fork(() -> task2());
    scope.join();
    scope.throwIfFailed();
}
```

## Pinning and Mounting

A virtual thread "pins" to its carrier (platform thread) when:
- Entering a `synchronized` block or method
- Calling native code (`JNI`)
- Executing `ForeignFunction` (FFM API in preview)

Detection: `-Djdk.tracePinnedThreads=short` prints stack traces of pinned threads.

Replacement pattern:

```java
// Before — pinned
synchronized (lock) { work(); }

// After — unpinned
ReentrantLock rtLock = new ReentrantLock();
rtLock.lock();
try { work(); } finally { rtLock.unlock(); }
```

## Migrating from Platform Thread Executors

| Old Pattern | Replacement |
|-------------|-------------|
| `Executors.newCachedThreadPool()` | `Executors.newVirtualThreadPerTaskExecutor()` |
| `Executors.newFixedThreadPool(n)` | Same as above (remove pool size) |
| `new ThreadPoolExecutor(...)` | Same as above |

## Scoped Values (Java 21 Preview, Java 22 Final)

ThreadLocal replacement designed for virtual threads:

```java
private static final ScopedValue<User> CURRENT_USER = ScopedValue.newInstance();

ScopedValue.where(CURRENT_USER, user).run(() -> {
    // Available to all child virtual threads
    service.process();
});
```

## Common Pitfalls

1. **Thread pooling** — never pool VTs
2. **ThreadLocal at scale** — each VT gets its own copy; expensive at millions
3. **Synchronized blocks** — causes pinning; use ReentrantLock
4. **CPU-bound tasks** — no benefit from VTs
