---
name: virtual-threads
description: "Use when working with Java 21 virtual threads, structured concurrency, or async I/O in desktop apps. Covers Thread.startVirtualThread, Executors.newVirtualThreadPerTaskExecutor, StructuredTaskScope, and pitfalls."
category: java
tags:
  - java-21
  - core-java
  - virtual-threads
  - concurrency
---

# Virtual Threads & Structured Concurrency

**Skill ID:** `virtual-threads`  
**Domain:** `core-java` → `concurrency`  
**Level:** advanced  
**Version:** 1.0.0  
**Last Updated:** 2026-06-01

**Stack:** `java-21, maven`  
**Requires:** `concurrency-fundamentals`  
**Related:** `async-ui-patterns, concurrency-fundamentals`

**Version Compatibility:** Java `>= 21`

---

## Purpose

Virtual threads (JEP 444) are lightweight threads managed by the JVM, not the OS. They enable high-throughput concurrency for I/O-bound workloads — database queries, HTTP calls, file I/O — without the overhead of platform threads. This skill covers creation, lifecycle, structured concurrency, and integration with JavaFX UI.

---

## Concepts Covered

- **Virtual Threads** — JVM-managed lightweight threads, millions feasible
- **Thread.startVirtualThread()** — simplest creation API
- **Executors.newVirtualThreadPerTaskExecutor()** — factory for per-task virtual threads
- **StructuredTaskScope** — parent-child thread lifecycle (JEP 453)
- **Pinning** — when virtual threads degrade to platform threads (synchronized, native)
- **ThreadLocal caution** — virtual threads make ThreadLocal expensive at scale

---

## Rules / Best Practices

1. **Use virtual threads for I/O-bound work only** — CPU-bound tasks don't benefit
2. **Never pool virtual threads** — they're cheap to create; pooling defeats the purpose
3. **Prefer `Executors.newVirtualThreadPerTaskExecutor()`** over raw `startVirtualThread`
4. **Use `StructuredTaskScope`** for tasks with a parent-child relationship
5. **Never use `synchronized` blocks in hot virtual thread paths** — use `ReentrantLock` instead
6. **Pin detection** — monitor with `-Djdk.tracePinnedThreads=short`

---

## Code Conventions

| Convention | Rule |
|------------|------|
| Executor naming | `taskExecutor` or `ioExecutor` — never `threadPool` |
| Structured scope | Always use `try-with-resources` |
| Cancellation | `shutdown()` on scope, never `Thread.interrupt()` |
| Naming | Virtual thread executors → `*TaskExecutor` |

```java
// ✅ CORRECT — Virtual thread executor for I/O
private final ExecutorService ioExecutor = 
    Executors.newVirtualThreadPerTaskExecutor();

public CompletableFuture<List<Product>> loadProductsAsync() {
    return CompletableFuture.supplyAsync(
        () -> productRepository.findAll(),
        ioExecutor
    );
}

// ✅ CORRECT — Structured concurrency for parallel queries
public DashboardData loadDashboardData() throws Exception {
    try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
        Future<List<Product>> products = scope.fork(() -> 
            productRepository.findLowStock());
        Future<List<Sale>> sales = scope.fork(() -> 
            saleRepository.findToday());
        Future<Money> revenue = scope.fork(() -> 
            saleRepository.todayRevenue());
        
        scope.join();           // Wait for all
        scope.throwIfFailed();  // Propagate first failure
        
        return new DashboardData(
            products.resultNow(),
            sales.resultNow(),
            revenue.resultNow()
        );
    }
}

// ❌ WRONG — Pooling virtual threads (anti-pattern)
ExecutorService pool = Executors.newFixedThreadPool(100); // Don't pool VTs

// ❌ WRONG — synchronized in virtual thread path
synchronized (this) {  // Causes pinning — degrade to platform thread
    repository.findAll();
}
// ✅ FIX: Use ReentrantLock
ReentrantLock lock = new ReentrantLock();
lock.lock();
try {
    repository.findAll();
} finally {
    lock.unlock();
}
```

---

## Examples

### Integration with JavaFX

```java
public class InventoryService {
    private final ExecutorService taskExecutor = 
        Executors.newVirtualThreadPerTaskExecutor();
    
    public CompletableFuture<List<Product>> searchAsync(String query) {
        return CompletableFuture.supplyAsync(() -> {
            return DB.find(Product.class)
                .where().ilike("name", "%" + query + "%")
                .findList();
        }, taskExecutor);
    }
}

// In controller — never block FX thread
@FXML
private void onSearch() {
    inventoryService.searchAsync(searchField.getText())
        .thenAcceptAsync(products -> {
            productTable.setItems(FXCollections.observableArrayList(products));
        }, Platform::runLater)
        .exceptionally(ex -> {
            Platform.runLater(() -> showError("Search failed: " + ex.getMessage()));
            return null;
        });
}
```

---

## Anti-Patterns

### ❌ Pinning via synchronized

```java
// WRONG — synchronized causes thread pinning
public synchronized List<Product> findAll() {
    return repository.findAll();
}
```
**Why:** `synchronized` pins the virtual thread to its carrier thread, eliminating the scalability benefit.  
**Fix:** Use `ReentrantLock` or `StampedLock` instead.

### ❌ Pooling virtual threads

```java
// WRONG — virtual threads should NOT be pooled
ExecutorService pool = Executors.newFixedThreadPool(10);
```
**Why:** Virtual threads are designed to be created per-task. Pooling adds overhead for no benefit.  
**Fix:** Use `Executors.newVirtualThreadPerTaskExecutor()` or `Thread.startVirtualThread()`.

### ❌ Using virtual threads for CPU-bound work

```java
// WRONG — CPU-bound work doesn't benefit from virtual threads
Thread.startVirtualThread(() -> {
    // Heavy computation here
    complexCalculation();
});
```
**Why:** CPU-bound work keeps the carrier thread busy; no other virtual threads can use it.  
**Fix:** Use `Executors.newWorkStealingPool()` or `ForkJoinPool` for CPU-bound work.

---

## Checklists

### Setup
- [ ] Java 21+ runtime verified (`java -version`)
- [ ] Maven compiler release set to 21
- [ ] `-Djdk.tracePinnedThreads=short` enabled in dev mode for pinning detection

### Implementation
- [ ] All I/O-offloading uses virtual thread executor
- [ ] `ReentrantLock` used instead of `synchronized` in virtual thread paths
- [ ] StructuredTaskScope used for parallel task groups
- [ ] No virtual thread pools

### Code Review
- [ ] No `synchronized` blocks in methods called from virtual threads
- [ ] No `ThreadLocal` abuse in virtual thread code
- [ ] Exception handling preserves structured scope cancellation

---

## Project-Specific Guidance (Simple POS)

- Use a single `Executors.newVirtualThreadPerTaskExecutor()` shared across all services
- Create it in `AppBootstrap` and inject into services via constructor
- For long-running background tasks (report generation, data export), use `StructuredTaskScope`
- The report generation service should use structured concurrency to parallelize data fetching

---

## Recommended Reading

### Official (Tier 1)
- [JEP 444: Virtual Threads](https://openjdk.org/jeps/444) — official JEP spec
- [JEP 453: Structured Concurrency](https://openjdk.org/jeps/453) — structured task scope
- [Java Virtual Threads (Oracle)](https://docs.oracle.com/en/java/javase/21/core/virtual-threads.html) — Oracle guide

---

## Exercises

### Exercise 1 — Parallel Dashboard Loader (medium)

**Task:** Implement a `DashboardService` that uses `StructuredTaskScope` to fetch low-stock products, today's sales, and today's revenue in parallel. Handle failure of any one task gracefully (return partial data with an error flag).  
**Verification:** Dashboard loads in < 500ms even with slow DB queries; partial results displayed when one query fails.

---

## AI/Agent Guide

### Strict Conventions
- Always use `Executors.newVirtualThreadPerTaskExecutor()` — never cached/fixed thread pools
- Always use `ReentrantLock` in any code path that will run on virtual threads
- Always run blocking I/O on virtual threads, update UI via `Platform.runLater`

### Forbidden Patterns
- `Executors.newFixedThreadPool()`, `newCachedThreadPool()` for I/O work
- `synchronized` in methods called from virtual threads
- `Thread.sleep()` in structured task scope children

### Preferred Libraries
- `java.util.concurrent` — virtual threads, structured concurrency (built-in)
- `java.util.concurrent.locks.ReentrantLock` — replacement for synchronized

### Example Prompts

```
Generate a Java 21 service method that uses virtual threads to run 3 database
queries in parallel, collects results into a record, and handles partial failure
with ShutdownOnFailure. The method returns CompletableFuture<DashboardData>.
```

### Architecture Decisions

| Decision | Choice | Rationale |
|----------|--------|-----------|
| Thread pool type | `newVirtualThreadPerTaskExecutor` | Cheapest for I/O-bound, no pooling needed |
| Lock type | `ReentrantLock` | Avoids virtual thread pinned by `synchronized` |
| Parallel query pattern | `StructuredTaskScope.ShutdownOnFailure` | Parent-child lifecycle, clean cancellation |

### Code Templates

```java
// Template: Virtual Thread Service Method
// Usage: For any service that does I/O off the FX thread
public CompletableFuture<ReturnType> operationAsync(InputType input) {
    return CompletableFuture.supplyAsync(() -> {
        // I/O work here — database, file, network
        return result;
    }, taskExecutor);
    // Controller handles .thenAcceptAsync(..., Platform::runLater)
}
```
