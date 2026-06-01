# Virtual Thread Service Anti-Patterns

## Pooling virtual threads

```java
// WRONG — defeats VT purpose
ExecutorService pool = Executors.newFixedThreadPool(50);
for (var item : items) pool.submit(() -> process(item));
```

**Pool adds contention. Use `newVirtualThreadPerTaskExecutor()` instead.**

## synchronized in VT path

```java
// WRONG — pins VT to carrier thread
public synchronized List<Product> findAll() {
    return DB.find(Product.class).findList();
}
```

**Use `ReentrantLock`. Pinning eliminates VT concurrency benefits.**

## Swallowing CompletableFuture exceptions

```java
// WRONG — exceptions silently lost
CompletableFuture.supplyAsync(() -> riskyWork());
```

**Always chain `.exceptionally()` or use `StructuredTaskScope.throwIfFailed()`.**

## Shared mutable state without locks

```java
// WRONG — race condition
private int counter = 0;
Thread.startVirtualThread(() -> counter++);
```

**Use `AtomicInteger`, `LongAdder`, or `ReentrantLock`.**

## Blocking I/O on the FX thread

```java
// WRONG — freezes UI
button.setOnAction(e -> {
    List data = DB.find(Product.class).findList(); // blocks!
});
```

**Offload to VT, return result via `Platform.runLater`.**
