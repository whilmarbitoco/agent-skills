---
name: virtual-threads
description: >
  Extends agent's knowledge of Java 21 virtual threads (JEP 444), structured
  concurrency (JEP 453), and JavaFX async patterns. Use when writing concurrent
  code, offloading I/O from the FX Application Thread, or debugging thread pinning.
compatibility: Java 21+ required
metadata:
  domain: core-java
  level: advanced
  stack: [java-21, openjdk]
  requires: [concurrency-fundamentals]
  related: [async-ui-patterns, threading-and-platform-runlater]
  version: "1.1.0"
---

# Virtual Threads & Structured Concurrency

Java 21 virtual threads are JVM-managed lightweight threads that enable
high-throughput I/O-bound concurrency without platform thread overhead.
This skill covers creation, lifecycle, structured concurrency, and integration
with JavaFX.

## When This Skill Applies

- Writing `CompletableFuture` or async service methods that do I/O
- Offloading database queries, HTTP calls, or file operations from the FX thread
- Parallelizing independent queries (dashboard loading, report generation)
- Diagnosing thread pinning or virtual thread performance issues

## Core Rules

1. **Use virtual threads exclusively for I/O-bound work.** CPU-bound tasks gain nothing.
2. **Create per-task — never pool.** Use `Executors.newVirtualThreadPerTaskExecutor()`, never `newFixedThreadPool()`.
3. **Use `ReentrantLock`, not `synchronized`.** `synchronized` pins virtual threads to their carrier.
4. **Use `StructuredTaskScope`** for parallel tasks with parent-child relationships.
5. **Never block the FX Application Thread.** All I/O goes through virtual threads; results arrive via `Platform.runLater` or `thenAcceptAsync`.
6. **Monitor pinning** with `-Djdk.tracePinnedThreads=short` in dev.

## Examples

### Creating a virtual-thread executor

```java
// ✅ CORRECT — per-task executor, never pooled
private final ExecutorService ioExecutor =
    Executors.newVirtualThreadPerTaskExecutor();

public CompletableFuture<List<Product>> loadProductsAsync() {
    return CompletableFuture.supplyAsync(
        () -> productRepository.findAll(),
        ioExecutor
    );
}
```

```java
// ❌ WRONG — pooling defeats virtual threads
ExecutorService pool = Executors.newFixedThreadPool(100);
```

**Why:** Virtual threads cost ~1KB vs ~1MB for platform threads. Pooling adds contention for no benefit.

### Structured concurrency for parallel queries

```java
// ✅ CORRECT — parent-child lifecycle, clean cancellation
public DashboardData loadDashboard() throws Exception {
    try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
        var products = scope.fork(() -> productRepository.findLowStock());
        var sales    = scope.fork(() -> saleRepository.findToday());
        var revenue  = scope.fork(() -> saleRepository.todayRevenue());

        scope.join();
        scope.throwIfFailed();

        return new DashboardData(
            products.resultNow(),
            sales.resultNow(),
            revenue.resultNow()
        );
    }
}
```

### Integration with JavaFX

```java
// Service layer — runs on virtual thread
public CompletableFuture<List<Product>> searchAsync(String query) {
    return CompletableFuture.supplyAsync(() ->
        DB.find(Product.class)
          .where().ilike("name", "%" + query + "%")
          .findList(),
        ioExecutor
    );
}

// Controller — never block FX thread
@FXML
private void onSearch() {
    inventoryService.searchAsync(searchField.getText())
        .thenAcceptAsync(products ->
            productTable.setItems(FXCollections.observableArrayList(products)),
            Platform::runLater
        )
        .exceptionally(ex -> {
            Platform.runLater(() -> showError(ex.getMessage()));
            return null;
        });
}
```

### Locking — avoiding pinning

```java
// ❌ WRONG — synchronized pins the virtual thread
public synchronized List<Product> findAll() {
    return repository.findAll();
}

// ✅ CORRECT — ReentrantLock doesn't pin
private final ReentrantLock lock = new ReentrantLock();

public List<Product> findAll() {
    lock.lock();
    try {
        return repository.findAll();
    } finally {
        lock.unlock();
    }
}
```

## Conventions

| Aspect | Convention |
|--------|-----------|
| Executor naming | `taskExecutor` or `ioExecutor` — never `threadPool` |
| Structured scope | Always `try-with-resources` |
| Cancellation | `scope.shutdown()` — never `Thread.interrupt()` |

## Anti-Patterns

- **Pooling virtual threads** — defeats the JVM's lightweight scheduling
- **`synchronized` in VT paths** — causes carrier thread pinning
- **VTs for CPU-bound work** — no benefit; use `ForkJoinPool` instead
- **`Thread.sleep()` in structured scope** — blocks the carrier

## Verification

### Implementation
- [ ] All I/O offloading uses `newVirtualThreadPerTaskExecutor()`
- [ ] `ReentrantLock` used instead of `synchronized` in VT paths
- [ ] `StructuredTaskScope` used for parallel task groups
- [ ] No virtual thread pools

### Code Review
- [ ] No `synchronized` in methods called from virtual threads
- [ ] No `ThreadLocal` abuse in VT code
- [ ] Exception handling preserves structured scope cancellation

## Reference Material

- `references/virtual-threads.md` — full JEP 444/453 reference, pinning deep-dive
- `scripts/detect-pinning.sh` — JVM flag helper for dev environments

## Recommended Reading

- [JEP 444: Virtual Threads](https://openjdk.org/jeps/444)
- [JEP 453: Structured Concurrency](https://openjdk.org/jeps/453)
- [Oracle Virtual Threads Guide](https://docs.oracle.com/en/java/javase/21/core/virtual-threads.html)
