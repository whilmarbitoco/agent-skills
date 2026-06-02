# Virtual Threads — Anti-Patterns

## 1. Using synchronized blocks inside virtual threads (Pinning)

```java
// WRONG — synchronized pin the carrier thread, eliminating the benefit of virtual threads
public class PinnedCounter {
    private int count = 0;

    public synchronized void increment() {
        // If a blocking call follows, the carrier thread is pinned
        Network.call(); // pins for the duration
    }
}
```

```java
// FIX: use ReentrantLock — does NOT pin the carrier thread
import java.util.concurrent.locks.ReentrantLock;

public class UnpinnedCounter {
    private final ReentrantLock lock = new ReentrantLock();
    private int count = 0;

    public void increment() {
        lock.lock();
        try {
            Network.call();
        } finally {
            lock.unlock();
        }
    }
}
```

## 2. Pooling virtual threads

```java
// WRONG — virtual threads are cheap to create and NOT meant to be pooled
private static final ExecutorService pool = Executors.newFixedThreadPool(10);

void handle(Request req) {
    pool.submit(() -> process(req));
}
```

```java
// FIX: one virtual-thread-per-task executor, close with try-with-resources
void handle(Request req) {
    try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
        executor.submit(() -> process(req));
    }
}
```

## 3. Using virtual threads for CPU-bound work

```java
// WRONG — virtual threads add overhead for CPU-bound computation
void sortLargeArray(int[] data) {
    Thread.startVirtualThread(() -> Arrays.sort(data)).join();
}
```

```java
// FIX: use common ForkJoinPool for CPU-bound work
void sortLargeArray(int[] data) {
    Arrays.parallelSort(data); // uses ForkJoinPool.commonPool()
}
```

## 4. Ignoring StructuredTaskScope (fire-and-forget)

```java
// WRONG — fire-and-forget hides failures and leaks threads
void fetchAll() {
    Thread.startVirtualThread(() -> fetchUsers());
    Thread.startVirtualThread(() -> fetchOrders());
    // no join, no error propagation
}
```

```java
// FIX: StructuredTaskScope joins and propagates errors
import java.util.concurrent.StructuredTaskScope;

record Result(List<User> users, List<Order> orders) {}

Result fetchAll() throws Exception {
    try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
        var users  = scope.fork(() -> fetchUsers());
        var orders = scope.fork(() -> fetchOrders());
        scope.join().throwIfFailed();
        return new Result(users.get(), orders.get());
    }
}
```

## 5. Blocking the carrier in a synchronized I/O path

See pattern #1. Rule: any `synchronized` that wraps I/O = pinning. Replace with `ReentrantLock` or move I/O outside the lock.
